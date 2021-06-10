package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

public class TowerSubsystem extends SubsystemBase {

  private static final double towerPercentOutput = 0.75;

  private static final double servoAngleIdle = 90;
  private static final double servoAngleActive = 75;

  private final Bag tower = new Bag(RobotMap.CAN.tower, 1.0);
  private final Servo servo = new Servo(RobotMap.PWM.towerServo);

  private TowerSubsystem() {
    tower.getMotorController().configFactoryDefault();

    toggle(false);
  }

  public void toggle(boolean on) {
    tower.setRawOutput(on ? towerPercentOutput : 0.0);
    servo.setAngle(on ? servoAngleActive : servoAngleIdle);
  }

  public void reverse() {
    tower.setRawOutput(-towerPercentOutput);
    servo.setAngle(servoAngleIdle);
  }

  private static TowerSubsystem instance;

  public static TowerSubsystem getInstance() {
    if (instance == null) instance = new TowerSubsystem();
    return instance;
  }
}
