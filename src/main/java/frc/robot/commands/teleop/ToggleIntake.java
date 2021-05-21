package frc.robot.commands.teleop;

import org.awtybots.frc.botplus.ToggleCommand;
import frc.robot.subsystems.IntakeSubsystem;
import kotlin.Unit;

public class ToggleIntake extends ToggleCommand {
  
  public ToggleIntake() {
    super(IntakeSubsystem.getInstance(), t -> { IntakeSubsystem.getInstance().toggle(t); return Unit.INSTANCE; });
  }

  public ToggleIntake(boolean on) {
    super(IntakeSubsystem.getInstance(), t -> { IntakeSubsystem.getInstance().toggle(t); return Unit.INSTANCE; }, on);
  }

}
