package ch.claude_martin.recursive.cache;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

@FunctionalInterface
public interface FunctionCache<T, R> {
  public R get(T key, Supplier<R> supplier);

  public static <T, R> FunctionCache<T, R> create(Supplier<Map<T, R>> ctor) {
    return (t, s) -> ctor.get().computeIfAbsent(t, key -> s.get());
  }

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
