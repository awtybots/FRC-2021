package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.commands.auton.RotateAngle;
import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.sensors.vision.Limelight.LEDMode;

public class AutoAim extends CommandBase {

  private static final double startupTime = 0.5;

  private Logger logger = new Logger("AutoAim");
  private RotateAngle rotateCommand = null;
  private Timer timer;

  public AutoAim() {}

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(0);
    Robot.limelight.setLedMode(LEDMode.On);

    rotateCommand = null;

    timer = new Timer();
    timer.start();
  }

  @Override
  public void execute() {
    if (rotateCommand != null) return;

    if (timer.get() > startupTime) {
      timer = null;

      if (!Robot.limelight.getHasVisibleTarget()) {
        logger.error("Cannot auto-aim without a target!");
        return;
      }

      double x = Robot.limelight.getXOffset();
      rotateCommand = new RotateAngle(x);
      rotateCommand.schedule();
    }
  }

  @Override
  public boolean isFinished() {
    return rotateCommand.isFinished();
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) rotateCommand.cancel();
    Robot.limelight.setLedMode(LEDMode.Off);
  }
}
