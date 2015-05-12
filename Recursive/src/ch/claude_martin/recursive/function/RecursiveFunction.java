package ch.claude_martin.recursive.function;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface RecursiveFunction<T, R> extends BiFunction<T, Function<T, R>, R> {

  @Override
  R apply(final T t, final Function<T, R> self);
}
