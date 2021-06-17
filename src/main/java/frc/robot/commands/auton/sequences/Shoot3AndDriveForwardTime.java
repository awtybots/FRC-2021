package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.automatic.ManualShootingPreset;

public class Shoot3AndDriveForwardTime extends SequentialCommandGroup {
  public Shoot3AndDriveForwardTime(double time) {
    addCommands(
        new ManualShootingPreset(4800, 56, false).withTimeout(10), new DriveForwardTime(time));
  }
}
