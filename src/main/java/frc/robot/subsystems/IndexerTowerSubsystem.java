package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

public class IndexerTowerSubsystem extends SubsystemBase {

  private static final double indexerLPercentOutput = 0.8;
  private static final double indexerRPercentOutput = 0.6;
  private static final double towerPercentOutput = 0.75;

  private final Bag indexerL = new Bag(RobotMap.CAN.indexerL, 1.0);
  private final Bag indexerR = new Bag(RobotMap.CAN.indexerR, 1.0);
  private final Bag tower1 = new Bag(RobotMap.CAN.tower1, 1.0);
  private final Bag tower2 = new Bag(RobotMap.CAN.tower2, 1.0);

  private IndexerTowerSubsystem() {
    indexerL.getMotorController().setInverted(false);
    indexerR.getMotorController().setInverted(true);
    tower1.getMotorController().setInverted(true);
    tower2.getMotorController().setInverted(true);

    toggle(false);
  }

  public void toggle(boolean on) {
    indexerL.setRawOutput(on ? indexerLPercentOutput : 0.0);
    indexerR.setRawOutput(on ? indexerRPercentOutput : 0.0);
    tower1.setRawOutput(on ? towerPercentOutput : 0.0);
    tower2.setRawOutput(on ? towerPercentOutput : 0.0);
  }

  public void reverse() {
    tower1.setRawOutput(-towerPercentOutput);
    tower2.setRawOutput(-towerPercentOutput);
  }

  private static IndexerTowerSubsystem instance;

  public static IndexerTowerSubsystem getInstance() {
    if (instance == null) instance = new IndexerTowerSubsystem();
    return instance;
  }
}
