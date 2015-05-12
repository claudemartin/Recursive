package ch.claude_martin.recursive;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.*;

import ch.claude_martin.recursive.function.*;

/**
 * Utility class for recusrive closures. This can convert any functional type to one that allows
 * recursive invokations. An extra paremater "self" can be used to call itself.
 * 
 * @author Claude Martin
 *
 * @param <F>
 *          The type of functional interface of the closure.
 */
public class Recursive<F> {
  /** Recursive {@link BiFunction}. */
  public static <T, U, R> BiFunction<T, U, R> biFunction(RecursiveBiFunction<T, U, R> f) {
    final Recursive<BiFunction<T, U, R>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Recursive {@link BinaryOperator}. */
  public static <T> BinaryOperator<T> binaryOperator(RecursiveBinaryOperator<T> f) {
    final Recursive<BinaryOperator<T>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Recursive {@link BiPredicate}. */
  public static <T, U> BiPredicate<T, U> biPredicate(RecursiveBiPredicate<T, U> f) {
    final Recursive<BiPredicate<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.test(t, u, r.f);
  }

  /** Recursive {@link DoubleBinaryOperator}. */
  public static DoubleBinaryOperator doubleBinaryOperator(RecursiveDoubleBinaryOperator f) {
    final Recursive<DoubleBinaryOperator> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Recursive {@link DoubleFunction}. */
  public static <R> DoubleFunction<R> doubleFunction(RecursiveDoubleFunction<R> f) {
    final Recursive<DoubleFunction<R>> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link DoublePredicate}. */
  public static DoublePredicate doublePredicate(RecursiveDoublePredicate f) {
    final Recursive<DoublePredicate> r = new Recursive<>();
    return r.f = d -> f.test(d, r.f);
  }

  /** Recursive {@link DoubleToIntFunction}. */
  public static DoubleToIntFunction doubleToIntFunction(RecursiveDoubleToIntFunction f) {
    final Recursive<DoubleToIntFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link DoubleToLongFunction}. */
  public static DoubleToLongFunction doubleToLongFunction(RecursiveDoubleToLongFunction f) {
    final Recursive<DoubleToLongFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link DoubleUnaryOperator}. */
  public static DoubleUnaryOperator doubleUnaryOperator(RecursiveDoubleUnaryOperator f) {
    final Recursive<DoubleUnaryOperator> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link Function}. */
  public static <T, R> Function<T, R> function(BiFunction<T, Function<T, R>, R> f) {
    final Recursive<Function<T, R>> r = new Recursive<>();
    return r.f = t -> f.apply(t, r.f);
  }

  /**
   * Like {@link #function(BiFunction)}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   * 
   * @param f
   *          The function
   * @return recursive, cached Function
   */
  public static <T, R> Function<T, R> cachedFunction(BiFunction<T, Function<T, R>, R> f) {
    final Recursive<Function<T, R>> r = new Recursive<>();
    final Map<T, R> cache = new HashMap<>();
    return r.f = t -> cache.computeIfAbsent(t, t2 -> f.apply(t2, r.f));
  }

  /** Recursive {@link IntBinaryOperator}. */
  public static IntBinaryOperator intBinaryOperator(RecursiveIntBinaryOperator f) {
    final Recursive<IntBinaryOperator> r = new Recursive<>();
    return r.f = (left, right) -> f.apply(left, right, r.f);
  }

  /** Recursive {@link IntFunction}. */
  public static <R> IntFunction<R> intFunction(RecursiveIntFunction<R> f) {
    final Recursive<IntFunction<R>> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /**
   * Like {@link #intFunction(RecursiveIntFunction)}, but using memoization.
   * 
   * @param f
   *          The function
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntUnaryOperator
   * */
  @SuppressWarnings("unchecked")
  public static <R> IntFunction<R> cachedIntFunction(RecursiveIntFunction<R> f, int min, int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final Recursive<IntFunction<R>> r = new Recursive<>();
    final int size = Math.abs(max + 1 - min);
    final R nil = (R) new Object();
    final R[] cache = (R[]) new Object[size];
    Arrays.fill(cache, nil);
    return r.f = i -> {
      try {
        final R cached = cache[i - min];
        if (cached == nil)
          return cache[i - min] = f.apply(i, r.f);
        return cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(i + " is not in bounds of used cache for IntFunction.",
            e);
      }
    };
  }

  /** Recursive {@link IntToDoubleFunction}. */
  public static IntToDoubleFunction intToDoubleFunction(RecursiveIntToDoubleFunction f) {
    final Recursive<IntToDoubleFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link IntToLongFunction}. */
  public static IntToLongFunction intToLongFunction(RecursiveIntToLongFunction f) {
    final Recursive<IntToLongFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /**
   * Like {@link #intToLongFunction(RecursiveIntToLongFunction)}, but using memoization.
   * 
   * @param f
   *          The function
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntToLongFunction
   */
  public static IntToLongFunction cachedIntToLongFunction(RecursiveIntToLongFunction f, int min,
      int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final Recursive<IntToLongFunction> r = new Recursive<>();

    final int size = Math.abs(max + 1 - min);
    final long nil = Long.MIN_VALUE + 1234;
    final long[] cache = new long[size];
    Arrays.fill(cache, nil);

    return r.f = i -> {
      try {
        final long cached = cache[i - min];
        if (cached == nil)
          return cache[i - min] = f.apply(i, r.f);
        return cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(i
            + " is not in bounds of used cache for IntToLongFunction.", e);
      }
    };
  }

  /** Recursive {@link IntUnaryOperator}. */
  public static IntUnaryOperator intUnaryOperator(RecursiveIntUnaryOperator f) {
    final Recursive<IntUnaryOperator> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /**
   * Like {@link #intUnaryOperator(RecursiveIntUnaryOperator)}, but using memoization.
   * 
   * @param f
   *          The operator
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntUnaryOperator
   */
  public static IntUnaryOperator cachedIntUnaryOperator(RecursiveIntUnaryOperator f, int min,
      int max) {
    if (min >= max)
      throw new IllegalArgumentException(String.format("min=%d; max=%d", min, max));
    final Recursive<IntUnaryOperator> r = new Recursive<>();

    final int size = Math.abs(max + 1 - min);
    final int nil = min - 1;
    final int[] cache = new int[size];
    Arrays.fill(cache, nil);

    return r.f = i -> {
      try {
        final int cached = cache[i - min];
        if (cached == nil)
          return cache[i - min] = f.apply(i, r.f);
        return cached;
      } catch (final ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException(i
            + " is not in bounds of used cache for IntUnaryOperator.", e);
      }
    };

  }

  /** Recursive {@link LongBinaryOperator}. */
  public static LongBinaryOperator longBinaryOperator(RecursiveLongBinaryOperator f) {
    final Recursive<LongBinaryOperator> r = new Recursive<>();
    return r.f = (left, right) -> f.apply(left, right, r.f);
  }

  /** Recursive {@link LongFunction}. */
  public static <R> LongFunction<R> longFunction(RecursiveLongFunction<R> f) {
    final Recursive<LongFunction<R>> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Recursive {@link LongPredicate}. */
  public static LongPredicate longPredicate(RecursiveLongPredicate f) {
    final Recursive<LongPredicate> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Recursive {@link LongToDoubleFunction}. */
  public static LongToDoubleFunction longToDoubleFunction(RecursiveLongToDoubleFunction f) {
    final Recursive<LongToDoubleFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link LongToIntFunction}. */
  public static LongToIntFunction longToIntFunction(RecursiveLongToIntFunction f) {
    final Recursive<LongToIntFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Recursive {@link LongUnaryOperator}. */
  public static LongUnaryOperator longUnaryOperator(RecursiveLongUnaryOperator f) {
    final Recursive<LongUnaryOperator> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Recursive {@link Predicate}. */
  public static <T> Predicate<T> predicate(BiPredicate<T, Predicate<T>> f) {
    final Recursive<Predicate<T>> r = new Recursive<>();
    return r.f = t -> f.test(t, r.f);
  }

  /** Recursive {@link ToDoubleBiFunction}. */
  public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(
      RecursiveToDoubleBiFunction<T, U> f) {
    final Recursive<ToDoubleBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Recursive {@link ToDoubleFunction}. */
  public static <T> ToDoubleFunction<T> toDoubleFunction(RecursiveToDoubleFunction<T> f) {
    final Recursive<ToDoubleFunction<T>> r = new Recursive<>();
    return r.f = t -> f.applyAsDouble(t, r.f);
  }

  /** Recursive {@link ToIntBiFunction}. */
  public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(RecursiveToIntBiFunction<T, U> f) {
    final Recursive<ToIntBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Recursive {@link ToIntFunction}. */
  public static <T> ToIntFunction<T> toIntFunction(RecursiveToIntFunction<T> f) {
    final Recursive<ToIntFunction<T>> r = new Recursive<>();
    return r.f = t -> f.applyAsInt(t, r.f);
  }

  /** Recursive {@link ToLongBiFunction}. */
  public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(RecursiveToLongBiFunction<T, U> f) {
    final Recursive<ToLongBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Recursive {@link ToLongFunction}. */
  public static <T> ToLongFunction<T> toLongFunction(RecursiveToLongFunction<T> f) {
    final Recursive<ToLongFunction<T>> r = new Recursive<>();
    return r.f = t -> f.applyAsLong(t, r.f);
  }

  /** Recursive {@link UnaryOperator}. */
  public static <T> UnaryOperator<T> unaryOperator(RecursiveUnaryOperator<T> f) {
    final Recursive<UnaryOperator<T>> r = new Recursive<>();
    return r.f = t -> f.apply(t, r.f);
  }

  /** Holds a reference to the recursive function, predicate or operand. */
  private F f;
}