package ch.claude_martin.recursive.function;

import java.util.function.LongFunction;

public interface RecursiveLongFunction<R> {
  R apply(final long value, final LongFunction<R> self);
}
