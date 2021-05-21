package frc.robot.commands.teleop;

import org.awtybots.frc.botplus.ToggleCommand;
import frc.robot.subsystems.SpindexerSubsystem;
import kotlin.Unit;

public class ToggleSpindexer extends ToggleCommand {
  
  public ToggleSpindexer() {
    super(SpindexerSubsystem.getInstance(), t -> { SpindexerSubsystem.getInstance().toggle(t); return Unit.INSTANCE; });
  }

  public ToggleSpindexer(boolean on) {
    super(SpindexerSubsystem.getInstance(), t -> { SpindexerSubsystem.getInstance().toggle(t); return Unit.INSTANCE; }, on);
  }

}
