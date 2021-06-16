package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.commands.teleop.automatic.AutoAimUsingTurret;
// import frc.robot.commands.teleop.automatic.AutoShoot;
import frc.robot.commands.teleop.automatic.ManualShootingPreset;

public class DriveForwardTimeAndShoot3 extends SequentialCommandGroup {
  public DriveForwardTimeAndShoot3(double time) {
    addCommands(new DriveForwardTime(time), new ManualShootingPreset(4000, 76, false));
  }
}
