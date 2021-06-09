package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.LimelightPipelines;
import frc.robot.subsystems.TurretSubsystem;
import org.awtybots.frc.botplus.Logger;

public class AutoAimUsingTurret extends CommandBase {

  private static final double startupTime = 0.5;

  private Logger logger = new Logger("AutoAimUsingTurret");
  private Timer timer;
  private boolean started;

  public AutoAimUsingTurret() {
    addRequirements(TurretSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(LimelightPipelines.powerPort);
    started = false;

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
      SmartDashboard.putNumber("Limelight Target X Offset", x);

      if (!TurretSubsystem.getInstance().isRelativeAngleInBounds(x)) {
        logger.warn("Desired turret goal angle is out of bounds!");
        return;
      }

      TurretSubsystem.getInstance().setRelativeGoalAngle(x);
      started = true;
    }
  }

  @Override
  public boolean isFinished() {
    return started && TurretSubsystem.getInstance().atGoalAngle();
  }

  @Override
  public void end(boolean interrupted) {
    if(interrupted) TurretSubsystem.getInstance().relax();
    //TurretSubsystem.getInstance().returnToStart();
    Robot.limelight.setPipeline(LimelightPipelines.idle);
  }
}
