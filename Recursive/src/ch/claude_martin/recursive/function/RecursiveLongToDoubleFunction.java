package ch.claude_martin.recursive.function;

import java.util.function.LongToDoubleFunction;

@FunctionalInterface
public interface RecursiveLongToDoubleFunction {
  double apply(final long value, final LongToDoubleFunction self);
}
