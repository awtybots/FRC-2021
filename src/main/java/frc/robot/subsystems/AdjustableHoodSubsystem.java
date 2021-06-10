package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class AdjustableHoodSubsystem extends SubsystemBase {

  /*
   * Note that "angle" means how elevated the shot is from horizontal. An angle of
   * 0 is a flat shot, 45 is a diagonal shot, and 90 is straight up.
   */
  private final double minAngle = 57;
  private final double maxAngle = 76;
  private double startAngle = minAngle;
  private double goalAngle = startAngle; // setpoint
  private double lastCurrentAngle = startAngle;

  private double slowDownWithinThisAngleFromGoal = 10.0; // TODO tune please
  private double stopWithinThisAngleFromGoal = 3.0;
  private double minimumPercentOutput = 0.1;
  private double maximumPercentOutput = 0.2;

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

    SmartDashboard.putBoolean("Hood In Bounds", tentativeCurrentAngle >= minAngle && tentativeCurrentAngle <= maxAngle);

    if (tentativeCurrentAngle < minAngle - slowDownWithinThisAngleFromGoal
        || tentativeCurrentAngle > maxAngle + slowDownWithinThisAngleFromGoal) {
      return lastCurrentAngle;
    } else {
      lastCurrentAngle = tentativeCurrentAngle;
      return lastCurrentAngle;
    }
  }

  @Override
  public void periodic() {
    double currentAngle = getCurrentAngle();
    double angleError = goalAngle - currentAngle;

    SmartDashboard.putNumber("Hood Current Angle", currentAngle);
    SmartDashboard.putNumber("Hood Goal Angle", goalAngle);
    SmartDashboard.putNumber("Hood Error Angle", angleError);

    double x = Math.min((Math.abs(angleError) - stopWithinThisAngleFromGoal) / (slowDownWithinThisAngleFromGoal - stopWithinThisAngleFromGoal), 1.0);
    double motorOutput = (x * maximumPercentOutput) + ((1 - x) * minimumPercentOutput);
    if (motorOutput < minimumPercentOutput) {
      motorOutput = 0;
    }
    motorOutput *= Math.signum(angleError);

    SmartDashboard.putNumber("Hood Motor Output", motorOutput);
    // motor.setRawOutput(motorOutput);
  }

  private static AdjustableHoodSubsystem instance;

  public static AdjustableHoodSubsystem getInstance() {
    if (instance == null) instance = new AdjustableHoodSubsystem();
    return instance;
  }
}
