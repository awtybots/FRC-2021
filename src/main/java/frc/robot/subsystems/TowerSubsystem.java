package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

public class TowerSubsystem extends SubsystemBase {

  private boolean currentLimiting = false;

  private static final double percentOutput = 0.85;
  private static final double reversePercentOutput = -0.3;

  private static final double stuckCurrent = 65;
  private static final double unstuckReverseTime = 0.3;
  private static final double unstuckPauseTime = 0.5;

  private boolean currentlyGettingUnstuck = false;
  private Timer stuckTimer = new Timer();

  private final Bag tower = new Bag(RobotMap.CAN.tower, 1.0);

  private boolean toggled = false;

  private TowerSubsystem() {
    tower.getMotorController().configFactoryDefault();
    SmartDashboard.putBoolean("Tower Current Limiting", currentLimiting);

    toggle(false);
    set(false);
  }

  public void toggle(boolean on) {
    toggled = on;
  }

  private void set(boolean on) {
    tower.setRawOutput(on ? percentOutput : 0.0);
  }

  private void reverse() {
    tower.setRawOutput(reversePercentOutput);
  }

  @Override
  public void periodic() {
    currentLimiting = SmartDashboard.getBoolean("Tower Current Limiting", currentLimiting);
    boolean towerStuck = (Robot.pdp.getCurrent(RobotMap.PDP.tower) > stuckCurrent * percentOutput) && currentLimiting;

    SmartDashboard.putBoolean("Tower Not Stuck", (!towerStuck) && (!currentlyGettingUnstuck));

    if(currentlyGettingUnstuck) {
      if(stuckTimer.get() > unstuckReverseTime) {
        set(false);
        if(stuckTimer.get() > unstuckReverseTime + unstuckPauseTime) {
          set(toggled);
          SpindexerSubsystem.getInstance().toggle(true);
          currentlyGettingUnstuck = false;
        }
      }
    } else if(towerStuck) {
      currentlyGettingUnstuck = true;
      stuckTimer.reset();
      stuckTimer.start();

      SpindexerSubsystem.getInstance().toggle(false);
      reverse();
    } else {
      set(toggled);
    }
  }

  private static TowerSubsystem instance;

  public static TowerSubsystem getInstance() {
    if (instance == null) instance = new TowerSubsystem();
    return instance;
  }
}
