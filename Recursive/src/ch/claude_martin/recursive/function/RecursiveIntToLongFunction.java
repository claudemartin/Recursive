package ch.claude_martin.recursive.function;

import java.util.function.IntToLongFunction;

@FunctionalInterface
public interface RecursiveIntToLongFunction {
  long apply(final int value, final IntToLongFunction self);
}
