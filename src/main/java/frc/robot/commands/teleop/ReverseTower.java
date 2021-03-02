package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerTowerSubsystem;

public class ReverseTower extends CommandBase {
  public ReverseTower() {
    addRequirements(IndexerTowerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    IndexerTowerSubsystem.getInstance().reverse();
  }

  @Override
  public void end(boolean interrupted) {
    IndexerTowerSubsystem.getInstance().toggle(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
