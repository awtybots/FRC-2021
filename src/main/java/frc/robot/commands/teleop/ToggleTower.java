package frc.robot.commands.teleop;

import frc.robot.subsystems.TowerSubsystem;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleTower extends ToggleCommand {

  public ToggleTower() {
    super(TowerSubsystem.getInstance());
  }

  public ToggleTower(boolean on) {
    super(on, TowerSubsystem.getInstance());
  }

  @Override
  public void toggle(boolean b) {
    if (b)
      TowerSubsystem.getInstance().enableForShooting();
    else
      TowerSubsystem.getInstance().stop();
  }
}
