/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.config.DriveConfig;
import org.awtybots.frc.botplus.motors.Falcon500;
import org.awtybots.frc.botplus.subsystems.Drivetrain;

public class DrivetrainSubsystem extends Drivetrain<Falcon500> {

  private static double gearRatio = 12.0 / 40.0 * 14.0 / 44.0;
  private static DriveConfig driveConfig =
      new DriveConfig(
          true, // invertRight
          0.154, // wheelDiameter (m)
          0.03, // p
          0.0, // i
          0.0, // d
          0.07, // f
          0.6, // percentRamp
          1.0, // percentPeak (max output)
          0.07, // percentNominal (min output)
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

    kill();
  }

  @Override
  public void periodic() {
    super.periodic();

    SmartDashboard.putNumber("Drive Goal Output", getLeftMotors().getGoalVelocity());
    SmartDashboard.putNumber("Drive Output", getLeftMotors().getWheelVelocity());
  }

  private static DrivetrainSubsystem instance = new DrivetrainSubsystem();

  public static DrivetrainSubsystem getInstance() {
    return instance;
  }
}
