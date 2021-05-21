package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.TowerSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.subsystems.AdjustableHoodSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.math.Simulation;
import org.awtybots.frc.botplus.math.Vector2;
import org.awtybots.frc.botplus.math.VisionTarget;
import org.awtybots.frc.botplus.sensors.vision.Limelight.LEDMode;

public class AutoShoot extends CommandBase {

  private Logger logger = new Logger("AutoShoot");

  private VisionTarget powerPort;
  private Simulation projectileMotionSimulation;

  public AutoShoot() {
    addRequirements(ShooterSubsystem.getInstance(), TowerSubsystem.getInstance(), AdjustableHoodSubsystem.getInstance());

    powerPort =
        new VisionTarget(
            Robot.limelight,
            2.0, // power port height - TODO fix
            0.5 // power port offset from vision target - TODO fix
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
    Robot.limelight.setPipeline(0);
    Robot.limelight.setLedMode(LEDMode.On);
  }

  @Override
  public void execute() {
    if (!Robot.limelight.getHasVisibleTarget()) {
      logger.error("Cannot auto-shoot without visible target!");
      return;
    }

    Vector2 powerPortOffset = powerPort.getTargetDisplacement();
    SmartDashboard.putNumber("Power Port Perceived Distance", powerPortOffset.getX());
    double angleDirectlyToPowerPort = Math.toDegrees(Math.atan2(powerPort.getTargetHeight(), powerPortOffset.getX()));
    AdjustableHoodSubsystem.getInstance().setGoalAngle(15.0 + angleDirectlyToPowerPort); // TODO this is a rough estimate, please tune

    projectileMotionSimulation.setLaunchAngle(AdjustableHoodSubsystem.getInstance().getCurrentAngle());
    Vector2 velocity = projectileMotionSimulation.findOptimalLaunchVelocity(powerPortOffset);

    if (velocity == null) {
      logger.error(
          "Projectile motion simulation found no solution! Move the robot to a better shooting position.");
      return;
    }

    double goalRevsPerSecond =
        ShooterSubsystem.getInstance().flywheel.ballVelocityToMotorRpm(velocity) / 60.0;
    ShooterSubsystem.getInstance().setFlywheelRevsPerSecond(goalRevsPerSecond);

    boolean readyToShoot = ShooterSubsystem.getInstance().isFlywheelReady() && TurretSubsystem.getInstance().atGoalAngle();
    TowerSubsystem.getInstance().toggle(readyToShoot);
  }

  @Override
  public void end(boolean interrupted) {
    ShooterSubsystem.getInstance().stopFlywheel();
    TowerSubsystem.getInstance().toggle(false);
  }
}
