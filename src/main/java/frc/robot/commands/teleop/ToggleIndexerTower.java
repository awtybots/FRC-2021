package frc.robot.commands.teleop;

import frc.robot.subsystems.IndexerTowerSubsystem;
import kotlin.Unit;
import org.awtybots.frc.botplus.ToggleCommand;

/** just in case we need it */
public class ToggleIndexerTower extends ToggleCommand {
  public ToggleIndexerTower() {
    super(
        IndexerTowerSubsystem.getInstance(),
        t -> {
          IndexerTowerSubsystem.getInstance().toggle(t);
          return Unit.INSTANCE;
        });
  }

  public ToggleIndexerTower(boolean on) {
    super(
        IndexerTowerSubsystem.getInstance(),
        t -> {
          IndexerTowerSubsystem.getInstance().toggle(t);
          return Unit.INSTANCE;
        },
        on);
  }
}
