package ch.claude_martin.recursive.cache;

import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface ToDoubleFunctionCache<T> {
  public double get(T key, DoubleSupplier supplier);
}
