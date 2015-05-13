package ch.claude_martin.recursive.function;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface RecursiveIntConsumer {
  void accept(final int i, final IntConsumer self);
}
