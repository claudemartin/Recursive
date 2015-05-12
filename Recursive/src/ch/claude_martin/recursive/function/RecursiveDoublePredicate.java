package ch.claude_martin.recursive.function;

import java.util.function.DoublePredicate;

@FunctionalInterface
public interface RecursiveDoublePredicate {
  boolean test(final double value, final DoublePredicate self);
}
