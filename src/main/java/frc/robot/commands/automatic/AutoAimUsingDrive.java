package frc.robot.commands.automatic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.LimelightPipelines;
import frc.robot.commands.auton.RotateAngle;

public class AutoAimUsingDrive extends CommandBase {

  private static final double startupTime = 0.5;

  private RotateAngle rotateCommand = null;
  private Timer timer;

  public AutoAimUsingDrive() {} // requirements are handled by RotateAngle command

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(LimelightPipelines.powerPort);

    rotateCommand = null;

    timer = new Timer();
    timer.start();
  }

  @Override
  public void execute() {
    if (rotateCommand != null) {
      if (rotateCommand.isFinished()) rotateCommand = null;
      else return;
    }

    if (timer.get() > startupTime) {
      timer = null;

      boolean hasVisibleTarget = Robot.limelight.getHasVisibleTarget();
      SmartDashboard.putBoolean("Limelight Target Visible", hasVisibleTarget);

      if (hasVisibleTarget) {
        double x = Robot.limelight.getXOffset();
        SmartDashboard.putNumber("Limelight Target X Offset", x);

        rotateCommand = new RotateAngle(x);
        rotateCommand.schedule();
      }
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) rotateCommand.cancel();
    Robot.limelight.setDriverMode(true);
  }
}
