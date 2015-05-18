package ch.claude_martin.recursive.function;

import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

@FunctionalInterface
public interface RecursiveToLongFunction<T> extends ToLongBiFunction<T, ToLongFunction<T>> {
  @Override
  long applyAsLong(final T t, final ToLongFunction<T> self);

  default long apply(final T t, final ToLongFunction<T> self) {
    return this.applyAsLong(t, self);
  }
}
