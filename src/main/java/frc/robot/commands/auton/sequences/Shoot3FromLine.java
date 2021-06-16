package frc.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.automatic.ManualShootingPreset;

public class Shoot3FromLine extends SequentialCommandGroup {
  public Shoot3FromLine() {
    addCommands(new ManualShootingPreset(4800, 56, false).withTimeout(10));
  }
}
