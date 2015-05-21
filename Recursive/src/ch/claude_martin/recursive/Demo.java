package ch.claude_martin.recursive;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.*;
import java.util.stream.IntStream;

import ch.claude_martin.recursive.function.RecursiveUnaryOperator;

/**
 * In order to understand recursion, one must first understand recursion.
 * 
 * @author Claude Martin
 * @see Demo
 */
public class Demo {
  public static void main(String... args) throws Throwable {
    System.out.println("--- Fibonacci: ---");
    // "fib" is a recursive function.
    final IntToLongFunction fib = Recursive.cachedIntToLongFunction(//
        (n, self) -> n <= 1 ? n : self.applyAsLong(n - 1) + self.applyAsLong(n - 2), 0, 60);

    IntStream.rangeClosed(55, 60).mapToObj(n -> String.format("F%d = %d", n, fib.applyAsLong(n)))
        .forEach(System.out::println);

    System.out.println("--- Factorial: ---");
    // "fact" is a recursive function too.
    // But's let test this with large numbers!
    final UnaryOperator<BigInteger> fact = Recursive.cachedUnaryOperator(//
        (n, self) -> n.equals(BigInteger.ZERO) ? BigInteger.ONE : //
            n.multiply(self.apply(n.subtract(BigInteger.ONE))));

    IntStream.rangeClosed(100, 105).mapToObj(BigInteger::valueOf)
        .map(n -> String.format("%s! = %s", n, fact.apply(n))).forEach(System.out::println);

    System.out.println("--- Greatest Common Divisor: ---");
    // The cache will convert two ints to one Long:
    final IntBinaryOperator gcd = Recursive.cachedIntBinaryOperator(//
        (x, y, self) -> y == 0 ? x : self.applyAsInt(y, x % y));

    for (int x = 200; x < 300; x++)
      for (int y = 200; y < 300; y++) {
        if (x == y)
          continue;
        long gcdXY = gcd.applyAsInt(x, y);
        if (gcdXY >= 70)
          System.out.println(String.format("gcd(%d,%d) = %d", x, y, gcdXY));
      }

    System.out.println("--- Sum: ---");
    // Returns the sum of elements in given list of Integers:
    ToIntFunction<List<Integer>> sum = Recursive.toIntFunction((list, self) -> {
      if (list.isEmpty())
        return 0;
      return list.get(0) + self.applyAsInt(list.subList(1, list.size()));
    });

    List<Integer> list = Arrays.asList(3, 5, 42, -17, 321, 1010, -1, 0);
    // same as: list.stream().mapToInt(i->i).sum();
    System.out.println(String.format("list = %s", list));
    System.out.println(String.format("sum(list) = %d", sum.applyAsInt(list)));

    System.out.println("--- Find Max: ---");

    ToIntFunction<List<Integer>> max = Recursive.toIntFunction((l, self) -> {
      if (l.isEmpty())
        throw new IllegalArgumentException("Empty List");
      final int size = l.size();
      if (size == 1)
        return list.get(0);
      if (size == 2)
        return Math.max(l.get(0), l.get(1));
      final int middle = size / 2;
      return Math.max(self.applyAsInt(l.subList(0, middle)),
          self.applyAsInt(l.subList(middle, size)));
    });

    System.out.println(String.format("max(list) = %d", max.applyAsInt(list)));

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
    System.out.println(find.apply(new File(path).getParentFile(), "Demo.java").map(File::toString)
        .map("Source Code found: "::concat).orElse("File not found"));

    System.out.println("--- Stay Positive: ---");
    // Will get any integer:
    final IntSupplier rng = new Random()::nextInt;
    // Will try again if negative:
    final IntSupplier positiveRng = Recursive.intSupplier((self) -> {
      final int next = rng.getAsInt();
      if (next >= 0)
        return next;
      return self.getAsInt();
    });

    System.out.println("Random Unsigned Number: " + positiveRng.getAsInt());

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

    System.out.println();
    System.out.flush();
    if(args.length>0) {
      System.out.println("End of Demo. Thank you and good bye!");
      return;
    }

    System.out.println("I heard you like recursion, so I put some recursion into your recursion.");
    try {
      Thread.sleep(2000);
    } catch (Exception e) {
    }
    main("U MAD?");

  }
}
