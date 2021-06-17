package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TowerSubsystem;

public class ToggleTower extends CommandBase {

  public ToggleTower() {
    addRequirements(TowerSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    TowerSubsystem.getInstance().enableForShooting();
  }

  @Override
  public void end(boolean interrupted) {
    TowerSubsystem.getInstance().stop();
  }
}
