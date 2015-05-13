package ch.claude_martin.recursive;

import java.io.File;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

public class Demo {
  public static void main(String[] args) throws Throwable {
    System.out.println("--- Fibonacci: ---");
    // "fib" is a recursive function.
    final IntToLongFunction fib = Recursive.cachedIntToLongFunction(//
        (n, self) -> n <= 1 ? n : self.applyAsLong(n - 1) + self.applyAsLong(n - 2), 0, 60);

    IntStream.rangeClosed(50, 60).mapToObj(n -> String.format("F%d = %d", n, fib.applyAsLong(n)))
        .forEach(System.out::println);

    System.out.println("--- Factorial: ---");
    // "fact" is a recursive function too.
    final IntToLongFunction fact = Recursive.cachedIntToLongFunction(//
        (n, self) -> n == 0 ? 1 : n * self.applyAsLong(n - 1), 0, 20);

    IntStream.rangeClosed(10, 20).mapToObj(n -> String.format("%d! = %d", n, fact.applyAsLong(n)))
        .forEach(System.out::println);

    System.out.println("--- Greatest Common Divisor: ---");
    // So far there's no cached version for this:
    final LongBinaryOperator gcd = Recursive.longBinaryOperator(//
        (x, y, self) -> y == 0 ? x : self.applyAsLong(y, x % y));

    for (int x = 200; x < 300; x++)
      for (int y = 200; y < 300; y++) {
        if (x == y)
          continue;
        long gcdXY = gcd.applyAsLong(x, y);
        if (gcdXY >= 70)
          System.out.println(String.format("gcd(%d,%d) = %d", x, y, gcdXY));
      }

    System.out.println("--- Traverse File System: ---");
    // Try to find the source code of this Demo:
    final BiFunction<File, String, Optional<File>> find = Recursive.biFunction(//
        (f, name, self) -> {
          for (File x : Optional.ofNullable(f.listFiles()).orElse(new File[0])) {
            if (name.equals(x.getName()))
              return Optional.of(x);
            if (x.isDirectory() && x.canRead() && x.getName().charAt(0) != '.') {
              Optional<File> o = self.apply(x, name);
              if (o.isPresent())
                return o;
            }
          }
          return Optional.empty();
        });

    String path = Demo.class.getProtectionDomain().getCodeSource().getLocation().getPath()
        .replaceFirst("^/([A-Z]:/)", "$1").replaceAll("bin/$", "");
    System.out.println(find.apply(new File(path).getParentFile(), "Demo.java").map(File::toString).map("Source Code found: "::concat)
        .orElse("File not found"));

    System.out.println("--- Count Down: ---");
    
    // This works like a simple animation loop:
    Recursive.intConsumer((i, self) -> {
      if (i == 0) {
        System.out.println("TAKE OFF!");
        return;
      }
      System.out.println(i);
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
      }
      self.accept(i - 1);
    }).accept(5);

    System.out.println("");
    System.out.println("End of Demo. Thank you and good bye!");
    
    System.out.flush();
    System.out.println();

  }
}
