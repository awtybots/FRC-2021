package frc.robot.commands.teleop.automatic;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.TurretSubsystem;

public class ResetTurret extends InstantCommand {
  public ResetTurret() {
    super(() -> TurretSubsystem.getInstance().returnToStart(), TurretSubsystem.getInstance());
  }
}
