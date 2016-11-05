package com.github.mdssjc.algorithms.chapter1.exercises11;

import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

/**
 * Exercício 19.
 *
 * @author Marcelo dos Santos
 *
 */
@TestDrive
public class Ex19 {

  public static void main(final String[] args) {
    Executor.execute(Ex19.class, args);

    for (int N = 0; N < 100; N++) {
      StdOut.println(N + " " + Fibonacci.F(N));
    }
  }
}

class Fibonacci {

  private static final Map<Integer, Long> cache = new HashMap<>();

  public static long F(final int N) {
    if (N == 0) {
      return 0;
    }
    if (N == 1) {
      return 1;
    }

    if (Fibonacci.cache.containsKey(N)) {
      return Fibonacci.cache.get(N);
    }
    final long value = F(N - 1) + F(N - 2);
    Fibonacci.cache.put(N, value);
    return value;
  }
}
