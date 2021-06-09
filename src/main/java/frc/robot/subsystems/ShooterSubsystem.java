package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;

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
  
  private double kP = 0.05; // TODO tune
  private double kI = 0;
  private double kD = 0;
  private double kF = 0.055;

  private ShooterSubsystem() {
    motor.getMotorController().setSensorPhase(true);
    motor.getMotorController().configClosedloopRamp(3.0);
    motor.getMotorController().setNeutralMode(NeutralMode.Coast);

    motor.setPIDF(kP, kI, kD, kF);
    SmartDashboard.putNumber("Shooter PID P", kP);
    SmartDashboard.putNumber("Shooter PID I", kI);
    SmartDashboard.putNumber("Shooter PID D", kD);
    SmartDashboard.putNumber("Shooter PID F", kF);
  }

  public void setFlywheelRevsPerSecond(double rps) {
    SmartDashboard.putNumber("Shooter Goal RPM", rps * 60.0);
    goalRevsPerSecond = rps;
    motor.setRevsPerSecond(rps);
  }

  public void stopFlywheel() {
    setFlywheelRevsPerSecond(0.0);
  }

  public boolean isFlywheelReady() {
    return Math.abs(goalRevsPerSecond - motor.getOutputRevsPerSecond()) < maxRevsPerSecondError;
  }

  @Override
  public void periodic() {
    kP = SmartDashboard.getNumber("Shooter PID P", kP);
    kI = SmartDashboard.getNumber("Shooter PID I", kI);
    kD = SmartDashboard.getNumber("Shooter PID D", kD);
    kF = SmartDashboard.getNumber("Shooter PID F", kF);
    motor.setPIDF(kP, kI, kD, kF);

    double outputRpm = motor.getOutputRevsPerSecond() * 60.0;

    SmartDashboard.putNumber("Shooter Actual RPM",  outputRpm);
    SmartDashboard.putNumber("Shooter Error RPM", Math.abs(outputRpm - (goalRevsPerSecond * 60.0)));
  }

  private static ShooterSubsystem instance;

  public static ShooterSubsystem getInstance() {
    if (instance == null) instance = new ShooterSubsystem();
    return instance;
  }
}
