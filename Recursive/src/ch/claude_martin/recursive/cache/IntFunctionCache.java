package ch.claude_martin.recursive.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

@FunctionalInterface
public interface IntFunctionCache<R> {

  public R get(int key, Supplier<R> supplier);

  /** Creates a cache that allows input from the given range (both inclusive). */
  @SuppressWarnings("unchecked")
  public static <R> IntFunctionCache<R> create(int min, int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final int size = Math.abs(max + 1 - min);
    final Object nil = new Object();
    final Object[] rs = new Object[size];
    Arrays.fill(rs, nil);

    return (i, s) -> {
      try {
        final Object cached = rs[i - min];
        if (cached == nil)
          return (R) (rs[i - min] = s.get());
        return (R) cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(i + " is not in bounds of used cache for IntFunction.",
            e);
      }
    };
  }

}
