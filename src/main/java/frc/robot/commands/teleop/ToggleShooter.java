package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class ToggleShooter extends CommandBase {

  private double rps;
  private boolean startedTower = false;
  private Timer timer = new Timer();

  public ToggleShooter(double rpm) {
    addRequirements(
        ShooterSubsystem.getInstance(),
        TowerSubsystem.getInstance(),
        IndexerSubsystem.getInstance());
    rps = rpm / 60.0;
  }

  @Override
  public void initialize() {
    ShooterSubsystem.getInstance().setFlywheelRevsPerSecond(rps);
    startedTower = false;
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    if (timer.get() > 0.5 && !startedTower && ShooterSubsystem.getInstance().isFlywheelReady()) {
      TowerSubsystem.getInstance().enableForShooting();
      IndexerSubsystem.getInstance().set(1);
      startedTower = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    TowerSubsystem.getInstance().stop();
    IndexerSubsystem.getInstance().set(0);
    ShooterSubsystem.getInstance().stopFlywheel();
    timer.stop();
  }
}
