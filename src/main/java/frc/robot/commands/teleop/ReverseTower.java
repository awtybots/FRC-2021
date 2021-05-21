package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TowerSubsystem;

public class ReverseTower extends CommandBase {
  public ReverseTower() {
    addRequirements(TowerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    TowerSubsystem.getInstance().reverse();
  }

  @Override
  public void end(boolean interrupted) {
    TowerSubsystem.getInstance().toggle(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
