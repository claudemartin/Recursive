package ch.claude_martin.recursive.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface RecursiveBiConsumer<T, U> {
  void accept(final T t, final U u, final BiConsumer<T, U> self);
}
