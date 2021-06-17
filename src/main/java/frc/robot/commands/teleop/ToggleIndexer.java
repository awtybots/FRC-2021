package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class ToggleIndexer extends CommandBase {
  public ToggleIndexer() {
    addRequirements(IndexerSubsystem.getInstance(), TowerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    IndexerSubsystem.getInstance().set(1);
    TowerSubsystem.getInstance().enableForLoading();
  }

  @Override
  public void end(boolean interrupted) {
    IndexerSubsystem.getInstance().set(0);
    TowerSubsystem.getInstance().stop();
  }
}
