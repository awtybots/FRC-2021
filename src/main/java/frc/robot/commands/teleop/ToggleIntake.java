package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntake extends CommandBase {

  public ToggleIntake() {
    addRequirements(IntakeSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    IntakeSubsystem.getInstance().toggle(true);
  }

  @Override
  public void end(boolean interrupted) {
    IntakeSubsystem.getInstance().toggle(false);
  }
}
