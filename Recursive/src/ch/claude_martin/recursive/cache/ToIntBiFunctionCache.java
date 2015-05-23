package ch.claude_martin.recursive.cache;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface ToIntBiFunctionCache<T, U> {
  public int get(T t, U u, IntSupplier supplier);

  /** This will automatically create a pair (2-tuple) of both arguments. You simply provide any kind
   * of map.
   * 
   * @see FunctionCache#create(Supplier) */
  public static <T, U> ToIntBiFunctionCache<T, U> create(Supplier<Map<Object, Integer>> ctor) {
    final Map<Object, Integer> map = requireNonNull(ctor, "ctor").get();
    return (t, u, s) -> map.computeIfAbsent(new Pair<>(t, u), key -> s.getAsInt());
  }

  /** Default cache, using {@link HashMap}. */
  public static <T, U> ToIntBiFunctionCache<T, U> create() {
    return create(HashMap::new);
  }

}
