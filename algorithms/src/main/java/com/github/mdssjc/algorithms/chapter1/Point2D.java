package com.github.mdssjc.algorithms.chapter1;

import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Point.
 * <p>
 * Compilation:  javac Point2D.java
 * Execution:    java Point2D x0 y0 n
 * Dependencies: StdDraw.java StdRandom.java
 * <p>
 * Immutable point data type for points in the plane.
 *
 * @author Marcelo dos Santos
 *
 */

/**
 * The {@code Point} class is an immutable data type to encapsulate a
 * two-dimensional point with real-value coordinates.
 * <p>
 * Note: in order to deal with the difference behavior of double and
 * Double with respect to -0.0 and +0.0, the Point2D constructor converts
 * any coordinates that are -0.0 to +0.0.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
@TestDrive({"10", "10", "100"})
public final class Point2D implements Comparable<Point2D> {

  /**
   * Compares two points by x-coordinate.
   */
  public static final Comparator<Point2D> X_ORDER = new XOrder();

  /**
   * Compares two points by y-coordinate.
   */
  public static final Comparator<Point2D> Y_ORDER = new YOrder();

  /**
   * Compares two points by polar radius.
   */
  public static final Comparator<Point2D> R_ORDER = new ROrder();

  private final double x;
  private final double y;

  /**
   * Initializes a new point (x, y).
   *
   * @param x
   *     the x-coordinate
   * @param y
   *     the y-coordinate
   *
   * @throws IllegalArgumentException
   *     if either {@code x} or {@code y}
   *     is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY} or
   *     {@code Double.NEGATIVE_INFINITY}
   */
  public Point2D(final double x, final double y) {
    if (Double.isInfinite(x) || Double.isInfinite(y)) {
      throw new IllegalArgumentException("Coordinates must be finite");
    }
    if (Double.isNaN(x) || Double.isNaN(y)) {
      throw new IllegalArgumentException("Coordinates cannot be NaN");
    }
    if (x == 0.0) {
      this.x = 0.0;
    } else {
      this.x = x;
    }

    if (y == 0.0) {
      this.y = 0.0;
    } else {
      this.y = y;
    }
  }

  /**
   * Returns the x-coordinate.
   *
   * @return the x-coordinate
   */
  public double x() {
    return this.x;
  }

  /**
   * Returns the y-coordinate.
   *
   * @return the y-coordinate
   */
  public double y() {
    return this.y;
  }

  /**
   * Returns the polar radius of this point.
   *
   * @return the polar radius of this point in polar coordiantes: sqrt(x*x + y*y)
   */
  public double r() {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  /**
   * Returns the angle of this point in polar coordinates.
   *
   * @return the angle (in radians) of this point in polar coordiantes (between
   * –&pi; and &pi;)
   */
  public double theta() {
    return Math.atan2(this.y, this.x);
  }

  /**
   * Returns the angle between this point and that point.
   *
   * @return the angle in radians (between –&pi; and &pi;) between this point
   * and that point (0 if equal)
   */
  private double angleTo(final Point2D that) {
    final var dx = that.x - this.x;
    final var dy = that.y - this.y;
    return Math.atan2(dy, dx);
  }

  /**
   * Returns true if a→b→c is a counterclockwise turn.
   *
   * @param a
   *     first point
   * @param b
   *     second point
   * @param c
   *     third point
   *
   * @return { -1, 0, +1 } if a→b→c is a { clockwise, collinear; counterclocwise } turn.
   */
  public static int ccw(final Point2D a, final Point2D b, final Point2D c) {
    final var area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    if (area2 < 0) {
      return -1;
    } else if (area2 > 0) {
      return +1;
    } else {
      return 0;
    }
  }

  /**
   * Returns twice the signed area of the triangle a-b-c.
   *
   * @param a
   *     first point
   * @param b
   *     second point
   * @param c
   *     third point
   *
   * @return twice the signed area of the triangle a-b-c
   */
  public static double area2(final Point2D a, final Point2D b, final Point2D c) {
    return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
  }

  /**
   * Returns the Euclidean distance between this point and that point.
   *
   * @param that
   *     the other point
   *
   * @return the Euclidean distance between this point and that point
   */
  public double distanceTo(final Point2D that) {
    final var dx = this.x - that.x;
    final var dy = this.y - that.y;
    return Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Returns the square of the Euclidean distance between this point and that
   * point.
   *
   * @param that
   *     the other point
   *
   * @return the square of the Euclidean distance between this point and that point
   */
  public double distanceSquaredTo(final Point2D that) {
    final var dx = this.x - that.x;
    final var dy = this.y - that.y;
    return dx * dx + dy * dy;
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate.
   * Formally, the invoking point (x0, y0) is less than the argument point (x1,
   * y1)
   * if and only if either {@code y0 < y1} or if {@code y0 == y1} and {@code x0
   * < x1}.
   *
   * @param that
   *     the other point
   *
   * @return the value {@code 0} if this string is equal to the argument
   * string (precisely when {@code equals()} returns {@code true});
   * a negative integer if this point is less than the argument
   * point; and a positive integer if this point is greater than the
   * argument point
   */
  @Override
  public int compareTo(final Point2D that) {
    if (this.y < that.y) {
      return -1;
    }
    if (this.y > that.y) {
      return +1;
    }
    if (this.x < that.x) {
      return -1;
    }
    if (this.x > that.x) {
      return +1;
    }
    return 0;
  }

  /**
   * Compares two points by polar angle (between 0 and 2&pi;) with respect to
   * this point.
   *
   * @return the comparator
   */
  public Comparator<Point2D> polarOrder() {
    return new PolarOrder();
  }

  /**
   * Compares two points by atan2() angle (between –&pi; and &pi;) with respect
   * to this point.
   *
   * @return the comparator
   */
  public Comparator<Point2D> atan2Order() {
    return new Atan2Order();
  }

  /**
   * Compares two points by distance to this point.
   *
   * @return the comparator
   */
  public Comparator<Point2D> distanceToOrder() {
    return new DistanceToOrder();
  }

  private static class XOrder implements Comparator<Point2D> {

    @Override
    public int compare(final Point2D p, final Point2D q) {
      if (p.x < q.x) {
        return -1;
      }
      if (p.x > q.x) {
        return +1;
      }
      return 0;
    }
  }

  private static class YOrder implements Comparator<Point2D> {

    @Override
    public int compare(final Point2D p, final Point2D q) {
      if (p.y < q.y) {
        return -1;
      }
      if (p.y > q.y) {
        return +1;
      }
      return 0;
    }
  }

  private static class ROrder implements Comparator<Point2D> {

    @Override
    public int compare(final Point2D p, final Point2D q) {
      final var delta = (p.x * p.x + p.y * p.y) - (q.x * q.x + q.y * q.y);
      if (delta < 0) {
        return -1;
      }
      if (delta > 0) {
        return +1;
      }
      return 0;
    }
  }

  private class Atan2Order implements Comparator<Point2D> {

    @Override
    public int compare(final Point2D q1, final Point2D q2) {
      final var angle1 = angleTo(q1);
      final var angle2 = angleTo(q2);
      if (angle1 < angle2) {
        return -1;
      } else if (angle1 > angle2) {
        return +1;
      } else {
        return 0;
      }
    }
  }

  private class PolarOrder implements Comparator<Point2D> {

    @Override
    public int compare(final Point2D q1, final Point2D q2) {
      final var dx1 = q1.x - Point2D.this.x;
      final var dy1 = q1.y - Point2D.this.y;
      final var dx2 = q2.x - Point2D.this.x;
      final var dy2 = q2.y - Point2D.this.y;

      if (dy1 >= 0 && dy2 < 0) {
        return -1;
      } else if (dy2 >= 0 && dy1 < 0) {
        return +1;
      } else if (dy1 == 0 && dy2 == 0) {
        if (dx1 >= 0 && dx2 < 0) {
          return -1;
        } else if (dx2 >= 0 && dx1 < 0) {
          return +1;
        } else {
          return 0;
        }
      } else {
        return -ccw(Point2D.this, q1, q2);
      }
    }
  }

  private class DistanceToOrder implements Comparator<Point2D> {

    @Override
    public int compare(final Point2D p, final Point2D q) {
      final var dist1 = distanceSquaredTo(p);
      final var dist2 = distanceSquaredTo(q);
      if (dist1 < dist2) {
        return -1;
      } else if (dist1 > dist2) {
        return +1;
      } else {
        return 0;
      }
    }
  }

  /**
   * Compares this point to the specified point.
   *
   * @param other
   *     the other point
   *
   * @return {@code true} if this point equals {@code other};
   * {@code false} otherwise
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
    final var that = (Point2D) other;
    return this.x == that.x && this.y == that.y;
  }

  /**
   * Return a string representation of this point.
   *
   * @return a string representation of this point in the format (x, y)
   */
  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }

  /**
   * Returns an integer hash code for this point.
   *
   * @return an integer hash code for this point
   */
  @Override
  public int hashCode() {
    final var hashX = ((Double) this.x).hashCode();
    final var hashY = ((Double) this.y).hashCode();
    return 31 * hashX + hashY;
  }

  /**
   * Plot this point using standard draw.
   */
  public void draw() {
    StdDraw.point(this.x, this.y);
  }

  /**
   * Plot a line from this point to that point using standard draw.
   *
   * @param that
   *     the other point
   */
  public void drawTo(final Point2D that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Unit tests the point data type.
   *
   * @param args
   *     the command-line arguments
   */
  public static void main(final String[] args) {
    Executor.execute(Point2D.class, args);

    final var x0 = Integer.parseInt(args[0]);
    final var y0 = Integer.parseInt(args[1]);
    final var n = Integer.parseInt(args[2]);

    StdDraw.setCanvasSize(800, 800);
    StdDraw.setXscale(0, 100);
    StdDraw.setYscale(0, 100);
    StdDraw.setPenRadius(0.005);
    StdDraw.enableDoubleBuffering();

    final var points = new Point2D[n];
    for (var i = 0; i < n; i++) {
      final var x = StdRandom.uniform(100);
      final var y = StdRandom.uniform(100);
      points[i] = new Point2D(x, y);
      points[i].draw();
    }

    final var p = new Point2D(x0, y0);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setPenRadius(0.02);
    p.draw();

    StdDraw.setPenRadius();
    StdDraw.setPenColor(StdDraw.BLUE);
    Arrays.sort(points, p.polarOrder());
    for (var i = 0; i < n; i++) {
      p.drawTo(points[i]);
      StdDraw.show();
      StdDraw.pause(100);
    }
  }
}
