package com.github.mdssjc.algorithms.chapter1.exercises12;

/**
 * RationalCEx17 Class.
 *
 * @author Marcelo dos Santos
 *
 */
class RationalCEx17 implements Comparable<RationalCEx17> {

  private static final RationalCEx17 zero = new RationalCEx17(0, 1);

  private long num;   // the numerator
  private long den;   // the denominator

  // create and initialize a new Rational object
  public RationalCEx17(final long numerator, final long denominator) {
    // deal with x/0
    if (denominator == 0) {
      throw new ArithmeticException("denominator is zero");
    }

    // reduce fraction
    final long g = gcd(numerator, denominator);
    this.num = numerator / g;
    this.den = denominator / g;

    // only needed for negative numbers
    if (this.den < 0) {
      this.den = -this.den;
      this.num = -this.num;
    }
  }

  // return the numerator and denominator of this rational number
  public long numerator() {
    return this.num;
  }

  public long denominator() {
    return this.den;
  }

  // return double precision representation of this rational number
  public double toDouble() {
    return (double) this.num / this.den;
  }

  // return string representation of this rational number
  public String toString() {
    if (this.den == 1) {
      return this.num + "";
    } else return this.num + "/" + this.den;
  }

  // return { -1, 0, +1 } if this < that, this = that, or this > that
  public int compareTo(final RationalCEx17 that) {
    final long lhs = this.num * that.den;
    final long rhs = this.den * that.num;
    if (lhs < rhs) {
      return -1;
    }
    if (lhs > rhs) {
      return +1;
    }
    return 0;
  }

  // is this RationalCEx17 object equal to other?
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }
    if (other.getClass() != this.getClass()) {
      return false;
    }
    final RationalCEx17 that = (RationalCEx17) other;
    return this.compareTo(that) == 0;
  }

  // hashCode consistent with equals() and compareTo()
  public int hashCode() {
    return this.toString()
               .hashCode();
  }

  // return this * that, staving off overflow as much as possible by cross-cancellation
  public RationalCEx17 times(final RationalCEx17 that) {
    // reduce p1/q2 and p2/q1, then multiply, where a = p1/q1 and b = p2/q2
    final RationalCEx17 c = new RationalCEx17(this.num, that.den);
    final RationalCEx17 d = new RationalCEx17(that.num, this.den);
    return new RationalCEx17(c.num * d.num, c.den * d.den);
  }

  // return this + that, staving off overflow
  public RationalCEx17 plus(final RationalCEx17 that) {
    // special cases
    if (this.compareTo(zero) == 0) {
      return that;
    }
    if (that.compareTo(zero) == 0) {
      return this;
    }

    // Find gcd of numerators and denominators
    final long f = gcd(this.num, that.num);
    final long g = gcd(this.den, that.den);

    // add cross-product terms for numerator
    final RationalCEx17 s = new RationalCEx17((this.num / f) * (that.den / g)
                                                  + (that.num / f) * (this.den / g),
                                              this.den * (that.den / g));

    // multiply back in
    s.num *= f;
    return s;
  }

  // return -this
  public RationalCEx17 negate() {
    return new RationalCEx17(-this.num, this.den);
  }

  // return this - that
  public RationalCEx17 minus(final RationalCEx17 that) {
    return this.plus(that.negate());
  }

  public RationalCEx17 reciprocal() {
    return new RationalCEx17(this.den, this.num);
  }

  // return this / that
  public RationalCEx17 dividedBy(final RationalCEx17 that) {
    return this.times(that.reciprocal());
  }

  // create and return a new rational (r.num + s.num) / (r.den + s.den)
  public static RationalCEx17 mediant(final RationalCEx17 r, final RationalCEx17 s) {
    return new RationalCEx17(r.num + s.num, r.den + s.den);
  }

  // return gcd(|m|, |n|)
  private static long gcd(long m, long n) {
    if (m < 0) {
      m = -m;
    }
    if (n < 0) {
      n = -n;
    }
    if (0 == n) {
      return m;
    } else {
      return gcd(n, m % n);
    }
  }

  // return lcm(|m|, |n|)
  private static long lcm(long m, long n) {
    if (m < 0) {
      m = -m;
    }
    if (n < 0) {
      n = -n;
    }
    return m * (n / gcd(m, n));    // parentheses important to avoid overflow
  }
}
