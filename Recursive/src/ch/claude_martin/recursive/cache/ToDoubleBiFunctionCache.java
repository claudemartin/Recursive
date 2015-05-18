package ch.claude_martin.recursive.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface ToDoubleBiFunctionCache<T, U> {
  public double get(T t, U u, DoubleSupplier supplier);

  /** This will automatically create a pair (2-tuple) of both arguments. You simply provide any kind
   * of map. */
  public static <T, U> ToDoubleBiFunctionCache<T, U> create(Supplier<Map<Object, Double>> ctor) {
    return (t, u, s) -> ctor.get().computeIfAbsent(new Pair<>(t, u), key -> s.getAsDouble());
  }

  public static <T, U> ToDoubleBiFunctionCache<T, U> create() {
    return create(HashMap::new);
  }

}
