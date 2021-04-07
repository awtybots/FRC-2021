/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop;

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

    DrivetrainSubsystem.getInstance()
        .setMotorRawOutput(driveControlsInput.getX(), driveControlsInput.getY());
  }

  @Override
  public void cancel() {
    DrivetrainSubsystem.getInstance().kill();
  }

  @SuppressWarnings("unused")
  private Vector2 splitArcadeDrive(ControllerValues controllerValues) {
    double speed = controllerValues.getLeftStickY();
    double steer = controllerValues.getRightStickX();

    double left = speed + steer;
    double right = speed - steer;

    return new Vector2(left, right);
  }

  private Vector2 gtaDrive(ControllerValues controllerValues) {
    double speed = controllerValues.getRightTrigger() - controllerValues.getLeftTrigger();
    double steer = controllerValues.getLeftStickX();

    double left = speed + steer;
    double right = speed - steer;

    return new Vector2(left, right);
  }
}
