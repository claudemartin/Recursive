package ch.claude_martin.recursive.cache;

import static java.util.Objects.requireNonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

@FunctionalInterface
public interface FunctionCache<T, R> {
  public R get(T key, Supplier<R> supplier);

  /** A basic cache implementation that uses
   * {@link Map#computeIfAbsent(Object, java.util.function.Function) computeIfAbsent}. This is thread
   * safe if you use a thread safe map implementation that provides atomicity guarantees for
   * computeIfAbsent.
   * 
   * @param ctor
   *          A supplier for a new (empty) {@link Map}.
   * @return a nrealy created {@link FunctionCache} */
  public static <T, R> FunctionCache<T, R> create(Supplier<Map<T, R>> ctor) {
    final Map<T, R> map = requireNonNull(ctor, "ctor").get();
    return (t, s) -> map.computeIfAbsent(t, key -> s.get());
  }

  /** Default cache, using {@link HashMap}. */
  public static <T, R> FunctionCache<T, R> create() {
    return create(HashMap::new);
  }

  /** Cache which only holds weak references (for both input and output). */
  public static <T, R> FunctionCache<T, R> createWeakCache() {
    final WeakHashMap<T, WeakReference<R>> map = new WeakHashMap<>();
    return (t, s) -> {
      R result = null;
      final WeakReference<R> ref = map.computeIfAbsent(t, key -> new WeakReference<>(s.get()));
      if ((result = ref.get()) == null)
        map.put(t, new WeakReference<>(result = s.get()));
      return result;
    };
  }
}
