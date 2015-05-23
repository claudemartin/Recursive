package ch.claude_martin.recursive.cache;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@FunctionalInterface
public interface BiFunctionCache<T, U, R> {
  public R get(T t, U u, Supplier<R> supplier);

  /** This will automatically create a pair (2-tuple) of both arguments. You simply provide any kind
   * of map.
   * 
   * @see FunctionCache#create(Supplier) */
  public static <T, U, R> BiFunctionCache<T, U, R> create(Supplier<Map<Object, R>> ctor) {
    final Map<Object, R> map = requireNonNull(ctor, "ctor").get();
    return (t, u, s) -> map.computeIfAbsent(new Pair<>(t, u), key -> s.get());
  }

  /** Default cache, using {@link HashMap}. */
  public static <T, U, R> BiFunctionCache<T, U, R> create() {
    return create(HashMap::new);
  }
}
