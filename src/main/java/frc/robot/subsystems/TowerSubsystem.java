package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class TowerSubsystem extends SubsystemBase {

  private static final double loadingPercentOutput = 0.25; // slow while loading balls
  private static final double shootingPercentOutput = 0.65; // fast while shooting
  private static final double reversePercentOutput = -0.3;

  private final TalonSRX tower = new TalonSRX(RobotMap.CAN.tower);
  private final DigitalInput limitSwitch = new DigitalInput(RobotMap.DIO.towerLimitSwitch);

  private boolean
      stopIfLimitSwitchPressed; // this is not a setting, this is just a flag for internal use

  private TowerSubsystem() {
    tower.configFactoryDefault();
    tower.setInverted(false);

    stop();
  }

  public void enableForLoading() {
    if (isFull()) return;

    tower.set(ControlMode.PercentOutput, loadingPercentOutput);
    stopIfLimitSwitchPressed = true;
  }

  public void enableForShooting() {
    tower.set(ControlMode.PercentOutput, shootingPercentOutput);
    stopIfLimitSwitchPressed = false;
  }

  public void reverse() {
    tower.set(ControlMode.PercentOutput, reversePercentOutput);
    stopIfLimitSwitchPressed = false;
  }

  public void stop() {
    tower.set(ControlMode.PercentOutput, 0.0);
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
