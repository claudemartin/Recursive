package ch.claude_martin.recursive.function;

import java.util.function.ToDoubleBiFunction;

@FunctionalInterface
public interface RecursiveToDoubleBiFunction<T, U> {
  double apply(final T t, final U u, final ToDoubleBiFunction<T, U> self);
}
