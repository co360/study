package com.github.mdssjc.algorithms.chapter1;

import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 1d interval.
 * <p>
 * Compilation:  javac Interval1D.java
 * Execution:    java Interval1D
 * Dependencies: StdOut.java
 * <p>
 * 1-dimensional interval data type.
 *
 * @author Marcelo dos Santos
 *
 */

/**
 *  The {@code Interval1D} class represents a one-dimensional interval.
 *  The interval is <em>closed</em>—it contains both endpoints.
 *  Intervals are immutable: their values cannot be changed after they are created.
 *  The class {@code Interval1D} includes methods for checking whether
 *  an interval contains a point and determining whether two intervals intersect.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
@TestDrive
public class Interval1D {

  /**
   * Compares two intervals by min endpoint.
   */
  public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER = new MinEndpointComparator();

  /**
   * Compares two intervals by max endpoint.
   */
  public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER = new MaxEndpointComparator();

  /**
   * Compares two intervals by length.
   */
  public static final Comparator<Interval1D> LENGTH_ORDER = new LengthComparator();

  private final double min;
  private final double max;

  /**
   * Initializes a closed interval [min, max].
   *
   * @param  min the smaller endpoint
   * @param  max the larger endpoint
   * @throws IllegalArgumentException if the min endpoint is greater than the max endpoint
   * @throws IllegalArgumentException if either {@code min} or {@code max}
   *         is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY} or
   *         {@code Double.NEGATIVE_INFINITY}
   */
  public Interval1D(double min, double max) {
    if (Double.isInfinite(min) || Double.isInfinite(max)) {
      throw new IllegalArgumentException("Endpoints must be finite");
    }
    if (Double.isNaN(min) || Double.isNaN(max)) {
      throw new IllegalArgumentException("Endpoints cannot be NaN");
    }

    if (min == 0.0) {
      min = 0.0;
    }
    if (max == 0.0) {
      max = 0.0;
    }

    if (min <= max) {
      this.min = min;
      this.max = max;
    } else {
      throw new IllegalArgumentException("Illegal interval");
    }
  }

  /**
   * Returns the left endpoint of this interval.
   *
   * @return the left endpoint of this interval
   * @deprecated Replaced by {@link #min()}.
   */
  @Deprecated
  public double left() {
    return this.min;
  }

  /**
   * Returns the right endpoint of this interval.
   * @return the right endpoint of this interval
   * @deprecated Replaced by {@link #max()}.
   */
  @Deprecated
  public double right() {
    return this.max;
  }

  /**
   * Returns the min endpoint of this interval.
   *
   * @return the min endpoint of this interval
   */
  public double min() {
    return this.min;
  }

  /**
   * Returns the max endpoint of this interval.
   *
   * @return the max endpoint of this interval
   */
  public double max() {
    return this.max;
  }

  /**
   * Returns true if this interval intersects the specified interval.
   *
   * @param  that the other interval
   * @return {@code true} if this interval intersects the argument interval;
   *         {@code false} otherwise
   */
  public boolean intersects(final Interval1D that) {
    if (this.max < that.min) {
      return false;
    }
    if (that.max < this.min) {
      return false;
    }
    return true;
  }

  /**
   * Returns true if this interval contains the specified value.
   *
   * @param x the value
   * @return {@code true} if this interval contains the value {@code x};
   *         {@code false} otherwise
   */
  public boolean contains(final double x) {
    return (this.min <= x) && (x <= this.max);
  }

  /**
   * Returns the length of this interval.
   *
   * @return the length of this interval (max - min)
   */
  public double length() {
    return this.max - this.min;
  }

  /**
   * Returns a string representation of this interval.
   *
   * @return a string representation of this interval in the form [min, max]
   */
  @Override
  public String toString() {
    return "[" + this.min + ", " + this.max + "]";
  }

  /**
   * Compares this transaction to the specified object.
   *
   * @param  other the other interval
   * @return {@code true} if this interval equals the other interval;
   *         {@code false} otherwise
   */
  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other.getClass() != this.getClass()) {
      return false;
    }
    final var that = (Interval1D) other;
    return this.min == that.min && this.max == that.max;
  }

  /**
   * Returns an integer hash code for this interval.
   *
   * @return an integer hash code for this interval
   */
  @Override
  public int hashCode() {
    final var hash1 = ((Double) this.min).hashCode();
    final var hash2 = ((Double) this.max).hashCode();
    return 31 * hash1 + hash2;
  }

  private static class MinEndpointComparator implements Comparator<Interval1D> {

    @Override
    public int compare(final Interval1D a, final Interval1D b) {
      if (a.min < b.min) {
        return -1;
      } else if (a.min > b.min) {
        return +1;
      } else if (a.max < b.max) {
        return -1;
      } else if (a.max > b.max) {
        return +1;
      } else {
        return 0;
      }
    }
  }

  private static class MaxEndpointComparator implements Comparator<Interval1D> {

    @Override
    public int compare(final Interval1D a, final Interval1D b) {
      if (a.max < b.max) {
        return -1;
      } else if (a.max > b.max) {
        return +1;
      } else if (a.min < b.min) {
        return -1;
      } else if (a.min > b.min) {
        return +1;
      } else {
        return 0;
      }
    }
  }

  private static class LengthComparator implements Comparator<Interval1D> {

    @Override
    public int compare(final Interval1D a, final Interval1D b) {
      final var alen = a.length();
      final var blen = b.length();
      if (alen < blen) {
        return -1;
      } else if (alen > blen) {
        return +1;
      } else {
        return 0;
      }
    }
  }

  /**
   * Unit tests the {@code Interval1D} data type.
   *
   * @param args
   *     the command-line arguments
   */
  public static void main(final String[] args) {
    Executor.execute(Interval1D.class, args);

    final var intervals = new Interval1D[4];
    intervals[0] = new Interval1D(15.0, 33.0);
    intervals[1] = new Interval1D(45.0, 60.0);
    intervals[2] = new Interval1D(20.0, 70.0);
    intervals[3] = new Interval1D(46.0, 55.0);

    StdOut.println("Unsorted");
    for (var i = 0; i < intervals.length; i++) {
      StdOut.println(intervals[i]);
    }
    StdOut.println();

    StdOut.println("Sort by min endpoint");
    Arrays.sort(intervals, Interval1D.MIN_ENDPOINT_ORDER);
    for (var i = 0; i < intervals.length; i++) {
      StdOut.println(intervals[i]);
    }
    StdOut.println();

    StdOut.println("Sort by max endpoint");
    Arrays.sort(intervals, Interval1D.MAX_ENDPOINT_ORDER);
    for (var i = 0; i < intervals.length; i++) {
      StdOut.println(intervals[i]);
    }
    StdOut.println();

    StdOut.println("Sort by length");
    Arrays.sort(intervals, Interval1D.LENGTH_ORDER);
    for (var i = 0; i < intervals.length; i++) {
      StdOut.println(intervals[i]);
    }
    StdOut.println();
  }
}
