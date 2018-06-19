package com.github.mdssjc.algorithms.chapter1.section0;

import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Running average and stddev.
 * <p>
 * Compilation:  javac Accumulator.java
 * Execution:    java Accumulator < input.txt
 * Dependencies: StdOut.java StdIn.java
 * <p>
 * Mutable data type that calculates the mean, sample standard
 * deviation, and sample variance of a stream of real numbers
 * use a stable, one-pass algorithm.
 *
 * @author Marcelo dos Santos
 *
 */

/**
 * The {@code Accumulator} class is a data type for computing the running
 * mean, sample standard deviation, and sample variance of a stream of real
 * numbers. It provides an example of a mutable data type and a streaming
 * algorithm.
 * <p>
 * This implementation uses a one-pass algorithm that is less susceptible
 * to floating-point roundoff error than the more straightforward
 * implementation based on saving the sum of the squares of the numbers.
 * This technique is due to
 * <a href = "https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Online_algorithm">B.
 * P. Welford</a>.
 * Each operation takes constant time in the worst case.
 * The amount of memory is constant - the data values are not stored.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
@TestDrive(input = "input.txt", inputFile = true)
public class Accumulator {

  private int n = 0;
  private double sum = 0.0;
  private double mu = 0.0;

  /**
   * Initializes an accumulator.
   */
  public Accumulator() {
  }

  /**
   * Adds the specified data value to the accumulator.
   *
   * @param x
   *     the data value
   */
  public void addDataValue(final double x) {
    this.n++;
    final var delta = x - this.mu;
    this.mu += delta / this.n;
    this.sum += (double) (this.n - 1) / this.n * delta * delta;
  }

  /**
   * Returns the mean of the data values.
   *
   * @return the mean of the data values
   */
  public double mean() {
    return this.mu;
  }

  /**
   * Returns the sample variance of the data values.
   *
   * @return the sample variance of the data values
   */
  public double var() {
    if (this.n <= 1) {
      return Double.NaN;
    }
    return this.sum / (this.n - 1);
  }

  /**
   * Returns the sample standard deviation of the data values.
   *
   * @return the sample standard deviation of the data values
   */
  public double stddev() {
    return Math.sqrt(this.var());
  }

  /**
   * Returns the number of data values.
   *
   * @return the number of data values
   */
  public int count() {
    return this.n;
  }

  /**
   * Returns a string representation of this accumulator.
   *
   * @return a string representation of this accumulator
   */
  @Override
  public String toString() {
    return "n = " + this.n + ", mean = " + mean() + ", stddev = " + stddev();
  }

  /**
   * Unit tests the {@code Accumulator} data type.
   * Reads in a stream of real number from standard input;
   * adds them to the accumulator; and prints the mean,
   * sample standard deviation, and sample variance to standard
   * output.
   *
   * @param args
   *     the command-line arguments
   */
  public static void main(final String[] args) {
    Executor.execute(Accumulator.class, args);

    final var stats = new Accumulator();
    while (!StdIn.isEmpty()) {
      final double x = StdIn.readDouble();
      stats.addDataValue(x);
    }

    StdOut.printf("n      = %d%n", stats.count());
    StdOut.printf("mean   = %.5f%n", stats.mean());
    StdOut.printf("stddev = %.5f%n", stats.stddev());
    StdOut.printf("var    = %.5f%n", stats.var());
    StdOut.println(stats);
  }
}
