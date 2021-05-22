package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

public class TowerSubsystem extends SubsystemBase {

  private static final double towerPercentOutput = 0.75;

  private final Bag tower = new Bag(RobotMap.CAN.tower, 1.0);

  private TowerSubsystem() {
    tower.getMotorController().configFactoryDefault();

    toggle(false);
  }

  public void toggle(boolean on) {
    tower.setRawOutput(on ? towerPercentOutput : 0.0);
  }

  public void reverse() {
    tower.setRawOutput(-towerPercentOutput);
  }

  private static TowerSubsystem instance;

  public static TowerSubsystem getInstance() {
    if (instance == null) instance = new TowerSubsystem();
    return instance;
  }
}
