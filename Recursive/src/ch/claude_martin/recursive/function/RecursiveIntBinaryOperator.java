package ch.claude_martin.recursive.function;

import java.util.function.IntBinaryOperator;

@FunctionalInterface
public interface RecursiveIntBinaryOperator {
  int apply(final int left, final int right, final IntBinaryOperator self);
}
