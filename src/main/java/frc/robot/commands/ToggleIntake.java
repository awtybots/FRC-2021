package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntake extends CommandBase {
  private boolean button;
  private boolean on;

  public ToggleIntake() {
    button = true;
  }

  public ToggleIntake(boolean on) {
    button = false;
    this.on = on;
  }

  @Override
  public void initialize() {
    if (button) {
      IntakeSubsystem.getInstance().toggle(true);
    } else {
      IntakeSubsystem.getInstance().toggle(on);
    }
  }

  @Override
  public void end(boolean interrupted) {
    if (button) {
      IntakeSubsystem.getInstance().toggle(false);
    }
  }

  @Override
  public boolean isFinished() {
    return !button;
  }
}
