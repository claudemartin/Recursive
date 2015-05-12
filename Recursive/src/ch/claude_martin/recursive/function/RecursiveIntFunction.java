package ch.claude_martin.recursive.function;

import java.util.function.IntFunction;

@FunctionalInterface
public interface RecursiveIntFunction<R> {
  R apply(final int value, final IntFunction<R> self);
}
