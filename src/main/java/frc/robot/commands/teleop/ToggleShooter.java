package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class ToggleShooter extends CommandBase {

  private double rps;

  public ToggleShooter(double rpm) {
    addRequirements(ShooterSubsystem.getInstance());
    rps = rpm / 60.0;
  }

  @Override
  public void initialize() {
    ShooterSubsystem.getInstance().setFlywheelRevsPerSecond(rps);
  }

  @Override
  public void execute() {
    TowerSubsystem.getInstance().toggle(ShooterSubsystem.getInstance().isFlywheelReady());
  }

  @Override
  public void end(boolean interrupted) {
    TowerSubsystem.getInstance().toggle(false);
    ShooterSubsystem.getInstance().stopFlywheel();
  }
}
