package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.DriveDistance;
import frc.robot.commands.teleop.ToggleShooter;

public class Shoot3AndReverse extends SequentialCommandGroup {
  public Shoot3AndReverse() {
    addCommands(
      new ToggleShooter(4000).withTimeout(8),
      new DriveDistance(-2.0)
    );
  }
}
