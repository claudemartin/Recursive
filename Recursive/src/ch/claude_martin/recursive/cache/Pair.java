package ch.claude_martin.recursive.cache;

import java.util.Objects;

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

  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(Pair<F, S> o) {
    if (this.first instanceof Comparable) {
      final int k = ((Comparable<F>) this.first).compareTo(o.first);
      if (k > 0)
        return 1;
      if (k < 0)
        return -1;
    }

    if (this.second instanceof Comparable) {
      final int k = ((Comparable<S>) this.second).compareTo(o.second);
      if (k > 0)
        return 1;
      if (k < 0)
        return -1;
    }

    return 0;
  }

}
