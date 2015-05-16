package ch.claude_martin.recursive.cache;

import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import ch.claude_martin.recursive.Recursive;

@FunctionalInterface
public interface IntPredicateCache {
  public boolean get(int key, BooleanSupplier supplier);

  /** Creates a cache that allows input from the given range (both inclusive). */
  public static IntPredicateCache create(int min, int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final int size = Math.abs(max + 1 - min);
    final byte nil = -1;
    final byte[] bools = new byte[size];
    Arrays.fill(bools, nil);

    return (v, s) -> {
      try {
        final byte cached = bools[v - min];
        if (cached == nil) {
          boolean result = s.getAsBoolean();
          bools[v - min] = (byte) (result ? 1 : 0);
          return result;
        }
        return cached == 1 ? true : false;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(v + " is not in bounds of used cache for IntPredicate.",
            e);
      }
    };

  }
}
