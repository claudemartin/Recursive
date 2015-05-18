package ch.claude_martin.recursive.cache;

import java.util.function.BooleanSupplier;

@FunctionalInterface
public interface DoublePredicateCache {
  public boolean get(double key, BooleanSupplier supplier);

}
