package frc.robot.commands.teleop;

import frc.robot.subsystems.ClimbSubsystem;
import kotlin.Unit;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleClimber extends ToggleCommand {

  public ToggleClimber() {
    super(ClimbSubsystem.getInstance(), t -> {
      ClimbSubsystem.getInstance().toggle(t);
      return Unit.INSTANCE;
    });
  }

  public ToggleClimber(boolean on) {
    super(ClimbSubsystem.getInstance(), t -> {
      ClimbSubsystem.getInstance().toggle(t);
      return Unit.INSTANCE;
    }, on);
  }
}
