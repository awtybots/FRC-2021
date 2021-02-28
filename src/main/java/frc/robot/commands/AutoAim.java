package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Robot;
import frc.robot.subsystems.DrivetrainSubsystem;
import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.sensors.vision.Limelight.LEDMode;

public class AutoAim extends CommandBase {
  private static final double maxDrivePercentOutput = 0.3;
  private static final double goalMaxOffsetAngle = 1.0; // degrees
  private static final double kP = 0.03;

  private Logger logger = new Logger("AutoAim");
  private boolean onTarget = false;

  public AutoAim() {
    addRequirements(DrivetrainSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(0);
    Robot.limelight.setLedMode(LEDMode.On);
  }

  @Override
  public void execute() {
    if (!Robot.limelight.getHasVisibleTarget()) {
      logger.error("Cannot auto-aim without a target!");
      cancel();
      return;
    }

    double x = Robot.limelight.getXOffset();

    onTarget = Math.abs(x) < goalMaxOffsetAngle;

    if (onTarget) {
      DrivetrainSubsystem.getInstance().kill();
    } else {
      double speed = MathUtil.clamp(x * kP, -maxDrivePercentOutput, maxDrivePercentOutput);
      DrivetrainSubsystem.getInstance().setMotorRawOutput(speed, -speed);
    }
  }

  @Override
  public boolean isFinished() {
    return onTarget;
  }

  @Override
  public void end(boolean interrupted) {
    DrivetrainSubsystem.getInstance().kill();
    Robot.limelight.setLedMode(LEDMode.Off);
  }
}
