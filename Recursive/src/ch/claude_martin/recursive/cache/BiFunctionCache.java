package ch.claude_martin.recursive.cache;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@FunctionalInterface
public interface BiFunctionCache<T, U, R> {
  public R get(T t, U u, Supplier<R> supplier);

  public static <T, U, R> BiFunctionCache<T, U, R> create(Supplier<Map<Object, R>> ctor) {
    return (t, u, s) -> ctor.get().computeIfAbsent(new Pair<>(t, u), key -> s.get());
  }

  public static <T, U, R> BiFunctionCache<T, U, R> create() {
    return create(HashMap::new);
  }

  // public static <T, U, R> BiFunctionCache<T, U, R> createWeakCache() {
  // TODO
  // }
}
