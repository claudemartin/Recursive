package ch.claude_martin.recursive.function;

import java.util.function.IntPredicate;

@FunctionalInterface
public interface RecursiveIntPredicate {
  boolean test(final int value, final IntPredicate self);
}
