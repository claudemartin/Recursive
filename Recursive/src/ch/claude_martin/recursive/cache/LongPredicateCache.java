package ch.claude_martin.recursive.cache;

import java.util.function.BooleanSupplier;

@FunctionalInterface
public interface LongPredicateCache {
  public boolean get(long key, BooleanSupplier supplier);

}
