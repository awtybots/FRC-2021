package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

/** just in case we need it */
public class IndexerTowerSubsystem extends SubsystemBase {

  public static final boolean exists = false;

  public static final double indexerLPercentOutput = 0.8;
  public static final double indexerRPercentOutput = 0.6;
  public static final double towerPercentOutput = 0.75;

  private Bag indexerL;
  private Bag indexerR;
  private Bag tower;

  private IndexerTowerSubsystem() {
    if (exists) {
      indexerL = new Bag(RobotMap.CAN.indexerL, 1.0);
      indexerR = new Bag(RobotMap.CAN.indexerR, 1.0);
      tower = new Bag(RobotMap.CAN.tower, 1.0);

      indexerL.getMotorController().configFactoryDefault();
      indexerR.getMotorController().configFactoryDefault();
      tower.getMotorController().configFactoryDefault();

      indexerL.getMotorController().setInverted(false);
      indexerR.getMotorController().setInverted(true);
      tower.getMotorController().setInverted(true);

      toggle(false);
    }
  }

  public void toggle(boolean on) {
    if (exists) {
      indexerL.setRawOutput(on ? indexerLPercentOutput : 0.0);
      indexerR.setRawOutput(on ? indexerRPercentOutput : 0.0);
      tower.setRawOutput(on ? towerPercentOutput : 0.0);
    }
  }

  public void reverse() {
    if (exists) {
      tower.setRawOutput(-towerPercentOutput);
    }
  }

  private static IndexerTowerSubsystem instance = new IndexerTowerSubsystem();

  public static IndexerTowerSubsystem getInstance() {
    return instance;
  }
}
