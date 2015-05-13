package ch.claude_martin.recursive.function;

import java.util.function.DoubleConsumer;

@FunctionalInterface
public interface RecursiveDoubleConsumer {
  void accept(final double v, final DoubleConsumer self);
}
