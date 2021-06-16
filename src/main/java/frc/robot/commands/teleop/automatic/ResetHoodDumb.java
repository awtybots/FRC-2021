/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop.automatic;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AdjustableHoodSubsystem;

public class ResetHoodDumb extends CommandBase {

  public ResetHoodDumb() {
    addRequirements(AdjustableHoodSubsystem.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Thread t = new Thread();
    AdjustableHoodSubsystem.getInstance()
        .motor
        .getMotorController()
        .set(ControlMode.PercentOutput, 0.15);
    try {
      t.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    AdjustableHoodSubsystem.getInstance()
        .motor
        .getMotorController()
        .set(ControlMode.PercentOutput, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
