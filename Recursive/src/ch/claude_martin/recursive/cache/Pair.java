package ch.claude_martin.recursive.cache;

import java.util.Objects;

final class Pair<T1, T2> {
  public final T1 first;
  public final T2 second;

  public Pair(T1 first, T2 second) {
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
    Pair other = (Pair) obj;
    return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
  }

  @Override
  public String toString() {
    return "(" + this.first + ", " + this.second + ")";
  }

  public T1 _1() {
    return this.first;
  }

  public T2 _2() {
    return this.second;
  }

}
