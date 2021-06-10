package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SpindexerSubsystem;

public class ReverseSpindexer extends CommandBase {
  public ReverseSpindexer() {
    addRequirements(SpindexerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    SpindexerSubsystem.getInstance().reverse();
  }

  @Override
  public void end(boolean interrupted) {
    SpindexerSubsystem.getInstance().toggle(true);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
