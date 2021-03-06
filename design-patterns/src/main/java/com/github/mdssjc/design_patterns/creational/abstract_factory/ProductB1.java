package com.github.mdssjc.design_patterns.creational.abstract_factory;

/**
 * Concrete Product.
 *
 * @author Marcelo dos Santos
 *
 */
public class ProductB1 implements AbstractProductB {

  @Override
  public void message() {
    System.out.println("Concrete Product B1");
  }
}
