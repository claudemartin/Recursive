package ch.claude_martin.recursive.cache;

import java.util.Arrays;
import java.util.function.IntSupplier;

@FunctionalInterface
public interface IntUnaryOperatorCache {
  public int get(int key, IntSupplier supplier);

  /** Creates a cache that allows input from the given range (both inclusive). */
  public static IntUnaryOperatorCache create(int min, int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final int size = Math.abs(max + 1 - min);
    final int nil = min - 1;
    final int[] ints = new int[size];
    Arrays.fill(ints, nil);

    return (v, s) -> {
      try {
        final int cached = ints[v - min];
        if (cached == nil)
          return ints[v - min] = s.getAsInt();
        return cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(v
            + " is not in bounds of used cache for IntUnaryOperator.", e);
      }
    };

  }
}
