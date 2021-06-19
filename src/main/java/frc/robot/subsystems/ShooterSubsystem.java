package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
// import org.awtybots.frc.botplus.math.Flywheel;

public class ShooterSubsystem extends SubsystemBase {

  private TalonFX motor = new TalonFX(RobotMap.CAN.shooter);
  // private final double maxRevsPerSecondError = 1.0;
  public double goalRevsPerSecond = 0.0;
  // public Flywheel flywheel =
  //     new Flywheel(
  //         0.051, // flywheel radius (m)
  //         motor,
  //         0.9 // efficiency factor
  //         );

  private double kP = 0.1;
  private double kI = 0;
  private double kD = 0;
  private double kF = 0.044;

  // private boolean pidfChanged = false;
  private boolean flywheelReady = true;

  private ShooterSubsystem() {
    motor.setSensorPhase(true);
    motor.configClosedloopRamp(2.0);
    motor.setNeutralMode(NeutralMode.Coast);
    motor.configVoltageCompSaturation(13.0);
    motor.enableVoltageCompensation(true);

    // motor.setPIDF(kP, kI, kD, kF);

    if (Robot.testMode) {
      SmartDashboard.putNumber("Shooter PID - P", kP);
      SmartDashboard.putNumber("Shooter PID - I", kI);
      SmartDashboard.putNumber("Shooter PID - D", kD);
      SmartDashboard.putNumber("Shooter PID - F", kF);
    }
  }

  public void setFlywheelRevsPerSecond(double rps) {
    goalRevsPerSecond = rps;
    if (rps == 0) motor.set(ControlMode.PercentOutput, 0);
    // else motor.setRevsPerSecond(rps); // TODO fix
  }

  public void stopFlywheel() {
    setFlywheelRevsPerSecond(0.0);
  }

  public boolean isFlywheelReady() {
    return flywheelReady;
  }

  @Override
  public void periodic() { // TODO revisit
    // if (Robot.testMode) {
    //   pidfChanged = false;

    //   kP = getNumberFromSmartDashboard("Shooter PID - P", kP);
    //   kI = getNumberFromSmartDashboard("Shooter PID - I", kI);
    //   kD = getNumberFromSmartDashboard("Shooter PID - D", kD);
    //   kF = getNumberFromSmartDashboard("Shooter PID - F", kF);

    //   if (pidfChanged) motor.setPIDF(kP, kI, kD, kF);
    // }

    // double outputRps = motor.getOutputRevsPerSecond();
    // double outputRpm = outputRps * 60.0;
    // flywheelReady = Math.abs(goalRevsPerSecond - outputRps) < maxRevsPerSecondError;

    // SmartDashboard.putNumber("Shooter Actual RPM", outputRpm);
    // SmartDashboard.putNumber("Shooter Error RPM", Math.abs(outputRpm - (goalRevsPerSecond *
    // 60.0)));
    // SmartDashboard.putBoolean("Shooter At Goal", flywheelReady);
  }

  // private double getNumberFromSmartDashboard(String name, double previousValue) {
  //   double newValue = SmartDashboard.getNumber(name, previousValue);
  //   if (newValue != previousValue) {
  //     pidfChanged = true;
  //   }
  //   return newValue;
  // }

  private static ShooterSubsystem instance = new ShooterSubsystem();

  public static ShooterSubsystem getInstance() {
    return instance;
  }
}
