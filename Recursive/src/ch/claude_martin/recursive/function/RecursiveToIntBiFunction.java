package ch.claude_martin.recursive.function;

import java.util.function.ToIntBiFunction;

@FunctionalInterface
public interface RecursiveToIntBiFunction<T, U> {
  int apply(final T t, final U u, final ToIntBiFunction<T, U> self);
}
