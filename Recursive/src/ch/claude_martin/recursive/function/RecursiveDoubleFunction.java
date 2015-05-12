package ch.claude_martin.recursive.function;

import java.util.function.DoubleFunction;

@FunctionalInterface
public interface RecursiveDoubleFunction<R> {
  R apply(final double value, final DoubleFunction<R> self);
}
