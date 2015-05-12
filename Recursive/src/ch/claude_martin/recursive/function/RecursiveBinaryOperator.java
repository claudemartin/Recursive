package ch.claude_martin.recursive.function;

import java.util.function.BiFunction;

@FunctionalInterface
public interface RecursiveBinaryOperator<T> extends RecursiveBiFunction<T, T, T> {
  @Override
  public T apply(T t, T u, BiFunction<T, T, T> self);
}
