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
    Vector2 driveControlsInput = splitArcadeDrive(controllerValues);

    SmartDashboard.putNumber("Drive Controls L", driveControlsInput.getX());
    SmartDashboard.putNumber("Drive Controls R", driveControlsInput.getY());
    DrivetrainSubsystem.getInstance()
        // .setMotorRawOutput( // uncomment this for no PID drive
        .setMotorVelocityOutput( // comment this for no PID drive
            driveControlsInput.getX(), driveControlsInput.getY());
  }

  private double smoothingFunction(double x) {
    // return x; // simple passthrough
    return (x * 0.3) + (Math.pow(x, 7) * 0.7); // joe drive
  }

  private Vector2 splitArcadeDrive(ControllerValues controllerValues) {
    double speed = smoothingFunction(controllerValues.getLeftStickY());
    double steer = smoothingFunction(controllerValues.getRightStickX());

    if (speed < 0) {
      steer *= -1;
    }

    double left = speed + steer;
    double right = speed - steer;

    return new Vector2(left, right);
  }

  @SuppressWarnings("unused")
  private Vector2 gtaDrive(ControllerValues controllerValues) {
    double speed = smoothingFunction(controllerValues.getRightTrigger() - controllerValues.getLeftTrigger());
    double steer = smoothingFunction(controllerValues.getLeftStickX());

    if (speed < 0) {
      steer *= -1;
    }

    double left = speed + steer;
    double right = speed - steer;

    return new Vector2(left, right);
  }

  @Override
  public void end(boolean interrupted) {
    DrivetrainSubsystem.getInstance().kill();
  }
}
