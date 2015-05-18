package ch.claude_martin.recursive.cache;

import java.util.function.IntSupplier;

@FunctionalInterface
public interface DoubleToIntFunctionCache {
  public int get(double key, IntSupplier supplier);
}
