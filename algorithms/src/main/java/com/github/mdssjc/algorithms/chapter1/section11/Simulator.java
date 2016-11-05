package com.github.mdssjc.algorithms.chapter1.section11;

import com.github.mdssjc.algorithms.utils.Executor;

/**
 * Simulator Class.
 *
 * @author Marcelo dos Santos
 *
 */
public class Simulator {

  public static void main(final String[] args) {
    Executor.execute(BinarySearch.class);
    Executor.execute(BinarySearchRecursive.class);
    Executor.execute(RandomSeq.class);
  }
}
