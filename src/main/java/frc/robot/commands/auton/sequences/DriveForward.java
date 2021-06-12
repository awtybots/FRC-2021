package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.DriveDistance;

public class DriveForward extends SequentialCommandGroup {
  public DriveForward() {
    addCommands(
      new DriveDistance(0.5)
    );
  }
}
