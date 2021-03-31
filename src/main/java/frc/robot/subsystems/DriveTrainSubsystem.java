// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//yeetamouse
//hi
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainSubsystem extends SubsystemBase {
  /** Creates a new DTsub. */

  private TalonFX motorLeftBack = new TalonFX(1);
  private TalonFX motorLeftFront = new TalonFX(2);
  private TalonFX motorRightBack = new TalonFX(3);
  private TalonFX motorRightFront = new TalonFX(4);

  public DriveTrainSubsystem() {}

  public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
    motorLeftBack.set(ControlMode.PercentOutput, leftSpeed);
    motorLeftFront.set(ControlMode.PercentOutput, leftSpeed);

    motorRightBack.set(ControlMode.PercentOutput, rightSpeed);
    motorRightFront.set(ControlMode.PercentOutput, rightSpeed);
    
    motorRightBack.setInverted(true);
    motorRightFront.setInverted(true);
    }
  
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
