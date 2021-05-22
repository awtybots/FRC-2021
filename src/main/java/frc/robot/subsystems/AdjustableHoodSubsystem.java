package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class AdjustableHoodSubsystem extends SubsystemBase {

  /*
   * Note that "angle" means how elevated the shot is from horizontal. An angle of
   * 0 is a flat shot, 45 is a diagonal shot, and 90 is straight up.
   */
  private final double minAngle = 30;
  private final double maxAngle = 60;
  private double startAngle = 45;
  private double goalAngle = startAngle; // setpoint
  private double lastCurrentAngle = startAngle;

  private double slowDownWithinThisAngleFromGoal = 5.0; // TODO tune please
  private double minimumPercentOutput = 0.2;
  private double maximumPercentOutput = 0.8;

  private final double sensorGearRatio =
      1.0 / 400.0; // TODO ratio between actual output rotation and encoder-detected rotation
  private Pro775 motor = new Pro775(RobotMap.CAN.adjustableHood, 1.0);

  public AdjustableHoodSubsystem() {
    motor.getMotorController().configFactoryDefault();
    motor.getMotorController().setNeutralMode(NeutralMode.Brake);

    motor.setSensorGearRatio(sensorGearRatio);
    motor.resetSensorPosition();

    // motor.getMotorController().setInverted(true); // here if you need it
  }

  public void setGoalAngle(double newGoalAngle) {
    goalAngle = MathUtil.clamp(newGoalAngle, minAngle, maxAngle);
  }

  public boolean atGoalAngle() {
    return Math.abs(goalAngle - lastCurrentAngle)
        < slowDownWithinThisAngleFromGoal * minimumPercentOutput;
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

  @Override
  public void periodic() {
    double angleError = goalAngle - getCurrentAngle();

    double motorOutput =
        Math.min(angleError / slowDownWithinThisAngleFromGoal, maximumPercentOutput);
    if (motorOutput < minimumPercentOutput) {
      motorOutput = 0;
    }

    motor.setRawOutput(motorOutput);
  }

  private static AdjustableHoodSubsystem instance;

  public static AdjustableHoodSubsystem getInstance() {
    if (instance == null) instance = new AdjustableHoodSubsystem();
    return instance;
  }
}
