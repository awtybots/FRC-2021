package frc.robot.commands.teleop;

import frc.robot.subsystems.IntakeSubsystem;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleIntake extends ToggleCommand {

  public ToggleIntake() {
    super(IntakeSubsystem.getInstance(), t -> {
      IntakeSubsystem.getInstance().toggle(t);
    });
  }

  public ToggleIntake(boolean on) {
    super(IntakeSubsystem.getInstance(), t -> {
      IntakeSubsystem.getInstance().toggle(t);
    }, on);
  }
}
