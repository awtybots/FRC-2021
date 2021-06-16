package frc.robot.commands.teleop.automatic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.LimelightPipelines;
import frc.robot.subsystems.TurretSubsystem;

public class AutoAimUsingTurret extends CommandBase {

  private boolean hunting = false;
  private boolean huntingToMax = true;

  public AutoAimUsingTurret() {
    addRequirements(TurretSubsystem.getInstance());
  }

  @Override
  public void initialize() {
    Robot.limelight.setPipeline(LimelightPipelines.powerPort);

    hunting = false;
  }

  @Override
  public void execute() {
    boolean hasVisibleTarget = Robot.limelight.getHasVisibleTarget();
    SmartDashboard.putBoolean("Limelight Target Visible", hasVisibleTarget);

    if (hasVisibleTarget) {
      if (hunting) {
        hunting = false;
      }
    } else {
      if (hunting) {
        if (TurretSubsystem.getInstance().atGoalAngle()) {
          huntingToMax = !huntingToMax;
          TurretSubsystem.getInstance()
              .setAbsoluteGoalAngle(
                  huntingToMax ? TurretSubsystem.maxAngle : TurretSubsystem.minAngle);
        }
      } else {
        hunting = true;
        huntingToMax = true;

        TurretSubsystem.getInstance().setAbsoluteGoalAngle(TurretSubsystem.maxAngle);
      }
      return;
    }

    double x = Robot.limelight.getXOffset();
    SmartDashboard.putNumber("Limelight Target X Offset", x);

    TurretSubsystem.getInstance().setRelativeGoalAngle(x);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.limelight.setDriverMode(true);
    TurretSubsystem.getInstance().returnToStart();
  }
}
