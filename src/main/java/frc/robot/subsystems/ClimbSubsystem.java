package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.awtybots.frc.botplus.motors.Falcon500;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ClimbSubsystem extends SubsystemBase {

  private final double percentOutput = 0.75;

  private final Falcon500 climber = new Falcon500(RobotMap.CAN.climber, 1.0);

  private ClimbSubsystem() {
    climber.getMotorController().configFactoryDefault();
    // climber.getMotorController().setInverted(true);
    climber.getMotorController().setNeutralMode(NeutralMode.Brake);
  }

  public void set(int d) {
    climber.setRawOutput(percentOutput * d);
  }

  private static ClimbSubsystem instance = new ClimbSubsystem();

  public static ClimbSubsystem getInstance() {
    return instance;
  }
  
}
