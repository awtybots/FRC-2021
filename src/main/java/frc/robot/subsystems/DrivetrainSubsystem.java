/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {

  private TalonFX motorLeftBack = new TalonFX(1);
  private TalonFX motorLeftFront = new TalonFX(2);
  private TalonFX motorRightBack = new TalonFX(3);
  private TalonFX motorRightFront = new TalonFX(4);

  public DrivetrainSubsystem() {}

  public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
    motorLeftBack.set(ControlMode.PercentOutput, leftSpeed);
    motorLeftFront.set(ControlMode.PercentOutput, leftSpeed);
  
    motorRightBack.set(ControlMode.PercentOutput, rightSpeed);
    motorRightFront.set(ControlMode.PercentOutput, rightSpeed);
  }

  @Override
  public void periodic() {}
}
