package ch.claude_martin.recursive.function;

import java.util.function.DoubleBinaryOperator;

@FunctionalInterface
public interface RecursiveDoubleBinaryOperator {
  double apply(final double left, final double right, final DoubleBinaryOperator self);
}