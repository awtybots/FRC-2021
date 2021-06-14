package frc.robot.commands.teleop;

import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.TowerSubsystem;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleIndexer extends ToggleCommand {
  public ToggleIndexer() {
    super(IndexerSubsystem.getInstance(), TowerSubsystem.getInstance());
  }

  public ToggleIndexer(boolean on) {
    super(on, IndexerSubsystem.getInstance(), TowerSubsystem.getInstance());
  }

  @Override
  public void toggle(boolean b) {
    IndexerSubsystem.getInstance().set(b ? 1 : 0);
    if (b) TowerSubsystem.getInstance().enableForLoading();
    else TowerSubsystem.getInstance().stop();
  }
}
