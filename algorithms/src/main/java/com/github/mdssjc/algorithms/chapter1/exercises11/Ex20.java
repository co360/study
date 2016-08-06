package com.github.mdssjc.algorithms.chapter1.exercises11;

import edu.princeton.cs.algs4.StdOut;

/**
 * Exercício 20.
 * 
 * @author Marcelo dos Santos
 *
 */
public class Ex20 {

  public static void main(final String[] args) {
    StdOut.println(Math.log(86) + " " + ln(86));
    StdOut.println(Math.log(3) + " " + ln(3));
    StdOut.println(Math.log(2) + " " + ln(2));
    StdOut.println(Math.log(1) + " " + ln(1));
  }

  public static double ln(final int n) {
    if (n <= 1) {
      return 0;
    }
    return log(0, n) * 0.7;
  }

  private static double log(final double acc, final int n) {
    if (Math.pow(2, acc) >= n) {
      return acc;
    }
    return log(acc + 0.01, n);
  }
}
