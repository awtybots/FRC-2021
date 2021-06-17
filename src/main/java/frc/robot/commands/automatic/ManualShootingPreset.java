package frc.robot.commands.automatic;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.teleop.ToggleShooter;

public class ManualShootingPreset extends ParallelCommandGroup {
  public ManualShootingPreset(double rpm, double launchAngle, boolean autoAim) {
    addCommands(
        new ToggleShooter(rpm),
        // new SetHoodLaunchAngle(launchAngle), // TODO fix
        autoAim ? new AutoAimUsingTurret() : new ResetTurret());
  }
}