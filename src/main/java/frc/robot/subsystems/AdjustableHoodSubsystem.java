package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

// TODO hood is completely broken in some way, come back to this after the event
public class AdjustableHoodSubsystem extends SubsystemBase {

  // private final boolean encoderGlitchDetection = false;
  // private final boolean encoderGlitchRecovery = false;
  // the above resets the encoder and pretends like nothing ever happened, may work first try, may
  // destroy robot idk

  public static final double minLaunchAngle = 49;
  public static final double maxLaunchAngle = 76;
  private double startAngle = maxLaunchAngle;
  private double goalLaunchAngle = startAngle; // setpoint
  private double lastCurrentAngle = startAngle;

  private final double slowDownWithinThisAngleFromGoal = 6.0;
  private final double stopWithinThisAngleFromGoal = 1.0;
  // private final double minimumPercentOutput = 0.15;
  // private final double maximumPercentOutput = 0.2;
  // private final double workingWithGravityAdjustment = 0.75;

  // private final double encoderBrokenTime = 1.0; // seconds
  // private final double postBrokenRecoveryTime =
  //     1.0; // even though it's the same amount of time, it's working with gravity
  // on the way down

  private final double sensorGearRatio = 15.0 / 72.0;
  public TalonSRX motor = new TalonSRX(RobotMap.CAN.adjustableHood);

  // private final Timer constantAngleTimer = new Timer();
  // private double beforeLastCurrentAngle = lastCurrentAngle;

  private boolean encoderBroken = false;
  // private boolean currentlyRecovering = false;

  private AdjustableHoodSubsystem() {
    motor.configFactoryDefault();
    motor.setNeutralMode(NeutralMode.Coast);

    motor.setSelectedSensorPosition(0);

    // constantAngleTimer.start();
  }

  public void setGoalLaunchAngle(double newGoalLaunchAngle) {
    // goalLaunchAngle = MathUtil.clamp(newGoalLaunchAngle, minLaunchAngle, maxLaunchAngle);
  }

  public boolean atGoalLaunchAngle() {
    return true;
    // return Math.abs(goalLaunchAngle - lastCurrentAngle)
    //     < slowDownWithinThisAngleFromGoal * minimumPercentOutput;
  }

  public double getCurrentLaunchAngle() {
    double tentativeCurrentAngle = startAngle /*+ motor.getOutputRevsCompleted()*/ * 360.0;

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
    // double currentLaunchAngle = getCurrentLaunchAngle();
    // double angleError = goalLaunchAngle - currentLaunchAngle;

    // double x =
    //     Math.min(
    //         (Math.abs(angleError) - stopWithinThisAngleFromGoal)
    //             / (slowDownWithinThisAngleFromGoal - stopWithinThisAngleFromGoal),
    //         1.0);
    // double motorOutput = (x * maximumPercentOutput) + ((1 - x) * minimumPercentOutput);
    // if (motorOutput < minimumPercentOutput) {
    //   motorOutput = 0;
    // }
    // motorOutput *= Math.signum(angleError);

    // if (angleError > 0) {
    //   motorOutput *= workingWithGravityAdjustment;
    // }

    // if (encoderGlitchDetection) {
    //   if (!encoderBroken) {
    //     if (Math.abs(motorOutput) > 0) {
    //       if (lastCurrentAngle != beforeLastCurrentAngle) {
    //         beforeLastCurrentAngle = lastCurrentAngle;
    //         constantAngleTimer.reset();
    //       } else if (constantAngleTimer.get() > encoderBrokenTime) {
    //         encoderBroken = true;
    //         currentlyRecovering = true;
    //         motor.setRawOutput(maximumPercentOutput); // positive goes towards max angle
    //       }
    //     } else {
    //       constantAngleTimer.reset();
    //       beforeLastCurrentAngle = lastCurrentAngle;
    //     }
    //   } else if (currentlyRecovering) {
    //     if (constantAngleTimer.get() > encoderBrokenTime + postBrokenRecoveryTime) {
    //       motor.setRawOutput(0);
    //       currentlyRecovering = false;
    //       constantAngleTimer.reset();

    //       if (encoderGlitchRecovery) {
    //         encoderBroken = false;
    //         motor.resetSensorPosition();
    //       }
    //     }
    //   }
    // }

    // if (!encoderBroken) motor.setRawOutput(motorOutput);
  }

  private static AdjustableHoodSubsystem instance = new AdjustableHoodSubsystem();

  public static AdjustableHoodSubsystem getInstance() {
    return instance;
  }
}
