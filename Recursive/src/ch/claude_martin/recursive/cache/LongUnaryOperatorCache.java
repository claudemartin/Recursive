package ch.claude_martin.recursive.cache;

import java.util.function.LongSupplier;

@FunctionalInterface
public interface LongUnaryOperatorCache {
  public long get(long key, LongSupplier supplier);
}
