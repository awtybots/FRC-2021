/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DrivetrainSubsystem;
import org.awtybots.frc.botplus.commands.AnalogInputCommand;
import org.awtybots.frc.botplus.commands.ControllerValues;
import org.awtybots.frc.botplus.math.Vector2;

public class TeleopDrive extends AnalogInputCommand {

  public TeleopDrive() {
    addRequirements(DrivetrainSubsystem.getInstance());
  }

  @Override
  public void analogExecute(ControllerValues controllerValues) {
    Vector2 driveControlsInput = gtaDrive(controllerValues);

    SmartDashboard.putNumber("Drive Controls L", driveControlsInput.getX());
    SmartDashboard.putNumber("Drive Controls R", driveControlsInput.getY());
    DrivetrainSubsystem.getInstance()
        .setMotorVelocityOutput(driveControlsInput.getX(), driveControlsInput.getY());
  }

  @Override
  public void cancel() {
    DrivetrainSubsystem.getInstance().kill();
  }

  @SuppressWarnings("unused")
  private Vector2 splitArcadeDrive(ControllerValues controllerValues) {
    double speed = controllerValues.getLeftStickY();
    double steer = controllerValues.getRightStickX();

    if (speed < 0) {
      steer *= -1;
    }

    double left = speed + steer;
    double right = speed - steer;

    return new Vector2(left, right);
  }

  private Vector2 gtaDrive(ControllerValues controllerValues) {
    double speed = controllerValues.getRightTrigger() - controllerValues.getLeftTrigger();
    double steer = controllerValues.getLeftStickX();

    if (speed < 0) {
      steer *= -1;
    }

    double left = speed + steer;
    double right = speed - steer;

    return new Vector2(left, right);
  }
}