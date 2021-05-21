package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.awtybots.frc.botplus.Logger;
import org.awtybots.frc.botplus.motors.Pro775;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class TurretSubsystem extends SubsystemBase {

  // this configuration assumes the turret can go +/- 45 degrees from start
  // position, try expanding the range to 0-360 if you think it's safe
  private final double minAngle = 135;
  private final double maxAngle = 225;
  private double startAngle = 180;
  private double goalAngle = startAngle; // setpoint
  private double lastCurrentAngle = startAngle;

  private double slowDownWithinThisAngleFromGoal = 5.0; // TODO tune please
  private double minimumPercentOutput = 0.2;
  private double maximumPercentOutput = 0.8;

  private final double sensorGearRatio = 1.0 / 400.0; // TODO ratio between actual output rotation and encoder-detected
                                                      // rotation
  private Pro775 motor = new Pro775(RobotMap.CAN.turret, 1.0);

  private Logger logger = new Logger("Turret");

  public TurretSubsystem() {
    motor.getMotorController().configFactoryDefault();
    motor.getMotorController().setNeutralMode(NeutralMode.Brake);

    motor.setSensorGearRatio(sensorGearRatio);
    motor.resetSensorPosition();

    // motor.getMotorController().setInverted(true); // here if you need it
  }

  /**
   * Returns true if the <i>relative</i> goal angle is reachable. Note that if the
   * reachable range of the turret is 0 - 360 degrees, every angle is reachable.
   * It may have to spin backwards like a ballerina though.
   * 
   * @param newGoalAngle relative goal angle in degrees
   */
  public boolean isRelativeAngleInBounds(double newGoalAngle) {
    newGoalAngle += lastCurrentAngle;
    newGoalAngle %= 360.0;

    return minAngle <= newGoalAngle && newGoalAngle < maxAngle; // note that min is inclusive, max is exclusive
  }

  public void setRelativeGoalAngle(double newGoalAngle) {
    if (!isRelativeAngleInBounds(newGoalAngle)) {
      logger.error("Something tried to set the turret out of bounds! Next time, use isRelativeAngleInBounds() first.");
      return;
    }

    newGoalAngle += lastCurrentAngle;
    newGoalAngle %= 360.0;
    goalAngle = newGoalAngle;
  }

  public boolean atGoalAngle() {
    return Math.abs(goalAngle - lastCurrentAngle) < slowDownWithinThisAngleFromGoal * minimumPercentOutput;
  }

  public double getCurrentAngle() {
    double tentativeCurrentAngle = startAngle + motor.getOutputRevsCompleted() * 360.0;
    if (tentativeCurrentAngle < minAngle - 3 * slowDownWithinThisAngleFromGoal
        || tentativeCurrentAngle > maxAngle + 3 * slowDownWithinThisAngleFromGoal) {
      return lastCurrentAngle;
    } else {
      lastCurrentAngle = tentativeCurrentAngle;
      return lastCurrentAngle;
    }
  }

  public void relax() {
    goalAngle = lastCurrentAngle;
  }

  @Override
  public void periodic() {
    double angleError = goalAngle - getCurrentAngle();

    double motorOutput = Math.min(angleError / slowDownWithinThisAngleFromGoal, maximumPercentOutput);
    if (motorOutput < minimumPercentOutput) {
      motorOutput = 0;
    }

    motor.setRawOutput(motorOutput);
  }

  private static TurretSubsystem instance;

  public static TurretSubsystem getInstance() {
    if (instance == null)
      instance = new TurretSubsystem();
    return instance;
  }
}
