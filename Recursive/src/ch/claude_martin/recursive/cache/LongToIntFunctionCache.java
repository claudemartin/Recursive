package ch.claude_martin.recursive.cache;

import java.util.function.IntSupplier;

@FunctionalInterface
public interface LongToIntFunctionCache {
  public int get(long key, IntSupplier supplier);
}
