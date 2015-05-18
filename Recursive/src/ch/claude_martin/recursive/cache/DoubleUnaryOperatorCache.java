package ch.claude_martin.recursive.cache;

import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface DoubleUnaryOperatorCache {
  public double get(double key, DoubleSupplier supplier);
}
