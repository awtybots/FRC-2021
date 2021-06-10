package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class SpindexerSubsystem extends SubsystemBase {

  private boolean spindexerCurrentLimiting = true;
  private static final double spindexerPerecentOutput = 0.15;
  private static final double spindexerStuckCurrent = 20.0;
  private static final double spindexerUnstuckReverseTime = 0.5; // seconds
  private static final double spindexerUnstuckPauseTime = 0.3; // seconds

  private final Pro775 spindexer = new Pro775(RobotMap.CAN.spindexer, 1.0);

  private boolean currentlyGettingUnstuck = false;
  private Timer stuckTimer = new Timer();

  private SpindexerSubsystem() {
    spindexer.getMotorController().configFactoryDefault();
    SmartDashboard.putBoolean("Spindexer Current Limiting", spindexerCurrentLimiting);

    toggle(false);
  }

  public void toggle(boolean on) {
    spindexer.setRawOutput(on ? spindexerPerecentOutput : 0.0);
  }

  public void reverse() {
    spindexer.setRawOutput(-spindexerPerecentOutput);
  }

  @Override
  public void periodic() {
    spindexerCurrentLimiting = SmartDashboard.getBoolean("Spindexer Current Limiting", spindexerCurrentLimiting);
    boolean spindexerStuck = (Robot.pdp.getCurrent(RobotMap.PDP.spindexer) > spindexerStuckCurrent * spindexerPerecentOutput) && spindexerCurrentLimiting;

    SmartDashboard.putBoolean("Spindexer Not Stuck", !spindexerStuck);

    if(currentlyGettingUnstuck) {
      if(stuckTimer.get() > spindexerUnstuckReverseTime) {
        toggle(false);
        if(stuckTimer.get() > spindexerUnstuckReverseTime + spindexerUnstuckPauseTime) {
          toggle(true);
          currentlyGettingUnstuck = false;
        }
      }
    } else if(spindexerStuck) {
      currentlyGettingUnstuck = true;
      stuckTimer.reset();
      stuckTimer.start();
      reverse();
    }
  }

  private static SpindexerSubsystem instance;

  public static SpindexerSubsystem getInstance() {
    if (instance == null) instance = new SpindexerSubsystem();
    return instance;
  }
}
