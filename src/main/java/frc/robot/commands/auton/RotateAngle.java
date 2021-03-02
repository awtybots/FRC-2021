package frc.robot.commands.auton;

import frc.robot.subsystems.DrivetrainSubsystem;

public class RotateAngle extends DriveDistance {
  public RotateAngle(double degrees) {
    super(Math.toRadians(degrees) * DrivetrainSubsystem.trackWidth * 0.5, -Math.toRadians(degrees) * DrivetrainSubsystem.trackWidth * 0.5);
  }
}
