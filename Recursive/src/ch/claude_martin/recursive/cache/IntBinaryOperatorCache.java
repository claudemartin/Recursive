package ch.claude_martin.recursive.cache;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;

@FunctionalInterface
public interface IntBinaryOperatorCache {
  public int get(int left, int right, IntSupplier supplier);

  /** Creates a cache that is backed by a {@code Map<Long, Integer>}. */
  public static IntBinaryOperatorCache create() {
    final Map<Long, Integer> map = new TreeMap<>();
    final BiFunction<Integer, Integer, Long> i2l = (a, b) -> ((long) a << 32) + (long) b;
    return (a, b, s) -> map.computeIfAbsent(i2l.apply(a, b), x -> s.getAsInt());
  }
}
