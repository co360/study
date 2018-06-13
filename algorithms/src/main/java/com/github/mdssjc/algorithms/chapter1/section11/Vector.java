package com.github.mdssjc.algorithms.chapter1.section11;

/**
 * Euclidean vector.
 * <p>
 * Compilation:  javac Vector.java
 * Execution:    java Vector
 * Dependencies: StdOut.java
 * <p>
 * Implementation of a vector of real numbers.
 * <p>
 * This class is implemented to be immutable: once the client program
 * initialize a Vector, it cannot change any of its fields
 * (d or data[i]) either directly or indirectly. Immutability is a
 * very desirable feature of a data type.
 * <p>
 * % java Vector
 *    x     = [ 1.0 2.0 3.0 4.0 ]
 *    y     = [ 5.0 2.0 4.0 1.0 ]
 *    z     = [ 6.0 4.0 7.0 5.0 ]
 *  10z     = [ 60.0 40.0 70.0 50.0 ]
 *   |x|    = 5.477225575051661
 *  <x, y>  = 25.0
 * <p>
 * <p>
 * Note that Vector is also the name of an unrelated Java library class
 * in the package java.util.
 *
 * @author Marcelo dos Santos
 *
 */
import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdOut;

/**
 *  The {@code Vector} class represents a <em>d</em>-dimensional Euclidean vector.
 *  Vectors are immutable: their values cannot be changed after they are created.
 *  It includes methods for addition, subtraction,
 *  dot product, scalar product, unit vector, Euclidean norm, and the Euclidean
 *  distance between two vectors.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
@TestDrive
public class Vector {

  private final int d;
  private final double[] data;

  /**
   * Initializes a d-dimensional zero vector.
   *
   * @param d the dimension of the vector
   */
  public Vector(final int d) {
    this.d = d;
    this.data = new double[d];
  }

  /**
   * Initializes a vector from either an array or a vararg list.
   * The vararg syntax supports a constructor that takes a variable number of
   * arugments such as Vector x = new Vector(1.0, 2.0, 3.0, 4.0).
   *
   * @param a  the array or vararg list
   */
  public Vector(final double... a) {
    this.d = a.length;

    this.data = new double[this.d];
    for (var i = 0; i < this.d; i++) {
      this.data[i] = a[i];
    }
  }

  /**
   * Returns the length of this vector.
   *
   * @return the dimension of this vector
   * @deprecated Replaced by {@link #dimension()}.
   */
  @Deprecated
  public int length() {
    return this.d;
  }

  /**
   * Returns the dimension of this vector.
   *
   * @return the dimension of this vector
   */
  public int dimension() {
    return this.d;
  }

  /**
   * Returns the do product of this vector with the specified vector.
   *
   * @param  that the other vector
   * @return the dot product of this vector and that vector
   * @throws IllegalArgumentException if the dimensions of the two vectors are not equal
   */
  public double dot(final Vector that) {
    if (this.d != that.d) {
      throw new IllegalArgumentException("Dimensions don't agree");
    }
    var sum = 0.0;
    for (var i = 0; i < this.d; i++) {
      sum += (this.data[i] * that.data[i]);
    }
    return sum;
  }

  /**
   * Returns the magnitude of this vector.
   * This is also known as the L2 norm or the Euclidean norm.
   *
   * @return the magnitude of this vector
   */
  public double magnitude() {
    return Math.sqrt(this.dot(this));
  }

  /**
   * Returns the Euclidean distance between this vector and the specified vector.
   *
   * @param  that the other vector
   * @return the Euclidean distance between this vector and that vector
   * @throws IllegalArgumentException if the dimensions of the two vectors are not equal
   */
  public double distanceTo(final Vector that) {
    if (this.d != that.d) {
      throw new IllegalArgumentException("Dimensions don't agree");
    }
    return this.minus(that)
               .magnitude();
  }

  /**
   * Returns the sum of this vector and the specified vector.
   *
   * @param  that the vector to add to this vector
   * @return the vector whose value is {@code (this + that)}
   * @throws IllegalArgumentException if the dimensions of the two vectors are not equal
   */
  public Vector plus(final Vector that) {
    if (this.d != that.d) {
      throw new IllegalArgumentException("Dimensions don't agree");
    }
    final var c = new Vector(this.d);
    for (var i = 0; i < this.d; i++) {
      c.data[i] = this.data[i] + that.data[i];
    }
    return c;
  }

  /**
   * Returns the difference between this vector and the specified vector.
   *
   * @param  that the vector to subtract from this vector
   * @return the vector whose value is {@code (this - that)}
   * @throws IllegalArgumentException if the dimensions of the two vectors are not equal
   */
  public Vector minus(final Vector that) {
    if (this.d != that.d) {
      throw new IllegalArgumentException("Dimensions don't agree");
    }
    final var c = new Vector(this.d);
    for (var i = 0; i < this.d; i++) {
      c.data[i] = this.data[i] - that.data[i];
    }
    return c;
  }

  /**
   * Returns the ith cartesian coordinate.
   *
   * @param  i the coordinate index
   * @return the ith cartesian coordinate
   */
  public double cartesian(final int i) {
    return this.data[i];
  }

  /**
   * Returns the scalar-vector product of this vector and the specified scalar
   *
   * @param  alpha the scalar
   * @return the vector whose value is {@code (alpha * this)}
   * @deprecated Replaced by {@link #scale(double)}.
   */
  @Deprecated
  public Vector times(final double alpha) {
    final var c = new Vector(this.d);
    for (var i = 0; i < this.d; i++) {
      c.data[i] = alpha * this.data[i];
    }
    return c;
  }

  /**
   * Returns the scalar-vector product of this vector and the specified scalar
   *
   * @param  alpha the scalar
   * @return the vector whose value is {@code (alpha * this)}
   */
  public Vector scale(final double alpha) {
    final var c = new Vector(this.d);
    for (var i = 0; i < this.d; i++) {
      c.data[i] = alpha * this.data[i];
    }
    return c;
  }

  /**
   * Returns a unit vector in the direction of this vector.
   *
   * @return a unit vector in the direction of this vector
   * @throws ArithmeticException if this vector is the zero vector
   */
  public Vector direction() {
    if (this.magnitude() == 0.0) {
      throw new ArithmeticException("Zero-vector has no direction");
    }
    return this.times(1.0 / this.magnitude());
  }


  /**
   * Returns a string representation of this vector.
   *
   * @return a string representation of this vector, which consists of the
   *         the vector entries, separates by single spaces
   */
  @Override
  public String toString() {
    final var s = new StringBuilder();
    for (var i = 0; i < this.d; i++) {
      s.append(this.data[i] + " ");
    }
    return s.toString();
  }

  /**
   * Unit tests the {@code Vector} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(final String[] args) {
    Executor.execute(Vector.class, args);

    final double[] xdata = {1.0, 2.0, 3.0, 4.0};
    final double[] ydata = {5.0, 2.0, 4.0, 1.0};
    final var x = new Vector(xdata);
    final var y = new Vector(ydata);

    StdOut.println("   x       = " + x);
    StdOut.println("   y       = " + y);

    var z = x.plus(y);
    StdOut.println("   z       = " + z);

    z = z.times(10.0);
    StdOut.println(" 10z       = " + z);

    StdOut.println("  |x|      = " + x.magnitude());
    StdOut.println(" <x, y>    = " + x.dot(y));
    StdOut.println("dist(x, y) = " + x.distanceTo(y));
    StdOut.println("dir(x)     = " + x.direction());
  }
}
