package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ManualShootingPreset extends ParallelCommandGroup {
  public ManualShootingPreset(double rpm, double launchAngle) {
    addCommands(new ResetTurret(), new ToggleShooter(rpm), new SetHoodLaunchAngle(launchAngle));
  }
}
