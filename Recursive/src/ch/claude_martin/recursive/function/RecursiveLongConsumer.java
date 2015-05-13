package ch.claude_martin.recursive.function;

import java.util.function.LongConsumer;

@FunctionalInterface
public interface RecursiveLongConsumer {
  void accept(final long v, final LongConsumer self);
}
