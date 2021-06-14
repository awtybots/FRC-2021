package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class AdjustableHoodSubsystem extends SubsystemBase {

  private final boolean encoderGlitchDetection = true;
  private final boolean encoderGlitchRecovery =
      true; // resets the encoder and pretends like nothing ever happened, may
  // work first try, may destroy robot idk

  /*
   * Note that "angle" means how elevated the shot is from horizontal. An angle of
   * 0 is a flat shot, 45 is a diagonal shot, and 90 is straight up.
   */
  public static final double minLaunchAngle = 57;
  public static final double maxLaunchAngle = 76;
  private double startAngle = maxLaunchAngle;
  private double goalLaunchAngle = startAngle; // setpoint
  private double lastCurrentAngle = startAngle;

  private final double slowDownWithinThisAngleFromGoal = 6.0;
  private final double stopWithinThisAngleFromGoal = 1.0;
  private final double minimumPercentOutput = 0.15;
  private final double maximumPercentOutput = 0.2;
  private final double workingWithGravityAdjustment = 0.75;

  private final double encoderBrokenTime = 1.0; // seconds
  private final double postBrokenRecoveryTime =
      1.0; // even though it's the same amount of time, it's working with gravity
  // on the way down

  private final double sensorGearRatio = 15.0 / 72.0;
  private Pro775 motor = new Pro775(RobotMap.CAN.adjustableHood, 1.0);

  private final Timer constantAngleTimer = new Timer();
  private double beforeLastCurrentAngle = lastCurrentAngle;

  private boolean encoderBroken = false;
  private boolean currentlyRecovering = false;

  private AdjustableHoodSubsystem() {
    motor.getMotorController().configFactoryDefault();
    motor.getMotorController().setNeutralMode(NeutralMode.Coast);

    motor.setSensorGearRatio(sensorGearRatio);
    motor.resetSensorPosition();

    constantAngleTimer.start();
  }

  public void setGoalLaunchAngle(double newGoalLaunchAngle) {
    goalLaunchAngle = MathUtil.clamp(newGoalLaunchAngle, minLaunchAngle, maxLaunchAngle);
  }

  public boolean atGoalLaunchAngle() {
    return Math.abs(goalLaunchAngle - lastCurrentAngle)
        < slowDownWithinThisAngleFromGoal * minimumPercentOutput;
  }

  public double getCurrentLaunchAngle() {
    double tentativeCurrentAngle = startAngle + motor.getOutputRevsCompleted() * 360.0;

    SmartDashboard.putBoolean(
        "Hood In Bounds",
        tentativeCurrentAngle >= (minLaunchAngle - stopWithinThisAngleFromGoal)
            && tentativeCurrentAngle <= (maxLaunchAngle + stopWithinThisAngleFromGoal));

    if (tentativeCurrentAngle < minLaunchAngle - slowDownWithinThisAngleFromGoal
        || tentativeCurrentAngle > maxLaunchAngle + slowDownWithinThisAngleFromGoal) {
      return lastCurrentAngle;
    } else {
      lastCurrentAngle = tentativeCurrentAngle;
      return lastCurrentAngle;
    }
  }

  @Override
  public void periodic() {
    double currentLaunchAngle = getCurrentLaunchAngle();
    double angleError = goalLaunchAngle - currentLaunchAngle;

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

    if (angleError > 0) {
      motorOutput *= workingWithGravityAdjustment;
    }

    if (encoderGlitchDetection) {
      if (!encoderBroken) {
        if (Math.abs(motorOutput) > 0) {
          if (lastCurrentAngle != beforeLastCurrentAngle) {
            beforeLastCurrentAngle = lastCurrentAngle;
            constantAngleTimer.reset();
          } else if (constantAngleTimer.get() > encoderBrokenTime) {
            encoderBroken = true;
            currentlyRecovering = true;
            motor.setRawOutput(maximumPercentOutput); // positive goes towards max angle
          }
        } else {
          constantAngleTimer.reset();
          beforeLastCurrentAngle = lastCurrentAngle;
        }
      } else if (currentlyRecovering) {
        if (constantAngleTimer.get() > encoderBrokenTime + postBrokenRecoveryTime) {
          motor.setRawOutput(0);
          currentlyRecovering = false;
          constantAngleTimer.reset();

          if (encoderGlitchRecovery) {
            encoderBroken = false;
            motor.resetSensorPosition();
          }
        }
      }
    }

    SmartDashboard.putNumber("Hood Current Angle", currentLaunchAngle);
    SmartDashboard.putNumber("Hood Goal Angle", goalLaunchAngle);
    SmartDashboard.putNumber("Hood Error Angle", angleError);
    SmartDashboard.putBoolean("Hood At Goal", atGoalLaunchAngle());
    SmartDashboard.putNumber("Hood Motor Output", motorOutput);
    SmartDashboard.putBoolean("Hood Encoder Working", !encoderBroken);

    if (!encoderBroken) motor.setRawOutput(motorOutput);
  }

  private static AdjustableHoodSubsystem instance = new AdjustableHoodSubsystem();

  public static AdjustableHoodSubsystem getInstance() {
    return instance;
  }
}
