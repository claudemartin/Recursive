package ch.claude_martin.recursive.cache;

import java.util.function.LongSupplier;

@FunctionalInterface
public interface LongBinaryOperatorCache {
  public long get(long left, long right, LongSupplier supplier);
}
