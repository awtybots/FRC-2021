package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.automatic.AutoAimUsingTurret;
import frc.robot.commands.teleop.automatic.AutoShoot;

public class Shoot3AndDriveForwardDistance extends SequentialCommandGroup {
  public Shoot3AndDriveForwardDistance() {
    addCommands(
        new AutoShoot().alongWith(new AutoAimUsingTurret()).withTimeout(8), new DriveForwardDistance());
  }
}
