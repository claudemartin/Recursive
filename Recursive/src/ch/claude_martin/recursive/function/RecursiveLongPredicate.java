package ch.claude_martin.recursive.function;

import java.util.function.LongPredicate;

public interface RecursiveLongPredicate {
  boolean test(final long value, final LongPredicate self);
}
