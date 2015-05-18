package ch.claude_martin.recursive.cache;

import java.util.function.Supplier;

@FunctionalInterface
public interface DoubleFunctionCache<R> {
  public R get(double key, Supplier<R> supplier);
}
