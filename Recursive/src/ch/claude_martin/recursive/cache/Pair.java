package ch.claude_martin.recursive.cache;

import static java.util.Arrays.asList;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.StreamSupport;

// comparable because some maps (TreeMap) need comparable key elements.
final class Pair<F, S> implements Comparable<Pair<F, S>> {
  public final F first;
  public final S second;

  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public int hashCode() {
    return ((this.first == null) ? 0 : this.first.hashCode())
        ^ ((this.second == null) ? 0 : this.second.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Pair))
      return false;
    @SuppressWarnings("rawtypes")
    final Pair other = (Pair) obj;
    return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
  }

  @Override
  public String toString() {
    return "(" + this.first + ", " + this.second + ")";
  }

  public F _1() {
    return this.first;
  }

  public S _2() {
    return this.second;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final static Comparator<Pair> comparator = (Comparator<Pair>) (Object) getComparator();

  private static <A extends Comparable<A>, B extends Comparable<B>> Comparator<Pair<A, B>> getComparator() {
    return Comparator.<Pair<A, B>, A> comparing(Pair::_1,
        Comparator.nullsFirst(Comparator.naturalOrder()))//
        .thenComparing(
            Comparator.<Pair<A, B>, B> comparing(Pair::_2,
                Comparator.nullsFirst(Comparator.naturalOrder())));
  }

  @Override
  public int compareTo(final Pair<F, S> o) {
    return Objects.compare(this, o, comparator);
  }

  /** Are both values comparable? */
  public boolean isComparable() {
    return (this.first == null || (this.first instanceof Comparable))
        && (this.second == null || (this.second instanceof Comparable));
  }

}
