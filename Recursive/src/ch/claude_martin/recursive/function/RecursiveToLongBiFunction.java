package ch.claude_martin.recursive.function;

import java.util.function.ToLongBiFunction;

@FunctionalInterface
public interface RecursiveToLongBiFunction<T, U> {
  long apply(final T t, final U u, final ToLongBiFunction<T, U> self);
}
