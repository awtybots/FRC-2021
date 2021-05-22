package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.TurretSubsystem;
import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.sensors.vision.Limelight.LEDMode;

public class AutoAimUsingTurret extends CommandBase {

  private static final double startupTime = 0.5;

  private Logger logger = new Logger("AutoAimUsingTurret");
  private Timer timer;

  public AutoAimUsingTurret() {
    addRequirements(TurretSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(0);
    Robot.limelight.setLedMode(LEDMode.On);

    timer = new Timer();
    timer.start();
  }

  @Override
  public void execute() {
    if (timer.get() > startupTime) {
      if (!Robot.limelight.getHasVisibleTarget()) {
        logger.warn("Cannot auto-aim without a target!");
        return;
      }

      double x = Robot.limelight.getXOffset();

      if (!TurretSubsystem.getInstance().isRelativeAngleInBounds(x)) {
        logger.warn("Desired turret goal angle is out of bounds!");
        return;
      }
      TurretSubsystem.getInstance().setRelativeGoalAngle(x);
    }
  }

  @Override
  public boolean isFinished() {
    return TurretSubsystem.getInstance().atGoalAngle();
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) TurretSubsystem.getInstance().relax();
    Robot.limelight.setLedMode(LEDMode.Off);
  }
}
