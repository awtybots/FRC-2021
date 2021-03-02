/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import org.awtybots.frc.botplus.config.DriveConfig;
import org.awtybots.frc.botplus.motors.Falcon500;
import org.awtybots.frc.botplus.subsystems.Drivetrain;

public class DrivetrainSubsystem extends Drivetrain<Falcon500> {

  private static DrivetrainSubsystem instance;

  public static double trackWidth = 0.7; // distance between the left and right wheels (meters)

  private static double gearRatio = 1.0;
  private static DriveConfig driveConfig =
      new DriveConfig(
          false, // invertRight
          0.154, // wheelDiameter (m)
          0.0, // p
          0.0, // i
          0.0, // d
          0.0, // f
          0.4, // percentRamp
          1.0, // percentPeak
          0.05, // percentNominal
          10.0 // velocityPeak (m/s)
          );

  private DrivetrainSubsystem() {
    super(
        driveConfig,
        new Falcon500[] {
          new Falcon500(RobotMap.CAN.leftDrive1, gearRatio),
          new Falcon500(RobotMap.CAN.leftDrive2, gearRatio)
        },
        new Falcon500[] {
          new Falcon500(RobotMap.CAN.rightDrive1, gearRatio),
          new Falcon500(RobotMap.CAN.rightDrive2, gearRatio)
        });

    kill();

    driveConfig.setUpdateCallback(
        () -> {
          Falcon500[] motors = getAllMotors().getMotorList();
          for (Falcon500 motor : motors) {
            motor.setPIDF(
                driveConfig.getP(), driveConfig.getI(), driveConfig.getD(), driveConfig.getF());
          }
        });
  }

  public static DrivetrainSubsystem getInstance() {
    if (instance == null) instance = new DrivetrainSubsystem();
    return instance;
  }
}
