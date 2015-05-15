package ch.claude_martin.recursive.cache;

import java.util.Arrays;
import java.util.function.LongSupplier;

@FunctionalInterface
public interface IntToLongFunctionCache {
  public long get(int key, LongSupplier supplier);

  /** Creates a cache that allows input from the given range (both inclusive). */
  public static IntToLongFunctionCache create(int min, int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final int size = Math.abs(max + 1 - min);
    final long nil = Long.MIN_VALUE + 1234;
    final long[] longs = new long[size];
    Arrays.fill(longs, nil);
    return (v, s) -> {
      try {
        final long cached = longs[v - min];
        if (cached == nil)
          return longs[v - min] = s.getAsLong();
        return cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(v
            + " is not in bounds of used cache for IntToLongFunction.", e);
      }
    };
  }
}
