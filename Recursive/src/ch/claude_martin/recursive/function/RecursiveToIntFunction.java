package ch.claude_martin.recursive.function;

import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface RecursiveToIntFunction<T> extends ToIntBiFunction<T, ToIntFunction<T>> {
  @Override
  int applyAsInt(final T t, final ToIntFunction<T> self);

  default int apply(final T t, final ToIntFunction<T> self) {
    return this.applyAsInt(t, self);
  }
}
