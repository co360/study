package com.github.mdssjc.algorithms.chapter1.exercises11;

import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdOut;

/**
 * Exercise 1.1.12.
 *
 * @author Marcelo dos Santos
 *
 */
@TestDrive
public class Ex12 {

  public static void main(final String[] args) {
    Executor.execute(Ex12.class, args);

    final int[] a = new int[10];
    for (int i = 0; i < 10; i++) {
      a[i] = 9 - i;
    }
    for (int i = 0; i < 10; i++) {
      a[i] = a[a[i]];
    }
    for (int i = 0; i < 10; i++) {
      StdOut.println(a[i]);
    }
  }
}
