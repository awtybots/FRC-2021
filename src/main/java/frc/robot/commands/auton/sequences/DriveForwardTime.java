package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveForwardTime extends SequentialCommandGroup {
  public DriveForwardTime() {
    addCommands(
      new StartEndCommand(
        () -> DrivetrainSubsystem.getInstance().setMotorVelocityOutput(0.3, 0.3),
        () -> DrivetrainSubsystem.getInstance().kill(),
        DrivetrainSubsystem.getInstance()
      ).withTimeout(0.5)
    );
  }
}
