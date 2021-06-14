package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TowerSubsystem;

public class ReverseTower extends CommandBase {
  public ReverseTower() {
    addRequirements(TowerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    TowerSubsystem.getInstance().set(-1);
  }

  @Override
  public void end(boolean interrupted) {
    TowerSubsystem.getInstance().set(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
