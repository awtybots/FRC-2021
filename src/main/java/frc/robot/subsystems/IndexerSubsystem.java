package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IndexerSubsystem extends SubsystemBase {

  public static final double leftSpeed = 0.8;
  public static final double rightSpeed = 0.6;

  private TalonSRX indexerL;
  private TalonSRX indexerR;

  private IndexerSubsystem() {
    indexerL = new TalonSRX(RobotMap.CAN.indexerL);
    indexerR = new TalonSRX(RobotMap.CAN.indexerR);

    indexerL.configFactoryDefault();
    indexerR.configFactoryDefault();

    indexerR.setInverted(true); // invert one of them

    set(0);
  }

  public void set(int percent) {
    indexerL.set(ControlMode.PercentOutput, leftSpeed * percent);
    indexerR.set(ControlMode.PercentOutput, rightSpeed * percent);
  }

  private static IndexerSubsystem instance = new IndexerSubsystem();

  public static IndexerSubsystem getInstance() {
    return instance;
  }
}
