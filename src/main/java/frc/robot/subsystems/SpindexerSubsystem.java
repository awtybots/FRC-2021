// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SpindexerSubsystem {
  private TalonSRX spindexerMotor = new TalonSRX(6);

  public SpindexerSubsystem() {
  }

  public void toggleIntake(boolean on){

    if(on){
      spindexerMotor.set(ControlMode.PercentOutput, 0.6);
    }else{
      spindexerMotor.set(ControlMode.PercentOutput,0.0);
    }

    spindexerMotor.set(ControlMode.PercentOutput, 0.6);
  }

  public void ToggleSpindexer(boolean b) {
  }

}
