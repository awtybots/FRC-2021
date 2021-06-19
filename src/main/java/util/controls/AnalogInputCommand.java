package util.controls;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class AnalogInputCommand extends CommandBase {

  public Controller controller;

  @Override
  public void execute() {
    analogExecute(this.controller.getControllerValues());
  }

  public abstract void analogExecute(ControllerValues controllerValues);
}
