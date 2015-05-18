package ch.claude_martin.recursive.function;

import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface RecursiveToDoubleFunction<T> extends ToDoubleBiFunction<T, ToDoubleFunction<T>> {
  @Override
  double applyAsDouble(final T t, final ToDoubleFunction<T> self);

  default double apply(final T t, final ToDoubleFunction<T> self) {
    return this.applyAsDouble(t, self);
  }
}
