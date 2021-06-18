package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DrivetrainSubsystem extends SubsystemBase {

  private static final double rampTime = 0.3;

  private final TalonFX[] leftMotors =
      new TalonFX[] {
        new TalonFX(RobotMap.CAN.leftDrive1), new TalonFX(RobotMap.CAN.leftDrive2),
      };
  private final TalonFX[] rightMotors =
      new TalonFX[] {
        new TalonFX(RobotMap.CAN.rightDrive1), new TalonFX(RobotMap.CAN.rightDrive2),
      };

  public DrivetrainSubsystem() {
    for (TalonFX driveMotor : leftMotors) applyMotorSettings(driveMotor);
    for (TalonFX driveMotor : rightMotors) {
      applyMotorSettings(driveMotor);
      driveMotor.setInverted(true);
    }
  }

  private void applyMotorSettings(TalonFX motor) {
    motor.configFactoryDefault();
    motor.setNeutralMode(NeutralMode.Brake);
    motor.configOpenloopRamp(rampTime);
    motor.configVoltageCompSaturation(12.5);
    motor.enableVoltageCompensation(true);
  }

  public void setPercentOutput(double leftSpeed, double rightSpeed) {
    leftMotors[0].set(ControlMode.PercentOutput, leftSpeed);
    leftMotors[1].set(ControlMode.PercentOutput, leftSpeed);
    rightMotors[0].set(ControlMode.PercentOutput, rightSpeed);
    rightMotors[1].set(ControlMode.PercentOutput, rightSpeed);
  }

  public void kill() {
    leftMotors[0].set(ControlMode.PercentOutput, 0);
    leftMotors[1].set(ControlMode.PercentOutput, 0);
    rightMotors[0].set(ControlMode.PercentOutput, 0);
    rightMotors[1].set(ControlMode.PercentOutput, 0);
  }

  private static DrivetrainSubsystem instance = new DrivetrainSubsystem();

  public static DrivetrainSubsystem getInstance() {
    return instance;
  }
}
