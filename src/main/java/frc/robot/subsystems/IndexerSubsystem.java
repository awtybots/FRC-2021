package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

public class IndexerSubsystem extends SubsystemBase {

  // running them at different speeds worked best last year
  public static final double indexerLPercentOutput = 0.8;
  public static final double indexerRPercentOutput = 0.6;

  private Bag indexerL;
  private Bag indexerR;

  private IndexerSubsystem() {
    indexerL = new Bag(RobotMap.CAN.indexerL, 1.0);
    indexerR = new Bag(RobotMap.CAN.indexerR, 1.0);

    indexerL.getMotorController().configFactoryDefault();
    indexerR.getMotorController().configFactoryDefault();

    indexerR.getMotorController().setInverted(true); // invert one of them

    set(0);
  }

  public void set(int p) {
    indexerL.setRawOutput(indexerLPercentOutput * p);
    indexerR.setRawOutput(indexerRPercentOutput * p);
  }

  private static IndexerSubsystem instance = new IndexerSubsystem();

  public static IndexerSubsystem getInstance() {
    return instance;
  }
}
