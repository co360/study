package com.github.mdssjc.cdc.tas.capitulo1;

import java.util.List;

import org.hibernate.Session;

public class LeilaoDao implements RepositorioDeLeiloes {

  private final Session session;

  public LeilaoDao(final Session session) {
    this.session = session;
  }

  @Override
  public void salvar(final Leilao leilao) {
    this.session.save(leilao);
  }

  @Override
  public List<Leilao> encerrados() {
    return null;
  }

  @Override
  public List<Leilao> correntes() {
    return null;
  }

  @Override
  public void atualiza(final Leilao leilao) {
  }

  public Long total() {
    return (Long) this.session.createQuery(
        "select count(1) from Leilao l where l.encerrado = false")
                              .uniqueResult();
  }
}
