package ch.claude_martin.recursive.function;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface RecursiveUnaryOperator<T> extends BiFunction<T, UnaryOperator<T>, T> {

  @Override
  T apply(final T t, final UnaryOperator<T> self);
}
