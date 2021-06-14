package frc.robot.commands.teleop;

import frc.robot.subsystems.IndexerSubsystem;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleIndexer extends ToggleCommand {
  public ToggleIndexer() {
    super(IndexerSubsystem.getInstance(), t -> {
      IndexerSubsystem.getInstance().set(t ? 1 : 0);
    });
  }

  public ToggleIndexer(boolean on) {
    super(IndexerSubsystem.getInstance(), t -> {
      IndexerSubsystem.getInstance().set(t ? 1 : 0);
    }, on);
  }
}
