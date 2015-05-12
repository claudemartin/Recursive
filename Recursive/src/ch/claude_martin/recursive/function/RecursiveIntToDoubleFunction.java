package ch.claude_martin.recursive.function;

import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface RecursiveIntToDoubleFunction {
  double apply(final int value, final IntToDoubleFunction self);
}
