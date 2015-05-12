package ch.claude_martin.recursive.function;

import java.util.function.LongBinaryOperator;

@FunctionalInterface
public interface RecursiveLongBinaryOperator {
  long apply(final long left, final long right, final LongBinaryOperator self);
}
