package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ClimbSubsystem extends SubsystemBase {

  private final double percentOutput = 0.75;

  private final TalonFX climber = new TalonFX(RobotMap.CAN.climber);

  private ClimbSubsystem() {
    climber.configFactoryDefault();
    climber.setInverted(true);
    climber.setNeutralMode(NeutralMode.Brake);
    climber.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    climber.setSelectedSensorPosition(0);
  }

  public void set(int d) {
    climber.set(ControlMode.PercentOutput, percentOutput * d);
  }

  private static ClimbSubsystem instance = new ClimbSubsystem();

  public static ClimbSubsystem getInstance() {
    return instance;
  }
}
