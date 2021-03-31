// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleIntake extends CommandBase {
  @Override
  public void initialize() {
      Robot.intakeSubsystem.toggleIntake(true);
  }

  @Override
  public void end(boolean interrupted) {
      Robot.intakeSubsystem.toggleIntake(false);
  }
}