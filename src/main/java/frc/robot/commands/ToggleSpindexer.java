package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleSpindexer extends CommandBase {
  @Override
  public void initialize() {
    Robot.spindexerSubsystem.toggleSpindexer(true);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.spindexerSubsystem.toggleSpindexer(false);
  }
}
