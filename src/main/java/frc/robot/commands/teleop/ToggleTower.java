package frc.robot.commands.teleop;

import org.awtybots.frc.botplus.ToggleCommand;

import frc.robot.subsystems.TowerSubsystem;
import kotlin.Unit;

public class ToggleTower extends ToggleCommand {

  public ToggleTower() {
    super(TowerSubsystem.getInstance(), t -> { TowerSubsystem.getInstance().toggle(t); return Unit.INSTANCE; });
  }

  public ToggleTower(boolean on) {
    super(TowerSubsystem.getInstance(), t -> { TowerSubsystem.getInstance().toggle(t); return Unit.INSTANCE; }, on);
  }

}
