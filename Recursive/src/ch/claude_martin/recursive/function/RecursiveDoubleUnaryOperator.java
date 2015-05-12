package ch.claude_martin.recursive.function;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface RecursiveDoubleUnaryOperator {
  double apply(final double value, final DoubleUnaryOperator self);
}
