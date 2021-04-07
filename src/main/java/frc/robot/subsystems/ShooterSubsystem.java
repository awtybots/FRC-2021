package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.math.Flywheel;
import org.awtybots.frc.botplus.motors.Falcon500;

public class ShooterSubsystem extends SubsystemBase {

  private Falcon500 motor = new Falcon500(RobotMap.CAN.shooter, 1.0);
  private final double maxRevsPerSecondError = 50.0;
  public double goalRevsPerSecond = 0.0;
  public Flywheel flywheel =
      new Flywheel(
          0.051, // flywheel radius (m)
          motor,
          0.90 // efficiency factor
          );

  private ShooterSubsystem() {
    motor.setPIDF(0.0, 0.0, 0.0, 0.0);
  }

  public void setFlywheelRevsPerSecond(double rps) {
    goalRevsPerSecond = rps;
    motor.setRevsPerSecond(rps);
  }

  public void stopFlywheel() {
    setFlywheelRevsPerSecond(0.0);
  }

  public boolean isFlywheelReady() {
    return Math.abs(goalRevsPerSecond - motor.getOutputRevsPerSecond()) < maxRevsPerSecondError;
  }

  private static ShooterSubsystem instance;

  public static ShooterSubsystem getInstance() {
    if (instance == null) instance = new ShooterSubsystem();
    return instance;
  }
}
