package frc.robot.commands.teleop;

import frc.robot.subsystems.ClimbSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleClimber extends CommandBase {

  private final int dir;

  public ToggleClimber(int dir) {
    this.dir = dir;
    addRequirements(ClimbSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    ClimbSubsystem.getInstance().set(dir);
  }

  @Override
  public void end(boolean interrupted) {
    ClimbSubsystem.getInstance().set(0);
  }
}
