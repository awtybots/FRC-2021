package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class SpindexerSubsystem extends SubsystemBase {

  private static final double spindexerPerecentOutput = 0.5;

  private final Pro775 spindexer = new Pro775(RobotMap.CAN.spindexer, 1.0);

  private SpindexerSubsystem() {
    spindexer.getMotorController().configFactoryDefault();

    toggle(false);
  }

  public void toggle(boolean on) {
    spindexer.setRawOutput(on ? spindexerPerecentOutput : 0.0);
  }

  public void reverse() {
    spindexer.setRawOutput(-spindexerPerecentOutput);
  }

  private static SpindexerSubsystem instance;

  public static SpindexerSubsystem getInstance() {
    if (instance == null) instance = new SpindexerSubsystem();
    return instance;
  }
}
