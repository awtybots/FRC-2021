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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivetrainSubsystem extends Drivetrain<Falcon500> {

  private static DrivetrainSubsystem instance;

  public static double trackWidth = 0.66; // distance between the left and right wheels (meters)

  private static double gearRatio = 12.0 / 40.0 * 14.0 / 44.0;
  private static DriveConfig driveConfig =
      new DriveConfig(
          true, // invertRight
          0.154, // wheelDiameter (m)
          0.03, // p
          0.0, // i
          0.0, // d
          0.07, // f
          0.4, // percentRamp
          1.0, // percentPeak
          0.05, // percentNominal
          4.0 // velocityPeak (m/s)
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

    for(Falcon500 motor : getAllMotors().getMotorList()) {
      motor.getMotorController().configVoltageCompSaturation(13.0);
      motor.getMotorController().enableVoltageCompensation(true);
    }

    kill();
  }

  @Override
  public void periodic() {
    super.periodic();

    SmartDashboard.putNumber("Drive Goal Output", getLeftMotors().getGoalVelocity());
    SmartDashboard.putNumber("Drive Output", getLeftMotors().getMotorList()[0].getOutputRevsPerSecond() * driveConfig.getWheelDiameter() * Math.PI);
  }

  public static DrivetrainSubsystem getInstance() {
    if (instance == null) instance = new DrivetrainSubsystem();
    return instance;
  }
}
