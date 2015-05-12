package ch.claude_martin.recursive;

import java.util.function.IntToLongFunction;
import java.util.stream.IntStream;

public class Demo {
  public static void main(String[] args) throws Throwable {
    // "fib" is a recursive function.
    final IntToLongFunction fib = Recursive.cachedIntToLongFunction(//
        (n, self) -> n <= 1 ? n : self.applyAsLong(n - 1) + self.applyAsLong(n - 2), 0, 60);

    IntStream.rangeClosed(0, 60).mapToObj(n -> String.format("F%d = %d", n, fib.applyAsLong(n)))
        .forEach(System.out::println);
  }
}
