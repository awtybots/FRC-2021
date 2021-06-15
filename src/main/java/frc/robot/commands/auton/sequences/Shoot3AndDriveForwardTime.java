package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.commands.teleop.automatic.AutoAimUsingTurret;
import frc.robot.commands.teleop.automatic.AutoShoot;

public class Shoot3AndDriveForwardTime extends SequentialCommandGroup {
  public Shoot3AndDriveForwardTime(double time) {
    addCommands(
        // new AutoShoot().alongWith(new AutoAimUsingTurret()).withTimeout(8), new
        // DriveForwardTime(time));
        new AutoShoot().withTimeout(8), new DriveForwardTime(time) // TODO replace with line above
        );
  }
}
