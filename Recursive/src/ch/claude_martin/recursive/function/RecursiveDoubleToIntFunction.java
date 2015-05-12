package ch.claude_martin.recursive.function;

import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface RecursiveDoubleToIntFunction {
  int apply(final double value, final DoubleToIntFunction self);
}
