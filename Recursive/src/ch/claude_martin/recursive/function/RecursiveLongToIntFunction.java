package ch.claude_martin.recursive.function;

import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface RecursiveLongToIntFunction {
  int apply(final long value, final LongToIntFunction self);
}
