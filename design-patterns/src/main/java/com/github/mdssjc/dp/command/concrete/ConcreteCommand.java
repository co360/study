package com.github.mdssjc.dp.command.concrete;

import com.github.mdssjc.dp.command.Command;
import com.github.mdssjc.dp.command.receiver.Receiver;

import lombok.AllArgsConstructor;

/**
 * Classe Command.
 *
 * @author Marcelo dos Santos
 *
 */
@AllArgsConstructor
public class ConcreteCommand implements Command {

  private final Receiver receiver;

  @Override
  public void execute() {
    this.receiver.message();
  }

  @Override
  public void undo() {
  }

  @Override
  public void store() {
  }

  @Override
  public void load() {
  }
}
