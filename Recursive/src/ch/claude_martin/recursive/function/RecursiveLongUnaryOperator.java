package ch.claude_martin.recursive.function;

import java.util.function.LongUnaryOperator;

@FunctionalInterface
public interface RecursiveLongUnaryOperator {
  long apply(final long operand, final LongUnaryOperator self);
}
