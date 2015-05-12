package ch.claude_martin.recursive.function;

import java.util.function.BiFunction;

@FunctionalInterface
public interface RecursiveBiFunction<T, U, R> {
  R apply(final T t, final U u, final BiFunction<T, U, R> self);
}
