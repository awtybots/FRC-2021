package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveBackwardsTime extends SequentialCommandGroup {
  public DriveBackwardsTime(double time) {
    addCommands(
        new StartEndCommand(
                () -> DrivetrainSubsystem.getInstance().setMotorRawOutput(-0.3, -0.3),
                () -> DrivetrainSubsystem.getInstance().kill(),
                DrivetrainSubsystem.getInstance())
            .withTimeout(time));
  }
}