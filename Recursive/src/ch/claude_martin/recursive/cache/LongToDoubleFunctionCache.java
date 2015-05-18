package ch.claude_martin.recursive.cache;

import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface LongToDoubleFunctionCache {
  public double get(long key, DoubleSupplier supplier);
}
