package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.DriveDistance;
import frc.robot.commands.teleop.AutoAimUsingTurret;
import frc.robot.commands.teleop.AutoShoot;

public class Shoot3AndDriveForward extends SequentialCommandGroup {
  public Shoot3AndDriveForward() {
    addCommands(
      new AutoShoot().alongWith(new AutoAimUsingTurret()).withTimeout(8),
      new DriveDistance(0.5)
    );
  }
}
