package frc.robot.commands.teleop;

import frc.robot.subsystems.TowerSubsystem;
import kotlin.Unit;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleTower extends ToggleCommand {

  public ToggleTower() {
    super(
        TowerSubsystem.getInstance(),
        t -> {
          TowerSubsystem.getInstance().toggle(t);
          return Unit.INSTANCE;
        });
  }

  public ToggleTower(boolean on) {
    super(
        TowerSubsystem.getInstance(),
        t -> {
          TowerSubsystem.getInstance().toggle(t);
          return Unit.INSTANCE;
        },
        on);
  }
}
