package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakeMotorOnly extends CommandBase {
  private boolean button;
  private boolean on;

  public ToggleIntakeMotorOnly() {
    button = true;
  }

  public ToggleIntakeMotorOnly(boolean on) {
    button = false;
    this.on = on;
  }

  @Override
  public void execute() {
    if (button) {
      IntakeSubsystem.getInstance().toggleMotorOnly(true);
    } else {
      IntakeSubsystem.getInstance().toggleMotorOnly(on);
    }
  }

  @Override
  public void end(boolean interrupted) {
    if (button) {
      IntakeSubsystem.getInstance().toggleMotorOnly(false);
    }
  }

  @Override
  public boolean isFinished() {
    return !button;
  }
}
