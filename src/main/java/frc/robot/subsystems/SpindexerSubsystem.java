package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class SpindexerSubsystem extends SubsystemBase {

  private boolean currentLimiting = true;

  private final Pro775 spindexer = new Pro775(RobotMap.CAN.spindexer, 1.0);

  private static final double percentOutput = 0.2;
  private static final double stuckCurrent = 20.0;
  private static final double unstuckReverseTime = 0.7; // seconds
  private static final double unstuckPauseTime = 0.3; // seconds

  private boolean currentlyGettingUnstuck = false;
  private Timer stuckTimer = new Timer();

  private boolean toggled = false;

  private SpindexerSubsystem() {
    spindexer.getMotorController().configFactoryDefault();
    SmartDashboard.putBoolean("Spindexer Current Limiting", currentLimiting);

    spindexer.getMotorController().setInverted(true); // TODO temp

    toggle(false);
    set(false);
  }

  public void toggle(boolean on) {
    toggled = on;
  }

  public void set(boolean on) {
    spindexer.setRawOutput(on ? percentOutput : 0.0);
  }

  public void reverse() {
    spindexer.setRawOutput(-percentOutput);
  }

  @Override
  public void periodic() {
    currentLimiting = SmartDashboard.getBoolean("Spindexer Current Limiting", currentLimiting);
    boolean spindexerStuck = (Robot.pdp.getCurrent(RobotMap.PDP.spindexer) > stuckCurrent * percentOutput) && currentLimiting;

    SmartDashboard.putBoolean("Spindexer Not Stuck", (!spindexerStuck) && (!currentlyGettingUnstuck));

    if(currentlyGettingUnstuck) {
      if(stuckTimer.get() > unstuckReverseTime) {
        set(false);
        if(stuckTimer.get() > unstuckReverseTime + unstuckPauseTime) {
          set(toggled);
          currentlyGettingUnstuck = false;
        }
      }
    } else if(spindexerStuck) {
      unstuck();
    } else {
      set(toggled);
    }
  }

  public void unstuck() {
    currentlyGettingUnstuck = true;
    stuckTimer.reset();
    stuckTimer.start();
    reverse();
  }

  private static SpindexerSubsystem instance;

  public static SpindexerSubsystem getInstance() {
    if (instance == null) instance = new SpindexerSubsystem();
    return instance;
  }
}
