package ch.claude_martin.recursive.cache;

import java.util.Arrays;
import java.util.function.DoubleSupplier;

@FunctionalInterface
public interface IntToDoubleFunctionCache {
  public double get(int key, DoubleSupplier supplier);

  /** Creates a cache that allows input from the given range (both inclusive). */
  public static IntToDoubleFunctionCache create(int min, int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final int size = Math.abs(max + 1 - min);
    final double nil = Double.MIN_VALUE + 1234;
    final double[] doubles = new double[size];
    Arrays.fill(doubles, nil);
    final IntToDoubleFunctionCache cache = (v, s) -> {
      try {
        final double cached = doubles[v - min];
        if (cached == nil)
          return doubles[v - min] = s.getAsDouble();
        return cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(v
            + " is not in bounds of used cache for IntToDoubleFunction.", e);
      }
    };
    return cache;
  }
}
