package ch.claude_martin.recursive.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface ToLongBiFunctionCache<T, U> {
  public long get(T t, U u, LongSupplier supplier);

  /** This will automatically create a pair (2-tuple) of both arguments. You simply provide any kind
   * of map. */
  public static <T, U> ToLongBiFunctionCache<T, U> create(Supplier<Map<Object, Long>> ctor) {
    return (t, u, s) -> ctor.get().computeIfAbsent(new Pair<>(t, u), key -> s.getAsLong());
  }

  public static <T, U> ToLongBiFunctionCache<T, U> create() {
    return create(HashMap::new);
  }

}
