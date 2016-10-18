package com.github.mdssjc.dp.template_method.base;

import com.github.mdssjc.dp.template_method.application.ApplicationClassA;
import com.github.mdssjc.dp.template_method.application.ApplicationClassB;

/**
 * Test drive do padrão de projeto Template Method.
 * <p>
 * Design Pattern
 * Behavioral - Template Method
 *
 * @author Marcelo dos Santos
 *
 */
public class Main {

  public static void main(String[] args) {
    ApplicationClassA appA = new ApplicationClassA();
    ApplicationClassB appB = new ApplicationClassB();

    appA.templateMethod();
    appB.templateMethod();
  }
}
