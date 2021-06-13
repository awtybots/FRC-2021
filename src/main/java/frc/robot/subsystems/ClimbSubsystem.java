package frc.robot.subsystems;

import org.awtybots.frc.botplus.motors.Falcon500;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ClimbSubsystem extends SubsystemBase {

  private final double percentOutput = 0.75;

  private final Falcon500 climber = new Falcon500(RobotMap.CAN.climber, 1.0);

  private ClimbSubsystem() {
    climber.getMotorController().configFactoryDefault();
    climber.getMotorController().setInverted(true);
  }

  public void toggle(boolean on) {
    climber.setRawOutput(on ? percentOutput : 0.0);
  }

  private static ClimbSubsystem instance = new ClimbSubsystem();

  public static ClimbSubsystem getInstance() {
    return instance;
  }
  
}
