package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class TowerSubsystem extends SubsystemBase {

  private static final double percentOutput = 0.65; // might want to go slower if using limit switch
  private static final double reversePercentOutput = -0.3;

  private final Pro775 tower = new Pro775(RobotMap.CAN.tower, 1.0);
  private final DigitalInput limitSwitch = new DigitalInput(RobotMap.DIO.towerLimitSwitch);

  private TowerSubsystem() {
    tower.getMotorController().configFactoryDefault();
    tower.getMotorController().setInverted(true);

    set(0);
  }

  public void set(int p) {
    switch (p) {
      case -1:
        tower.setRawOutput(reversePercentOutput);
        break;
      case 0:
        tower.setRawOutput(0.0);
        break;
      case 1:
        tower.setRawOutput(percentOutput);
        break;
    }
  }

  public boolean isFull() {
    return limitSwitch.get();
  }

  private static TowerSubsystem instance = new TowerSubsystem();

  public static TowerSubsystem getInstance() {
    return instance;
  }
}
