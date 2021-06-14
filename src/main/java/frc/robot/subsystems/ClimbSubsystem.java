package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Falcon500;

public class ClimbSubsystem extends SubsystemBase {

  private final double percentOutput = 0.75;

  private final Falcon500 climber = new Falcon500(RobotMap.CAN.climber, 8.0 / 72.0 * 18.0 / 84.0);

  private ClimbSubsystem() {
    climber.getMotorController().configFactoryDefault();
    // climber.getMotorController().setInverted(true);
    climber.getMotorController().setNeutralMode(NeutralMode.Brake);
    climber.getMotorController().configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    climber.resetSensorPosition();
  }

  public void set(int d) {
    climber.setRawOutput(percentOutput * d);
  }

  @Override
  public void periodic() {
    double current = Robot.pdp.getCurrent(RobotMap.PDP.climber);
    double encoderPos = climber.getOutputRevsCompleted() * RobotMap.Dimensions.climberWinchCircumference;

    SmartDashboard.putNumber("Climber Current", current);
    SmartDashboard.putNumber("Climber Position", encoderPos);
  }

  private static ClimbSubsystem instance = new ClimbSubsystem();

  public static ClimbSubsystem getInstance() {
    return instance;
  }
}
