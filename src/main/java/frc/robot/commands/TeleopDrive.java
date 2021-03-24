/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import static frc.robot.Robot.*;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TeleopDrive extends CommandBase {

  public TeleopDrive() {}

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double left = -Robot.controller1.getY(Hand.kLeft);
    double right = -Robot.controller1.getY(Hand.kRight);

    Robot.drivetrainSubsystem.setMotorSpeeds(left, right);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.drivetrainSubsystem.setMotorSpeeds(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false; // return true when you want the command to stop running
  }
}
