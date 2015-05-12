package ch.claude_martin.recursive.function;

import java.util.function.DoubleToLongFunction;

@FunctionalInterface
public interface RecursiveDoubleToLongFunction {
  long apply(final double value, final DoubleToLongFunction self);
}
