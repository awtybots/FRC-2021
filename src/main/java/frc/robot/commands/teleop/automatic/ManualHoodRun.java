/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop.automatic;

// import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.AdjustableHoodSubsystem;

public class ManualHoodRun extends SequentialCommandGroup {
  public ManualHoodRun(boolean forwards) { // TODO fix
    // double output = forwards ? 0.15 : -0.15;
    addCommands(
        new StartEndCommand(
                () -> AdjustableHoodSubsystem.getInstance(),
                // .motor
                // .getMotorController()
                // .set(ControlMode.PercentOutput, output),
                () -> AdjustableHoodSubsystem.getInstance(),
                // .motor
                // .getMotorController()
                // .set(ControlMode.PercentOutput, 0),
                AdjustableHoodSubsystem.getInstance())
            .withTimeout(1));
  }
}
