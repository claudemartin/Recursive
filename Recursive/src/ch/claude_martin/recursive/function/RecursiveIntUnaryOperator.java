package ch.claude_martin.recursive.function;

import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface RecursiveIntUnaryOperator {
  int apply(final int operand, final IntUnaryOperator self);
}
