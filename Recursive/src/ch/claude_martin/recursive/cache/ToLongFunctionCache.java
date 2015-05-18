package ch.claude_martin.recursive.cache;

import java.util.function.LongSupplier;

@FunctionalInterface
public interface ToLongFunctionCache<T> {
  public long get(T key, LongSupplier supplier);
}
