package com.github.mdssjc.hfooad.types;

public enum Builder {

  FENDER("Fender"),
  MARTIN("Martin"),
  GIBSON("Gibson"),
  COLLINGS("Collings"),
  OLSON("Olson"),
  RYAN("Ryan"),
  PRS("PRS"),
  ANY("Unspecified");

  String value;

  Builder(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
