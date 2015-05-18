package ch.claude_martin.recursive.cache;

import java.util.function.LongSupplier;

@FunctionalInterface
public interface DoubleToLongFunctionCache {
  public long get(double key, LongSupplier supplier);
}
