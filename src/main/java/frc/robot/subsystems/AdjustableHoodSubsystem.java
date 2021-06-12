package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class AdjustableHoodSubsystem extends SubsystemBase {

  /*
   * Note that "angle" means how elevated the shot is from horizontal. An angle of
   * 0 is a flat shot, 45 is a diagonal shot, and 90 is straight up.
   */
  public static final double minAngle = 57;
  public static final double maxAngle = 76;
  private double startAngle = maxAngle;
  private double goalAngle = startAngle; // setpoint
  private double lastCurrentAngle = startAngle;

  private final boolean encoderGlitchDetection = true;

  private final double slowDownWithinThisAngleFromGoal = 6.0;
  private final double stopWithinThisAngleFromGoal = 1.0;
  private final double minimumPercentOutput = 0.15;
  private final double maximumPercentOutput = 0.2;
  private final double workingWithGravityAdjustment = 0.75;
  private final double encoderBrokenTime = 1.0; // seconds

  private final double sensorGearRatio = 15.0 / 72.0;
  private Pro775 motor = new Pro775(RobotMap.CAN.adjustableHood, 1.0);

  private final Timer constantAngleTimer = new Timer();
  private double beforeLastCurrentAngle = lastCurrentAngle;
  private boolean encoderBroken = false;
  private boolean postEncoderBrokenResetComplete = false;
  private final double stallCurrent = 50;

  public AdjustableHoodSubsystem() {
    motor.getMotorController().configFactoryDefault();
    motor.getMotorController().setNeutralMode(NeutralMode.Coast);

    motor.setSensorGearRatio(sensorGearRatio);
    motor.resetSensorPosition();

    // SmartDashboard.putNumber("Hood Goal Angle", startAngle); // for setting

    constantAngleTimer.start();
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

    SmartDashboard.putBoolean("Hood In Bounds", tentativeCurrentAngle >= (minAngle - stopWithinThisAngleFromGoal) && tentativeCurrentAngle <= (maxAngle + stopWithinThisAngleFromGoal));

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
    // goalAngle = SmartDashboard.getNumber("Hood Goal Angle", goalAngle); // for setting

    double currentAngle = getCurrentAngle();
    double angleError = goalAngle - currentAngle;

    double x = Math.min((Math.abs(angleError) - stopWithinThisAngleFromGoal) / (slowDownWithinThisAngleFromGoal - stopWithinThisAngleFromGoal), 1.0);
    double motorOutput = (x * maximumPercentOutput) + ((1 - x) * minimumPercentOutput);
    if (motorOutput < minimumPercentOutput) {
      motorOutput = 0;
    }
    motorOutput *= Math.signum(angleError);

    if(angleError > 0) {
      motorOutput *= workingWithGravityAdjustment;
    }

    if(encoderGlitchDetection) {
      if(!encoderBroken) {
        if(Math.abs(motorOutput) > 0) {
          if(lastCurrentAngle != beforeLastCurrentAngle) {
            beforeLastCurrentAngle = lastCurrentAngle;
            constantAngleTimer.reset();
          } else if (constantAngleTimer.get() > encoderBrokenTime) {
            encoderBroken = true;
            motor.setRawOutput(minimumPercentOutput);
          }
        } else {
          constantAngleTimer.reset();
        }
      } else if (!postEncoderBrokenResetComplete) {
        if(Robot.pdp.getCurrent(RobotMap.PDP.adjustableHood) > stallCurrent * maximumPercentOutput) {
          motor.setRawOutput(0);
          postEncoderBrokenResetComplete = true;
          encoderBroken = false;
          motor.resetSensorPosition();
        }
      }
    }

    SmartDashboard.putNumber("Hood Current Angle", currentAngle);
    SmartDashboard.putNumber("Hood Goal Angle", goalAngle);
    SmartDashboard.putNumber("Hood Error Angle", angleError);
    SmartDashboard.putBoolean("Hood At Goal", atGoalAngle());
    SmartDashboard.putNumber("Hood Motor Output", motorOutput);
    SmartDashboard.putBoolean("Hood Encoder Working", !encoderBroken);

    if(!encoderBroken) motor.setRawOutput(motorOutput);
  }

  private static AdjustableHoodSubsystem instance;

  public static AdjustableHoodSubsystem getInstance() {
    if (instance == null) instance = new AdjustableHoodSubsystem();
    return instance;
  }
}
