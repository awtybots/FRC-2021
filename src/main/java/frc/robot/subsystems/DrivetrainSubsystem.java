/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.config.DriveConfig;
import org.awtybots.frc.botplus.motors.Falcon500;
import org.awtybots.frc.botplus.motors.MotorGroup;
import org.awtybots.frc.botplus.subsystems.Drivetrain;

public class DrivetrainSubsystem extends Drivetrain<Falcon500> {

  private static DrivetrainSubsystem instance;

  private static DriveConfig driveConfig =
      new DriveConfig(
          true, 0.0, 1.0, 0.5, 10.0, 5.0, FeedbackDevice.IntegratedSensor, 6.0, 0.5, 0.0, 0.0, 0.5);
  private static double gearRatio = 1.0;

  private static MotorGroup<Falcon500> leftMotorGroup =
      new MotorGroup<Falcon500>(
          new Falcon500[] {
            new Falcon500(RobotMap.CAN.leftDrive1, gearRatio),
            new Falcon500(RobotMap.CAN.leftDrive2, gearRatio)
          },
          driveConfig);
  private static MotorGroup<Falcon500> rightMotorGroup =
      new MotorGroup<Falcon500>(
          new Falcon500[] {
            new Falcon500(RobotMap.CAN.rightDrive1, gearRatio),
            new Falcon500(RobotMap.CAN.rightDrive2, gearRatio)
          },
          driveConfig);

  private DrivetrainSubsystem() {
    super(driveConfig, leftMotorGroup, rightMotorGroup);
  }

  public static DrivetrainSubsystem getInstance() {
    if (instance == null) instance = new DrivetrainSubsystem();
    return instance;
  }
}
