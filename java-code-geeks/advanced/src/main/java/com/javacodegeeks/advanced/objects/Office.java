package com.javacodegeeks.advanced.objects;

import java.util.Arrays;

public class Office {

  private Person[] persons;

  public Office(Person... persons) {
    this.persons = Arrays.copyOf(persons, persons.length);
  }

  @Override
  public String toString() {
    return String.format("%s{persons=%s}",
        getClass().getSimpleName(), Arrays.toString(persons));
  }

  @Override
  public Office clone() throws CloneNotSupportedException {
    final Office clone = (Office) super.clone();
    clone.persons = persons.clone();
    return clone;
  }

  public Person[] getPersons() {
    return persons;
  }
}
