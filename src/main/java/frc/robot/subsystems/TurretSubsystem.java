package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class TurretSubsystem extends SubsystemBase {

  public static final boolean isThereEvenATurretOnTheRobot = false;

  // this configuration assumes the turret can go +/- 45 degrees from start
  // position, try expanding the range to 0-360 if you think it's safe
  public static final double minAngle = 100;
  public static final double maxAngle = 270;
  public static final double startAngle = 180;
  private double goalAngle = startAngle; // goalAngle is setpoint
  private double lastCurrentAngle = startAngle;

  private double slowDownWithinThisAngleFromGoal = 20.0;
  private double stopWithinThisAngleFromGoal = 3.0;
  private double minimumPercentOutput = 0.13;
  private double maximumPercentOutput = 0.23;

  private final double sensorGearRatio =
      22.0 / 240.0; // ratio between actual output rotation and encoder-detected
  // rotation
  private Pro775 motor = new Pro775(RobotMap.CAN.turret, 1.0);

  public TurretSubsystem() {
    if(isThereEvenATurretOnTheRobot) {
      motor.getMotorController().configFactoryDefault();
      motor.getMotorController().setNeutralMode(NeutralMode.Coast);

      motor.setSensorGearRatio(sensorGearRatio);
      motor.resetSensorPosition();

      motor.getMotorController().setInverted(true);
      motor.getMotorController().setSensorPhase(true);
    }
  }

  public void setAbsoluteGoalAngle(double newGoalAngle) {
    if(!isThereEvenATurretOnTheRobot) return;

    newGoalAngle %= 360.0;
    goalAngle = MathUtil.clamp(newGoalAngle, minAngle, maxAngle);
    
    boolean goalReachable = newGoalAngle < minAngle || newGoalAngle > maxAngle;
    SmartDashboard.putBoolean("Turret Goal Reachable", goalReachable);
  }

  public void setRelativeGoalAngle(double newGoalAngle) {
    setAbsoluteGoalAngle(lastCurrentAngle + newGoalAngle);
  }

  public boolean atGoalAngle() {
    return Math.abs(goalAngle - lastCurrentAngle) < stopWithinThisAngleFromGoal || !isThereEvenATurretOnTheRobot;
  }

  public double getCurrentAngle() {
    if(!isThereEvenATurretOnTheRobot) return startAngle;

    double tentativeCurrentAngle = startAngle + motor.getOutputRevsCompleted() * 360.0;

    SmartDashboard.putBoolean("Turret In Bounds", tentativeCurrentAngle >= (minAngle - stopWithinThisAngleFromGoal) && tentativeCurrentAngle <= (maxAngle + stopWithinThisAngleFromGoal));

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
    if(!isThereEvenATurretOnTheRobot) return;

    double currentAngle = getCurrentAngle();
    double angleError = goalAngle - currentAngle;

    SmartDashboard.putNumber("Turret Current Angle", currentAngle);
    SmartDashboard.putNumber("Turret Goal Angle", goalAngle);
    SmartDashboard.putNumber("Turret Error Angle", angleError);
    SmartDashboard.putBoolean("Turret At Goal", atGoalAngle());

    double x = Math.min((Math.abs(angleError) - stopWithinThisAngleFromGoal) / (slowDownWithinThisAngleFromGoal - stopWithinThisAngleFromGoal), 1.0);
    double motorOutput = (x * maximumPercentOutput) + ((1 - x) * minimumPercentOutput);
    if (motorOutput < minimumPercentOutput) {
      motorOutput = 0;
    }
    motorOutput *= Math.signum(angleError);

    SmartDashboard.putNumber("Turret Motor Output", motorOutput);
    motor.setRawOutput(motorOutput);
  }

  private static TurretSubsystem instance;

  public static TurretSubsystem getInstance() {
    if (instance == null) instance = new TurretSubsystem();
    return instance;
  }
}
