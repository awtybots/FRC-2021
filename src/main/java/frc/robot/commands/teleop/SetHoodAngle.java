package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.AdjustableHoodSubsystem;

public class SetHoodAngle extends InstantCommand {
  public SetHoodAngle(double angle) {
    super(() -> AdjustableHoodSubsystem.getInstance().setGoalAngle(angle), AdjustableHoodSubsystem.getInstance());
  }
}
