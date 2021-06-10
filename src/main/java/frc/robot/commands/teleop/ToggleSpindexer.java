package frc.robot.commands.teleop;

import frc.robot.subsystems.SpindexerSubsystem;
import kotlin.Unit;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleSpindexer extends ToggleCommand {

  public ToggleSpindexer() {
    super(
        SpindexerSubsystem.getInstance(),
        t -> {
          SpindexerSubsystem.getInstance().toggle(!t);
          return Unit.INSTANCE;
        });
  }

  public ToggleSpindexer(boolean on) {
    super(
        SpindexerSubsystem.getInstance(),
        t -> {
          SpindexerSubsystem.getInstance().toggle(t);
          return Unit.INSTANCE;
        },
        on);
  }
}
