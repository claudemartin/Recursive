package ch.claude_martin.recursive;

import java.util.function.IntToLongFunction;
import java.util.stream.IntStream;

public class Demo {
  /** Since Java 8 still doesn't have predefined tuples... */
  static class FibResult {
    long n, Fn;

    public FibResult(long n, long fn) {
      this.n = n;
      this.Fn = fn;
    }
  }

  public static void main(String[] args) throws Throwable {
    // "fib" is a recursive function.
    final IntToLongFunction fib = Recursive.cachedIntToLongFunction(//
        (n, self) -> n <= 1 ? n : self.applyAsLong(n - 1) + self.applyAsLong(n - 2), 0, 60);

    IntStream.rangeClosed(0, 60).mapToObj(n -> new FibResult(n, fib.applyAsLong(n)))//
        .forEach(r -> System.out.format("F%d = %d%n", r.n, r.Fn));
  }
}
