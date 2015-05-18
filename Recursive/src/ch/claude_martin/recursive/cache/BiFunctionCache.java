package ch.claude_martin.recursive.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@FunctionalInterface
public interface BiFunctionCache<T, U, R> {
  public R get(T t, U u, Supplier<R> supplier);

  /** This will automatically create a pair (2-tuple) of both arguments. You simply provide any kind
   * of map. */
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
