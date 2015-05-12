package ch.claude_martin.recursive.function;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface RecursiveBiPredicate<T, U> {
  boolean test(final T t, final U u, final BiPredicate<T, U> self);
}
