package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class SpindexerSubsystem extends SubsystemBase {

  private final boolean exists = true;

  private boolean currentLimiting = true;

  private final Pro775 spindexer;

  private static final double percentOutput = 0.15;
  private static final double stuckCurrent = 20.0;
  private static final double stuckTime = 0.75; // seconds
  private static final double unstuckReverseTime = 0.7; // seconds
  private static final double unstuckPauseTime = 0.3; // seconds

  public boolean currentlyGettingUnstuck = false;
  private Timer stuckTimer = new Timer();

  private int desiredState = 0;

  private SpindexerSubsystem() {
    if(exists) {
      spindexer = new Pro775(RobotMap.CAN.spindexer, 1.0);
      spindexer.getMotorController().configFactoryDefault();
      SmartDashboard.putBoolean("Spindexer Current Limiting", currentLimiting);

      // spindexer.getMotorController().setInverted(true);

      stuckTimer.start();

      toggle(false);
      set(0);
    }
  }

  public void toggle(boolean on) {
    if(exists) desiredState = on ? 1 : 0;
  }

  public void set(int p) {
    if(exists) spindexer.setRawOutput(p * percentOutput);
  }

  public void reverse() {
    if(exists) desiredState = -1;
  }

  @Override
  public void periodic() {
    if(!exists) return;

    currentLimiting = SmartDashboard.getBoolean("Spindexer Current Limiting", currentLimiting);
    boolean spindexerStuck = Robot.pdp.getCurrent(RobotMap.PDP.spindexer) > stuckCurrent * percentOutput;

    SmartDashboard.putBoolean("Spindexer Not Stuck", (!spindexerStuck) && (!currentlyGettingUnstuck));

    spindexerStuck = spindexerStuck && currentLimiting;

    if(currentlyGettingUnstuck) {
      if(stuckTimer.get() > unstuckReverseTime) {
        set(0);
        if(stuckTimer.get() > unstuckReverseTime + unstuckPauseTime) {
          set(desiredState);
          currentlyGettingUnstuck = false;
        }
      }
    } else if(spindexerStuck) {
      if(stuckTimer.get() > stuckTime) unstuck();
    } else {
      stuckTimer.reset();
      set(desiredState);
    }
  }

  public void unstuck() {
    if(!exists) return;

    currentlyGettingUnstuck = true;
    stuckTimer.reset();
    set(-desiredState);
  }

  private static SpindexerSubsystem instance = new SpindexerSubsystem();

  public static SpindexerSubsystem getInstance() {
    return instance;
  }
}
