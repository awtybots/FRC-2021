package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Bag;

public class TowerSubsystem extends SubsystemBase {

  private boolean currentLimiting = false;

  private static final double percentOutput = 0.65;
  private static final double reversePercentOutput = -0.3;

  private static final double stuckCurrent = 65;
  private static final double unstuckReverseTime = 0.3;
  private static final double unstuckPauseTime = 0.5;

  private boolean currentlyGettingUnstuck = false;
  private Timer stuckTimer = new Timer();

  private final Bag tower = new Bag(RobotMap.CAN.tower, 1.0);

  private int desiredState = 0;

  private TowerSubsystem() {
    tower.getMotorController().configFactoryDefault();
    SmartDashboard.putBoolean("Tower Current Limiting", currentLimiting);
    tower.getMotorController().setInverted(true);

    toggle(false);
    set(0);
  }

  public void toggle(boolean on) {
    desiredState = on ? 1 : 0;
  }

  private void set(int p) {
    switch(p) {
      case -1:
        tower.setRawOutput(reversePercentOutput);
        break;
      case 0:
        tower.setRawOutput(0.0);
        break;
      case 1:
        tower.setRawOutput(percentOutput);
        break;
    }
  }

  public void reverse() {
    desiredState = -1;
  }

  @Override
  public void periodic() {
    currentLimiting = SmartDashboard.getBoolean("Tower Current Limiting", currentLimiting);
    boolean towerStuck = Robot.pdp.getCurrent(RobotMap.PDP.tower) > stuckCurrent * percentOutput;

    SmartDashboard.putBoolean("Tower Not Stuck", (!towerStuck) && (!currentlyGettingUnstuck));

    towerStuck = desiredState == 1 && towerStuck && currentLimiting;

    if(currentlyGettingUnstuck) {
      if(stuckTimer.get() > unstuckReverseTime) {
        set(0);
        if(stuckTimer.get() > unstuckReverseTime + unstuckPauseTime) {
          set(desiredState);
          currentlyGettingUnstuck = false;
        }
      }
    } else if(towerStuck) {
      currentlyGettingUnstuck = true;
      stuckTimer.reset();
      stuckTimer.start();

      set(-desiredState);

      SpindexerSubsystem.getInstance().unstuck();
    // } else if(SpindexerSubsystem.getInstance().currentlyGettingUnstuck) {
    //   set(0);
    } else {
      set(desiredState);
    }
  }

  private static TowerSubsystem instance = new TowerSubsystem();

  public static TowerSubsystem getInstance() {
    return instance;
  }
}
