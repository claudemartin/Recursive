package ch.claude_martin.recursive.cache;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface ToDoubleBiFunctionCache<T, U> {
  public double get(T t, U u, DoubleSupplier supplier);

  /** This will automatically create a pair (2-tuple) of both arguments. You simply provide any kind
   * of map.
   * 
   * @see FunctionCache#create(Supplier) */
  public static <T, U> ToDoubleBiFunctionCache<T, U> create(Supplier<Map<Object, Double>> ctor) {
    final Map<Object, Double> map = requireNonNull(ctor, "ctor").get();
    return (t, u, s) -> map.computeIfAbsent(new Pair<>(t, u), key -> s.getAsDouble());
  }

  /** Default cache, using {@link HashMap}. */
  public static <T, U> ToDoubleBiFunctionCache<T, U> create() {
    return create(HashMap::new);
  }

}
