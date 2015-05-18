package ch.claude_martin.recursive.cache;

import java.util.function.Supplier;

@FunctionalInterface
public interface LongFunctionCache<R> {
  public R get(long key, Supplier<R> supplier);
}
