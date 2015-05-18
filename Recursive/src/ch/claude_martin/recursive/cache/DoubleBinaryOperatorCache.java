package ch.claude_martin.recursive.cache;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface DoubleBinaryOperatorCache {
  public double get(double left, double right, DoubleSupplier supplier);

  /** Creates a cache that is backed by a {@code Map<Long, Integer>}. */
  public static DoubleBinaryOperatorCache create() {
    final Map<Pair<Double, Double>, Double> map = new TreeMap<>();
    return (a, b, s) -> map.computeIfAbsent(new Pair<>(a, b), x -> s.getAsDouble());
  }
}
