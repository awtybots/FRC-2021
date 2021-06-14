package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ToggleClimber extends CommandBase {

  public static final int Forward = 1;
  public static final int Reverse = -1;

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
