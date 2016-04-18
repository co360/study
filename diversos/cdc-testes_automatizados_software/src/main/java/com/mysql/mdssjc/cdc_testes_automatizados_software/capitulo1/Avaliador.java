package com.mysql.mdssjc.cdc_testes_automatizados_software.capitulo1;

public class Avaliador {

  private double maiorDeTodos = Double.NEGATIVE_INFINITY;
  private double menorDeTodos = Double.POSITIVE_INFINITY;

  public void avalia(Leilao leilao) {
    for (Lance lance : leilao.getLances()) {
      if (lance.getValor() > maiorDeTodos) {
        maiorDeTodos = lance.getValor();
      } else if (lance.getValor() < menorDeTodos) {
        menorDeTodos = lance.getValor();
      }
    }
  }

  public double getMaiorLance() {
    return maiorDeTodos;
  }

  public double getMenorDeTodos() {
    return menorDeTodos;
  }
}
