package com.github.mdssjc.remote;

public class LightOffCommand implements Command {

  private final Light light;

  public LightOffCommand(final Light light) {
    this.light = light;
  }

  @Override
  public void execute() {
    this.light.off();
  }

  @Override
  public void undo() {
    this.light.on();
  }
}
