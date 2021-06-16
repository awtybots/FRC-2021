package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;

public class ReverseIndexer extends CommandBase {
  public ReverseIndexer() {
    addRequirements(IndexerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    IndexerSubsystem.getInstance().set(-1);
  }

  @Override
  public void end(boolean interrupted) {
    IndexerSubsystem.getInstance().set(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
