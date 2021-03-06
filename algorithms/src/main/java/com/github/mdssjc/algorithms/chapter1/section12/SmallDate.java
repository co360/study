package com.github.mdssjc.algorithms.chapter1.section12;

import com.github.mdssjc.algorithms.utils.Executor;
import com.github.mdssjc.algorithms.utils.TestDrive;
import edu.princeton.cs.algs4.StdOut;

/**
 * Small Date: alternate implementation.
 *
 * @author Marcelo dos Santos
 *
 */
@TestDrive({"12", "31", "1999"})
public class SmallDate implements Datable {

  private final int value;

  public SmallDate(final int m, final int d, final int y) {
    this.value = y * 512 + m * 32 + d;
  }

  public static void main(final String[] args) {
    Executor.execute(SmallDate.class, args);

    final int m = Integer.parseInt(args[0]);
    final int d = Integer.parseInt(args[1]);
    final int y = Integer.parseInt(args[2]);
    final Datable date = new SmallDate(m, d, y);
    StdOut.println(date);
  }

  @Override
  public int month() {
    return (this.value / 32) % 16;
  }

  @Override
  public int day() {
    return this.value % 32;
  }

  @Override
  public int year() {
    return this.value / 512;
  }

  @Override
  public String toString() {
    return month() + "/" + day() + "/" + year();
  }
}
