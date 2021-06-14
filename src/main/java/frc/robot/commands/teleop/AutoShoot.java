package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.LimelightPipelines;
import frc.robot.subsystems.AdjustableHoodSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TowerSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.math.Simulation;
import org.awtybots.frc.botplus.math.Vector2;
import org.awtybots.frc.botplus.math.VisionTarget;

public class AutoShoot extends CommandBase {

  private Logger logger = new Logger("AutoShoot");

  private VisionTarget powerPort;
  private Simulation projectileMotionSimulation;

  public AutoShoot() {
    addRequirements(
        ShooterSubsystem.getInstance(),
        // TowerSubsystem.getInstance(),
        AdjustableHoodSubsystem.getInstance());

    SmartDashboard.putBoolean("Projectile Motion Solution", true);

    powerPort =
        new VisionTarget(
            Robot.limelight,
            2.496 - 0.216, // vision target height
            0.216 // offset from vision target to center of power port
            );
    projectileMotionSimulation =
        new Simulation(
            0.01, // simulation step (seconds)
            10, // simulation iterations
            0.178, // ball radius (m)
            0.142, // ball mass (kg)
            45, // launch angle (degrees), gets overwritten periodically so this doesn't matter
            ShooterSubsystem.getInstance().flywheel.getMaxBallVelocity(),
            false // debug mode
            );
  }

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(LimelightPipelines.powerPort);
  }

  @Override
  public void execute() {
    if (!Robot.limelight.getHasVisibleTarget()) {
      logger.warn("Cannot auto-shoot without visible target!");
      return;
    }

    Vector2 powerPortOffset = powerPort.getTargetDisplacement();
    SmartDashboard.putNumber("Power Port Perceived Distance", powerPortOffset.getX());

    double adjustableHoodGoalLaunchAngle =
        Math.toDegrees(Math.atan2(powerPortOffset.getX(), powerPortOffset.getY() + 1.0));

    AdjustableHoodSubsystem.getInstance().setGoalLaunchAngle(adjustableHoodGoalLaunchAngle);

    projectileMotionSimulation.setLaunchAngle(
        AdjustableHoodSubsystem.getInstance().getCurrentLaunchAngle());
    Vector2 velocity = projectileMotionSimulation.findOptimalLaunchVelocity(powerPortOffset);

    if (velocity == null) {
      SmartDashboard.putBoolean("Projectile Motion Solution", false);
      logger.warn(
          "Projectile motion simulation found no solution! Move the robot to a better shooting position.");
      return;
    }

    SmartDashboard.putBoolean("Projectile Motion Solution", true);

    double goalRevsPerSecond =
        ShooterSubsystem.getInstance().flywheel.ballVelocityToMotorRpm(velocity) / 60.0;
    ShooterSubsystem.getInstance().setFlywheelRevsPerSecond(goalRevsPerSecond);

    boolean readyToShoot =
        ShooterSubsystem.getInstance().isFlywheelReady()
            && TurretSubsystem.getInstance().atGoalAngle();
    
    if(readyToShoot) {
      TowerSubsystem.getInstance().enableForShooting();
    } else {
      TowerSubsystem.getInstance().stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    ShooterSubsystem.getInstance().stopFlywheel();
    TowerSubsystem.getInstance().stop();

    Robot.limelight.setPipeline(LimelightPipelines.idle);
  }
}
