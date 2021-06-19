package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.RobotMap;

public class TurretSubsystem extends SubsystemBase {

  public static final double minAngle = 135;
  public static final double maxAngle = 225;
  public static final double startAngle = 180;
  private double goalAngle = startAngle; // goalAngle is setpoint
  private double lastCurrentAngle = startAngle;

  private double slowDownWithinThisAngleFromGoal = 20.0;
  private double stopWithinThisAngleFromGoal = 3.0;
  private double minimumPercentOutput = 0.13;
  private double maximumPercentOutput = 0.23;

  private final double turretRatio = 22.0 / 240.0; // ratio between turret pulley and motor pulley
  // rotation
  private TalonSRX motor;

  private TurretSubsystem() {
    motor = new TalonSRX(RobotMap.CAN.turret);

    motor.configFactoryDefault();
    motor.setNeutralMode(NeutralMode.Coast);

    motor.setSelectedSensorPosition(0);

    motor.setInverted(true);
    motor.setSensorPhase(true);
  }

  public boolean setAbsoluteGoalAngle(double newGoalAngle) {
    newGoalAngle %= 360.0;
    goalAngle = MathUtil.clamp(newGoalAngle, minAngle, maxAngle);

    boolean goalReachable = newGoalAngle >= minAngle && newGoalAngle < maxAngle;
    return goalReachable;
  }

  public boolean setRelativeGoalAngle(double newGoalAngle) {
    return setAbsoluteGoalAngle(lastCurrentAngle + newGoalAngle);
  }

  public boolean atGoalAngle() {
    return Math.abs(goalAngle - lastCurrentAngle) < stopWithinThisAngleFromGoal;
  }

  public double getCurrentAngle() {
    double tentativeCurrentAngle = startAngle /*+ motor.getOutputRevsCompleted()*/ * 360.0;

    if (tentativeCurrentAngle < minAngle - slowDownWithinThisAngleFromGoal
        || tentativeCurrentAngle > maxAngle + slowDownWithinThisAngleFromGoal) {
      return lastCurrentAngle;
    } else {
      lastCurrentAngle = tentativeCurrentAngle;
      return lastCurrentAngle;
    }
  }

  public void relax() {
    goalAngle = lastCurrentAngle;
  }

  public void returnToStart() {
    goalAngle = startAngle;
  }

  @Override
  public void periodic() {
    double currentAngle = getCurrentAngle();
    double angleError = goalAngle - currentAngle;

    double x =
        Math.min(
            (Math.abs(angleError) - stopWithinThisAngleFromGoal)
                / (slowDownWithinThisAngleFromGoal - stopWithinThisAngleFromGoal),
            1.0);
    double motorOutput = (x * maximumPercentOutput) + ((1 - x) * minimumPercentOutput);
    if (motorOutput < minimumPercentOutput) {
      motorOutput = 0;
    }
    motorOutput *= Math.signum(angleError);

    motor.set(ControlMode.PercentOutput, motorOutput);
  }

  private static TurretSubsystem instance = new TurretSubsystem();

  public static TurretSubsystem getInstance() {
    return instance;
  }
}
