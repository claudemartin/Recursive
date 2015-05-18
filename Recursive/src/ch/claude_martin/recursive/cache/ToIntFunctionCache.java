package ch.claude_martin.recursive.cache;

import java.util.function.IntSupplier;

@FunctionalInterface
public interface ToIntFunctionCache<T> {
  public int get(T key, IntSupplier supplier);
}
