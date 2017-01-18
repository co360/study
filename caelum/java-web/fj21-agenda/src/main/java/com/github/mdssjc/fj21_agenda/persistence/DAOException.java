package com.github.mdssjc.fj21_agenda.persistence;

@SuppressWarnings( "serial" )
public class DAOException extends Exception {

  public DAOException() {
  }

  public DAOException(final String message) {
    super(message);
  }

  public DAOException(final Throwable cause) {
    super(cause);
  }

  public DAOException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
