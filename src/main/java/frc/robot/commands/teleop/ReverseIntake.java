package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class ReverseIntake extends CommandBase {
  public ReverseIntake() {
    addRequirements(IntakeSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    IntakeSubsystem.getInstance().reverse();
  }

  @Override
  public void end(boolean interrupted) {
    IntakeSubsystem.getInstance().toggle(false);
  }
}
