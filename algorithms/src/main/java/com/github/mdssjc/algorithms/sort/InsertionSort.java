package com.github.mdssjc.algorithms.sort;

import com.github.mdssjc.algorithms.utils.SortHandles;

/**
 * InsertionSort (N² / Stable).
 *
 * @author Marcelo dos Santos
 *
 */
public class InsertionSort implements SortHandles {

  public static void sort(final Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      for (int j = i; j > 0; j--) {
        if (SortHandles.less(a[j], a[j - 1])) {
          SortHandles.exch(a, j, j - 1);
        } else {
          break;
        }
      }
    }
  }
}
