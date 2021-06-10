package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.motors.Pro775;

public class TurretSubsystem extends SubsystemBase {

  // this configuration assumes the turret can go +/- 45 degrees from start
  // position, try expanding the range to 0-360 if you think it's safe
  public static final double minAngle = 90;
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

  private Logger logger = new Logger("Turret");

  public TurretSubsystem() {
    motor.getMotorController().configFactoryDefault();
    motor.getMotorController().setNeutralMode(NeutralMode.Coast);

    motor.setSensorGearRatio(sensorGearRatio);
    motor.resetSensorPosition();

    motor.getMotorController().setInverted(true);
    motor.getMotorController().setSensorPhase(true);
  }

  /**
   * Returns true if the <i>relative</i> goal angle is reachable. Note that if the reachable range
   * of the turret is 0 - 360 degrees, every angle is reachable. It may have to spin backwards like
   * a ballerina though.
   *
   * @param newGoalAngle relative goal angle in degrees
   */
  public boolean isRelativeAngleInBounds(double newGoalAngle) {
    newGoalAngle += lastCurrentAngle;
    newGoalAngle %= 360.0;

    return minAngle <= newGoalAngle
        && newGoalAngle < maxAngle; // note that min is inclusive, max is exclusive
  }

  public void setRelativeGoalAngle(double newGoalAngle) {
    if (!isRelativeAngleInBounds(newGoalAngle)) {
      logger.error(
          "Something tried to set the turret out of bounds! Next time, use isRelativeAngleInBounds() first.");
      return;
    }

    newGoalAngle += lastCurrentAngle;
    newGoalAngle %= 360.0;
    goalAngle = newGoalAngle;
  }

  public boolean atGoalAngle() {
    return Math.abs(goalAngle - lastCurrentAngle)
        < slowDownWithinThisAngleFromGoal * minimumPercentOutput;
  }

  public double getCurrentAngle() {
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
