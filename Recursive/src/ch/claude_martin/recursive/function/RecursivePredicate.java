package ch.claude_martin.recursive.function;

import java.util.function.Predicate;

public interface RecursivePredicate<T> {
  boolean test(final long value, final Predicate<T> self);
}
