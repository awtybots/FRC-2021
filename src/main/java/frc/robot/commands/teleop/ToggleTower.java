package frc.robot.commands.teleop;

import frc.robot.subsystems.TowerSubsystem;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleTower extends ToggleCommand {

  public ToggleTower() {
    super(TowerSubsystem.getInstance(), t -> {
      TowerSubsystem.getInstance().set(t ? 1 : 0);
    });
  }

  public ToggleTower(boolean on) {
    super(TowerSubsystem.getInstance(), t -> {
      TowerSubsystem.getInstance().set(t ? 1 : 0);
    }, on);
  }
}
