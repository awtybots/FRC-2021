package frc.robot.commands.teleop;

import frc.robot.subsystems.IntakeSubsystem;
import org.awtybots.frc.botplus.ToggleCommand;

public class ToggleIntake extends ToggleCommand {

  public ToggleIntake() {
    super(IntakeSubsystem.getInstance());
  }

  public ToggleIntake(boolean on) {
    super(on, IntakeSubsystem.getInstance());
  }

  @Override
  public void toggle(boolean b) {
    IntakeSubsystem.getInstance().toggle(b);
  }
}
