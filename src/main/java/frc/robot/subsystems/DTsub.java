// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//yeetamouse
//hi
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DTsub extends SubsystemBase {
  /** Creates a new DTsub. */
  public TalonFX frontRight = new TalonFX(0);
  public TalonFX backRight = new TalonFX(1);
  public TalonFX frontLeft = new TalonFX(2);
  public TalonFX backLeft = new TalonFX(3);

  public DTsub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
