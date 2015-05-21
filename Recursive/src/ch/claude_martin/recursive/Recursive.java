package ch.claude_martin.recursive;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.*;

import ch.claude_martin.recursive.cache.*;
import ch.claude_martin.recursive.function.*;

/** Utility class for recusrive closures. This can convert any functional type to one that allows
 * recursive invokations. An extra paremater "self" can be used to call itself.
 * 
 * <p>
 * For each function, predicate and operator there is a <i>cached</i> version, which uses
 * <i>memoization</i> to speed up execution. Existing results are not calculated again. You simply
 * need to provide some memoization routine. Some have predefined cache implementations, which are
 * based on arrays or JCF. Use HPPC or Koloboke for best results.
 *
 * <p>
 * Note: Each cache exists at least as long as the closure exists. In some cases this might lead to
 * {@link OutOfMemoryError}.
 *
 * @author Claude Martin
 * @see Recursive
 * 
 * @param <F>
 *          The type of functional interface of the closure. */
public class Recursive<F> {
  /** Recursive {@link BiFunction}. */
  public static <T, U, R> BiFunction<T, U, R> biFunction(RecursiveBiFunction<T, U, R> f) {
    final Recursive<BiFunction<T, U, R>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Like {@link #biFunction}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @return recursive, cached BiFunction */
  public static <T, U, R> BiFunction<T, U, R> cachedBiFunction(RecursiveBiFunction<T, U, R> f) {
    return cachedBiFunction(f, BiFunctionCache.create());
  }

  /** Like {@link #biFunction}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached BiFunction */
  public static <T, U, R> BiFunction<T, U, R> cachedBiFunction(RecursiveBiFunction<T, U, R> f,
      BiFunctionCache<T, U, R> cache) {
    final Recursive<BiFunction<T, U, R>> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.apply(t, u, r.f));
  }

  /** Recursive {@link BinaryOperator}. */
  public static <T> BinaryOperator<T> binaryOperator(RecursiveBinaryOperator<T> f) {
    final Recursive<BinaryOperator<T>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Like {@link #binaryOperator}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @return recursive, cached BinaryOperator */
  public static <T> BinaryOperator<T> cachedBinaryOperator(RecursiveBinaryOperator<T> f) {
    return cachedBinaryOperator(f, BiFunctionCache.create());
  }

  /** Like {@link #binaryOperator}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached BinaryOperator */
  public static <T> BinaryOperator<T> cachedBinaryOperator(RecursiveBinaryOperator<T> f,
      BiFunctionCache<T, T, T> cache) {
    final Recursive<BinaryOperator<T>> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.apply(t, u, r.f));
  }

  /** Recursive {@link BiPredicate}. */
  public static <T, U> BiPredicate<T, U> biPredicate(RecursiveBiPredicate<T, U> f) {
    final Recursive<BiPredicate<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.test(t, u, r.f);
  }

  /** Like {@link #biPredicate}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @return recursive, cached BiPredicate */
  public static <T, U> BiPredicate<T, U> cachedBiPredicate(RecursiveBiPredicate<T, U> f) {
    return cachedBiPredicate(f, BiFunctionCache.create());
  }

  /** Like {@link #biPredicate}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached BiPredicate */
  public static <T, U> BiPredicate<T, U> cachedBiPredicate(RecursiveBiPredicate<T, U> f,
      BiFunctionCache<T, U, Boolean> cache) {
    final Recursive<BiPredicate<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.test(t, u, r.f));
  }

  /** Recursive {@link DoubleBinaryOperator}. */
  public static DoubleBinaryOperator doubleBinaryOperator(RecursiveDoubleBinaryOperator f) {
    final Recursive<DoubleBinaryOperator> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Like {@link #doubleBinaryOperator}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached DoubleBinaryOperator */
  public static DoubleBinaryOperator cachedDoubleBinaryOperator(RecursiveDoubleBinaryOperator f,
      DoubleBinaryOperatorCache cache) {
    final Recursive<DoubleBinaryOperator> r = new Recursive<>();
    return r.f = (left, right) -> cache.get(left, right, () -> f.apply(left, right, r.f));
  }

  /** Recursive {@link DoubleFunction}. */
  public static <R> DoubleFunction<R> doubleFunction(RecursiveDoubleFunction<R> f) {
    final Recursive<DoubleFunction<R>> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #doubleFunction}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached DoubleFunction */
  public static <R> DoubleFunction<R> cachedDoubleFunction(RecursiveDoubleFunction<R> f,
      DoubleFunctionCache<R> cache) {
    final Recursive<DoubleFunction<R>> r = new Recursive<>();
    return r.f = (d) -> cache.get(d, () -> f.apply(d, r.f));
  }

  /** Recursive {@link DoublePredicate}. */
  public static DoublePredicate doublePredicate(RecursiveDoublePredicate f) {
    final Recursive<DoublePredicate> r = new Recursive<>();
    return r.f = d -> f.test(d, r.f);
  }

  /** Like {@link #doublePredicate}, but using memoization.
   *
   * @param f
   *          The predicate
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached DoublePredicate */
  public static DoublePredicate cachedDoublePredicate(RecursiveDoublePredicate f,
      DoublePredicateCache cache) {
    final Recursive<DoublePredicate> r = new Recursive<>();
    return r.f = (d) -> cache.get(d, () -> f.test(d, r.f));
  }

  /** Recursive {@link DoubleToIntFunction}. */
  public static DoubleToIntFunction doubleToIntFunction(RecursiveDoubleToIntFunction f) {
    final Recursive<DoubleToIntFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #doubleToIntFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached DoubleToIntFunction */
  public static DoubleToIntFunction cachedDoubleToIntFunction(RecursiveDoubleToIntFunction f,
      DoubleToIntFunctionCache cache) {
    final Recursive<DoubleToIntFunction> r = new Recursive<>();
    return r.f = (d) -> cache.get(d, () -> f.apply(d, r.f));
  }

  /** Recursive {@link DoubleToLongFunction}. */
  public static DoubleToLongFunction doubleToLongFunction(RecursiveDoubleToLongFunction f) {
    final Recursive<DoubleToLongFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #doubleToLongFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached DoubleToLongFunction */
  public static DoubleToLongFunction cachedDoubleToLongFunction(RecursiveDoubleToLongFunction f,
      DoubleToLongFunctionCache cache) {
    final Recursive<DoubleToLongFunction> r = new Recursive<>();
    return r.f = (d) -> cache.get(d, () -> f.apply(d, r.f));
  }

  /** Recursive {@link DoubleUnaryOperator}. */
  public static DoubleUnaryOperator doubleUnaryOperator(RecursiveDoubleUnaryOperator f) {
    final Recursive<DoubleUnaryOperator> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #doubleToLongFunction}, but using memoization.
   *
   * @param f
   *          The operator
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached DoubleUnaryOperator */
  public static DoubleUnaryOperator cachedDoubleUnaryOperator(RecursiveDoubleUnaryOperator f,
      DoubleUnaryOperatorCache cache) {
    final Recursive<DoubleUnaryOperator> r = new Recursive<>();
    return r.f = (d) -> cache.get(d, () -> f.apply(d, r.f));
  }

  /** Recursive {@link Function}. */
  public static <T, R> Function<T, R> function(BiFunction<T, Function<T, R>, R> f) {
    final Recursive<Function<T, R>> r = new Recursive<>();
    return r.f = t -> f.apply(t, r.f);
  }

  /** Like {@link #function}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @return recursive, cached Function */
  public static <T, R> Function<T, R> cachedFunction(BiFunction<T, Function<T, R>, R> f) {
    return cachedFunction(f, FunctionCache.create());
  }

  /** Like {@link #function}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached Function */
  public static <T, R> Function<T, R> cachedFunction(BiFunction<T, Function<T, R>, R> f,
      FunctionCache<T, R> cache) {
    final Recursive<Function<T, R>> r = new Recursive<>();
    return r.f = t -> cache.get(t, () -> f.apply(t, r.f));
  }

  /** Recursive {@link IntBinaryOperator}. */
  public static IntBinaryOperator intBinaryOperator(RecursiveIntBinaryOperator f) {
    final Recursive<IntBinaryOperator> r = new Recursive<>();
    return r.f = (left, right) -> f.apply(left, right, r.f);
  }

  /** Like {@link #intBinaryOperator}, but using memoization.
   * <p>
   * This will convert two primitive integers to one Long object so the result can be cached in a
   * {@link Map}. That cache exists as long as the closure exists.
   *
   * @param f
   *          The function
   * @return recursive, cached IntBinaryOperator */
  public static IntBinaryOperator cachedIntBinaryOperator(RecursiveIntBinaryOperator f) {
    return cachedIntBinaryOperator(f, IntBinaryOperatorCache.create());
  }

  /** Like {@link #intBinaryOperator}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached IntBinaryOperator */
  public static IntBinaryOperator cachedIntBinaryOperator(RecursiveIntBinaryOperator f,
      IntBinaryOperatorCache cache) {
    final Recursive<IntBinaryOperator> r = new Recursive<>();
    return r.f = (left, right) -> cache.get(left, right, () -> f.apply(left, right, r.f));
  }

  /** Recursive {@link IntFunction}. */
  public static <R> IntFunction<R> intFunction(RecursiveIntFunction<R> f) {
    final Recursive<IntFunction<R>> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Like {@link #intFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntUnaryOperator */
  public static <R> IntFunction<R> cachedIntFunction(RecursiveIntFunction<R> f, //
      final int min, final int max) {
    return cachedIntFunction(f, IntFunctionCache.<R> create(min, max));
  }

  /** Like {@link #intFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached IntUnaryOperator */
  public static <R> IntFunction<R> cachedIntFunction(RecursiveIntFunction<R> f,
      IntFunctionCache<R> cache) {
    final Recursive<IntFunction<R>> r = new Recursive<>();
    return r.f = i -> cache.get(i, () -> f.apply(i, r.f));
  }

  /** Recursive {@link IntPredicate}. */
  public static IntPredicate intPredicate(RecursiveIntPredicate f) {
    final Recursive<IntPredicate> r = new Recursive<>();
    return r.f = i -> f.test(i, r.f);
  }

  /** Like {@link #intPredicate}, but using memoization.
   *
   * @param f
   *          The function
   * @return recursive, cached IntUnaryOperator */
  public static IntPredicate cachedIntPredicate(RecursiveIntPredicate f, final int min,
      final int max) {
    return cachedIntPredicate(f, IntPredicateCache.create(min, max));
  }

  /** Like {@link #intPredicate}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached IntUnaryOperator */
  public static IntPredicate cachedIntPredicate(RecursiveIntPredicate f, IntPredicateCache cache) {
    final Recursive<IntPredicate> r = new Recursive<>();
    return r.f = i -> cache.get(i, () -> f.test(i, r.f));
  }

  /** Recursive {@link IntToDoubleFunction}. */
  public static IntToDoubleFunction intToDoubleFunction(RecursiveIntToDoubleFunction f) {
    final Recursive<IntToDoubleFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #intToDoubleFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntToDoubleFunction */
  public static IntToDoubleFunction cachedIntToDoubleFunction(RecursiveIntToDoubleFunction f,
      int min, int max) {
    return cachedIntToDoubleFunction(f, IntToDoubleFunctionCache.create(min, max));
  }

  /** Like {@link #intToDoubleFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached IntToDoubleFunction */
  public static IntToDoubleFunction cachedIntToDoubleFunction(RecursiveIntToDoubleFunction f,
      IntToDoubleFunctionCache cache) {
    final Recursive<IntToDoubleFunction> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link IntToLongFunction}. */
  public static IntToLongFunction intToLongFunction(RecursiveIntToLongFunction f) {
    final Recursive<IntToLongFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #intToLongFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntToLongFunction */
  public static IntToLongFunction cachedIntToLongFunction(RecursiveIntToLongFunction f, int min,
      int max) {
    return cachedIntToLongFunction(f, IntToLongFunctionCache.create(min, max));
  }

  /** Like {@link #intToLongFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached IntToLongFunction */
  public static IntToLongFunction cachedIntToLongFunction(RecursiveIntToLongFunction f,
      IntToLongFunctionCache cache) {
    final Recursive<IntToLongFunction> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link IntUnaryOperator}. */
  public static IntUnaryOperator intUnaryOperator(RecursiveIntUnaryOperator f) {
    final Recursive<IntUnaryOperator> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Like {@link #intUnaryOperator}, but using memoization.
   *
   * @param f
   *          The operator
   * @param min
   *          the smallest possible input value
   * @param max
   *          the greatest possible input value
   * @return recursive, cached IntUnaryOperator */
  public static IntUnaryOperator cachedIntUnaryOperator(RecursiveIntUnaryOperator f, int min,
      int max) {
    return cachedIntUnaryOperator(f, IntUnaryOperatorCache.create(min, max));
  }

  /** Like {@link #intUnaryOperator}, but using memoization.
   *
   * @param f
   *          The operator
   * @param cache
   *          The cache for memoization
   * @return recursive, cached IntUnaryOperator */
  public static IntUnaryOperator cachedIntUnaryOperator(RecursiveIntUnaryOperator f,
      IntUnaryOperatorCache cache) {
    final Recursive<IntUnaryOperator> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link LongBinaryOperator}. */
  public static LongBinaryOperator longBinaryOperator(RecursiveLongBinaryOperator f) {
    final Recursive<LongBinaryOperator> r = new Recursive<>();
    return r.f = (left, right) -> f.apply(left, right, r.f);
  }

  /** Like {@link #longBinaryOperator}, but using memoization.
   *
   * @param f
   *          The operator
   * @param cache
   *          The cache for memoization
   * @return recursive, cached IntUnaryOperator */
  public static LongBinaryOperator cachedLongBinaryOperator(RecursiveLongBinaryOperator f,
      LongBinaryOperatorCache cache) {
    final Recursive<LongBinaryOperator> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.apply(t, u, r.f));
  }

  /** Recursive {@link LongFunction}. */
  public static <R> LongFunction<R> longFunction(RecursiveLongFunction<R> f) {
    final Recursive<LongFunction<R>> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Like {@link #longFunction}, but using memoization.
   *
   * @param f
   *          The funcation
   * @param cache
   *          The cache for memoization
   * @return recursive, cached LongFunction */
  public static <R> LongFunction<R> cachedLongFunction(RecursiveLongFunction<R> f,
      LongFunctionCache<R> cache) {
    final Recursive<LongFunction<R>> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link LongPredicate}. */
  public static LongPredicate longPredicate(RecursiveLongPredicate f) {
    final Recursive<LongPredicate> r = new Recursive<>();
    return r.f = i -> f.test(i, r.f);
  }

  /** Like {@link #longPredicate}, but using memoization.
   *
   * @param f
   *          The predicate
   * @param cache
   *          The cache for memoization
   * @return recursive, cached LongPredicate */
  public static LongPredicate cachedLongPredicate(RecursiveLongPredicate f, LongPredicateCache cache) {
    final Recursive<LongPredicate> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.test(v, r.f));
  }

  /** Recursive {@link LongToDoubleFunction}. */
  public static LongToDoubleFunction longToDoubleFunction(RecursiveLongToDoubleFunction f) {
    final Recursive<LongToDoubleFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #longToDoubleFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached LongToDoubleFunction */
  public static LongToDoubleFunction cachedLongPredicate(RecursiveLongToDoubleFunction f,
      LongToDoubleFunctionCache cache) {
    final Recursive<LongToDoubleFunction> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link LongToIntFunction}. */
  public static LongToIntFunction longToIntFunction(RecursiveLongToIntFunction f) {
    final Recursive<LongToIntFunction> r = new Recursive<>();
    return r.f = d -> f.apply(d, r.f);
  }

  /** Like {@link #longToIntFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached LongToIntFunction */
  public static LongToIntFunction cachedLongToIntFunction(RecursiveLongToIntFunction f,
      LongToIntFunctionCache cache) {
    final Recursive<LongToIntFunction> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link LongUnaryOperator}. */
  public static LongUnaryOperator longUnaryOperator(RecursiveLongUnaryOperator f) {
    final Recursive<LongUnaryOperator> r = new Recursive<>();
    return r.f = i -> f.apply(i, r.f);
  }

  /** Like {@link #longUnaryOperator}, but using memoization.
   *
   * @param f
   *          The operator
   * @param cache
   *          The cache for memoization
   * @return recursive, cached LongUnaryOperator */
  public static LongUnaryOperator cachedLongUnaryOperator(RecursiveLongUnaryOperator f,
      LongUnaryOperatorCache cache) {
    final Recursive<LongUnaryOperator> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link Predicate}. */
  public static <T> Predicate<T> predicate(BiPredicate<T, Predicate<T>> f) {
    final Recursive<Predicate<T>> r = new Recursive<>();
    return r.f = t -> f.test(t, r.f);
  }

  /** Like {@link #predicate}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The predicate
   * @return recursive, cached Predicate */
  public static <T> Predicate<T> cachedPredicate(BiPredicate<T, Predicate<T>> f) {
    return cachedPredicate(f, FunctionCache.create());
  }

  /** Like {@link #predicate}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The predicate
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached Predicate */
  public static <T> Predicate<T> cachedPredicate(BiPredicate<T, Predicate<T>> f,
      FunctionCache<T, Boolean> cache) {
    final Recursive<Predicate<T>> r = new Recursive<>();
    return r.f = t -> cache.get(t, () -> f.test(t, r.f));
  }

  /** Recursive {@link ToDoubleBiFunction}. */
  public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(
      RecursiveToDoubleBiFunction<T, U> f) {
    final Recursive<ToDoubleBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Like {@link #toDoubleBiFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached ToDoubleBiFunction */
  public static <T, U> ToDoubleBiFunction<T, U> cachedToDoubleBiFunction(
      RecursiveToDoubleBiFunction<T, U> f, ToDoubleBiFunctionCache<T, U> cache) {
    final Recursive<ToDoubleBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.apply(t, u, r.f));
  }

  /** Recursive {@link ToDoubleFunction}. */
  public static <T> ToDoubleFunction<T> toDoubleFunction(RecursiveToDoubleFunction<T> f) {
    final Recursive<ToDoubleFunction<T>> r = new Recursive<>();
    return r.f = t -> f.apply(t, r.f);
  }

  /** Like {@link #toDoubleFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached LongToIntFunction */
  public static <T> ToDoubleFunction<T> cachedToDoubleFunction(RecursiveToDoubleFunction<T> f,
      ToDoubleFunctionCache<T> cache) {
    final Recursive<ToDoubleFunction<T>> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link ToIntBiFunction}. */
  public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(RecursiveToIntBiFunction<T, U> f) {
    final Recursive<ToIntBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Like {@link #toIntBiFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached ToIntBiFunction */
  public static <T, U> ToIntBiFunction<T, U> cachedIntBiFunction(RecursiveToIntBiFunction<T, U> f,
      ToIntBiFunctionCache<T, U> cache) {
    final Recursive<ToIntBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.apply(t, u, r.f));
  }

  /** Recursive {@link ToIntFunction}. */
  public static <T> ToIntFunction<T> toIntFunction(RecursiveToIntFunction<T> f) {
    final Recursive<ToIntFunction<T>> r = new Recursive<>();
    return r.f = t -> f.applyAsInt(t, r.f);
  }

  /** Like {@link #toIntFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached ToIntFunction */
  public static <T> ToIntFunction<T> cachedToIntFunction(RecursiveToIntFunction<T> f,
      ToIntFunctionCache<T> cache) {
    final Recursive<ToIntFunction<T>> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link ToLongBiFunction}. */
  public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(RecursiveToLongBiFunction<T, U> f) {
    final Recursive<ToLongBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.apply(t, u, r.f);
  }

  /** Like {@link #toLongBiFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached ToLongBiFunction */
  public static <T, U> ToLongBiFunction<T, U> cachedToLongBiFunction(
      RecursiveToLongBiFunction<T, U> f, ToLongBiFunctionCache<T, U> cache) {
    final Recursive<ToLongBiFunction<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> cache.get(t, u, () -> f.apply(t, u, r.f));
  }

  /** Recursive {@link ToLongFunction}. */
  public static <T> ToLongFunction<T> toLongFunction(RecursiveToLongFunction<T> f) {
    final Recursive<ToLongFunction<T>> r = new Recursive<>();
    return r.f = t -> f.applyAsLong(t, r.f);
  }

  /** Like {@link #toLongFunction}, but using memoization.
   *
   * @param f
   *          The function
   * @param cache
   *          The cache for memoization
   * @return recursive, cached ToLongFunction */
  public static <T> ToLongFunction<T> cachedToLongFunction(RecursiveToLongFunction<T> f,
      ToLongFunctionCache<T> cache) {
    final Recursive<ToLongFunction<T>> r = new Recursive<>();
    return r.f = v -> cache.get(v, () -> f.apply(v, r.f));
  }

  /** Recursive {@link UnaryOperator}. */
  public static <T> UnaryOperator<T> unaryOperator(RecursiveUnaryOperator<T> f) {
    final Recursive<UnaryOperator<T>> r = new Recursive<>();
    return r.f = t -> f.apply(t, r.f);
  }

  /** Like {@link #unaryOperator}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The operator
   * @return recursive, cached UnaryOperator */
  public static <T> UnaryOperator<T> cachedUnaryOperator(RecursiveUnaryOperator<T> f) {
    return cachedUnaryOperator(f, FunctionCache.create());
  }

  /** Like {@link #unaryOperator}, but using memoization.
   * <p>
   * Note that the cache holds strong references to all returned values. They all live until the
   * Function is garbage collected.
   *
   * @param f
   *          The operator
   * @param cache
   *          The cache for memoization.
   * @return recursive, cached UnaryOperator */
  public static <T> UnaryOperator<T> cachedUnaryOperator(RecursiveUnaryOperator<T> f,
      FunctionCache<T, T> cache) {
    final Recursive<UnaryOperator<T>> r = new Recursive<>();
    return r.f = t -> cache.get(t, () -> f.apply(t, r.f));
  }

  /** Recursive {@link Consumer}. */
  public static <T> Consumer<T> consumer(BiConsumer<T, Consumer<T>> f) {
    final Recursive<Consumer<T>> r = new Recursive<>();
    return r.f = t -> f.accept(t, r.f);
  }

  /** Recursive {@link IntConsumer}. */
  public static IntConsumer intConsumer(RecursiveIntConsumer f) {
    final Recursive<IntConsumer> r = new Recursive<>();
    return r.f = t -> f.accept(t, r.f);
  }

  /** Recursive {@link LongConsumer}. */
  public static LongConsumer longConsumer(RecursiveLongConsumer f) {
    final Recursive<LongConsumer> r = new Recursive<>();
    return r.f = t -> f.accept(t, r.f);
  }

  /** Recursive {@link DoubleConsumer}. */
  public static DoubleConsumer longConsumer(RecursiveDoubleConsumer f) {
    final Recursive<DoubleConsumer> r = new Recursive<>();
    return r.f = t -> f.accept(t, r.f);
  }

  /** Recursive {@link BiConsumer}. */
  public static <T, U> BiConsumer<T, U> biConsumer(RecursiveBiConsumer<T, U> f) {
    final Recursive<BiConsumer<T, U>> r = new Recursive<>();
    return r.f = (t, u) -> f.accept(t, u, r.f);
  }

  /** Recursive {@link Callable}. */
  public static <T> Callable<T> callable(Function<Callable<T>, T> f) {
    final Recursive<Callable<T>> r = new Recursive<>();
    return r.f = () -> f.apply(r.f);
  }

  /** Recursive {@link Supplier}. */
  public static <T> Supplier<T> supplier(Function<Supplier<T>, T> f) {
    final Recursive<Supplier<T>> r = new Recursive<>();
    return r.f = () -> f.apply(r.f);
  }

  /** Recursive {@link IntSupplier}. */
  public static IntSupplier intSupplier(ToIntFunction<IntSupplier> f) {
    final Recursive<IntSupplier> r = new Recursive<>();
    return r.f = () -> f.applyAsInt(r.f);
  }

  /** Recursive {@link LongSupplier}. */
  public static LongSupplier longSupplier(ToLongFunction<LongSupplier> f) {
    final Recursive<LongSupplier> r = new Recursive<>();
    return r.f = () -> f.applyAsLong(r.f);
  }

  /** Recursive {@link DoubleSupplier}. */
  public static DoubleSupplier longSupplier(ToDoubleFunction<DoubleSupplier> f) {
    final Recursive<DoubleSupplier> r = new Recursive<>();
    return r.f = () -> f.applyAsDouble(r.f);
  }

  /** Recursive {@link Runnable}. */
  public static Runnable runnable(Consumer<Runnable> f) {
    final Recursive<Runnable> r = new Recursive<>();
    return r.f = () -> f.accept(r.f);
  }

  /** Holds a reference to the recursive function, predicate, operand, consumer, suppliert, callable
   * or runnable. */
  private F f;
}