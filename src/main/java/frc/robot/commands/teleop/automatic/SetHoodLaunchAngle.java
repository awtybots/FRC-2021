package frc.robot.commands.teleop.automatic;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.AdjustableHoodSubsystem;

public class SetHoodLaunchAngle extends InstantCommand {
  public SetHoodLaunchAngle(double angle) { // TODO fix
    super(
        () -> AdjustableHoodSubsystem.getInstance(), // .setGoalLaunchAngle(angle),
        AdjustableHoodSubsystem.getInstance());
  }
}
