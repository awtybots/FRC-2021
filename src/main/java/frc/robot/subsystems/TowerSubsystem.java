package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class TowerSubsystem extends SubsystemBase {

  private static final double loadingPercentOutput = 0.25; // slow while loading balls
  private static final double shootingPercentOutput = 0.65; // fast while shooting
  private static final double reversePercentOutput = -0.3;

  private final Pro775 tower = new Pro775(RobotMap.CAN.tower, 1.0);
  private final DigitalInput limitSwitch = new DigitalInput(RobotMap.DIO.towerLimitSwitch);

  private boolean
      stopIfLimitSwitchPressed; // this is not a setting, this is just a flag for internal use

  private TowerSubsystem() {
    tower.getMotorController().configFactoryDefault();
    tower.getMotorController().setInverted(true);

    stop();
  }

  public void enableForLoading() {
    if (isFull()) return;

    tower.setRawOutput(loadingPercentOutput);
    stopIfLimitSwitchPressed = true;
  }

  public void enableForShooting() {
    tower.setRawOutput(shootingPercentOutput);
    stopIfLimitSwitchPressed = false;
  }

  public void reverse() {
    tower.setRawOutput(reversePercentOutput);
    stopIfLimitSwitchPressed = false;
  }

  public void stop() {
    tower.setRawOutput(0.0);
    stopIfLimitSwitchPressed = false;
  }

  /** Determine if a ball pressing the limit switch, indicating if the tower is full. */
  private boolean isFull() {
    return limitSwitch.get();
  }

  @Override
  public void periodic() {
    if (stopIfLimitSwitchPressed && isFull()) {
      stop();
    }
  }

  private static TowerSubsystem instance = new TowerSubsystem();

  public static TowerSubsystem getInstance() {
    return instance;
  }
}
