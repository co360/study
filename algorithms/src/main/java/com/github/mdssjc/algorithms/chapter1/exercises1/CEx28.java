package com.github.mdssjc.algorithms.chapter1.exercises1;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import com.github.mdssjc.algorithms.chapter1.section1.BinarySearchRecursive;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Creative Exercício 28.
 * <p>
 * Remove duplicates.
 * 
 * @author Marcelo dos Santos
 *
 */
public class CEx28 {

  public static void main(final String[] args) {
    int[] whitelist = IntStream.generate(() -> ThreadLocalRandom.current()
      .nextInt(5))
      .limit(50)
      .toArray();

    StdOut.println("Length: " + whitelist.length);
    whitelist = Arrays.stream(whitelist)
      .sorted()
      .distinct()
      .toArray();
    StdOut.println("Length: " + whitelist.length);

    while (!StdIn.isEmpty()) {
      final int key = StdIn.readInt();
      if (BinarySearchRecursive.rank(key, whitelist) == -1) {
        StdOut.println(key);
      }
    }
  }
}
