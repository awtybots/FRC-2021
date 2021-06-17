package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.automatic.ManualShootingPreset;

public class DriveForwardTimeAndShoot3 extends SequentialCommandGroup {
  public DriveForwardTimeAndShoot3(double time) {
    addCommands(new DriveForwardTime(time), new ManualShootingPreset(3700, 76, false));
  }
}
