package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeSubsystem extends SubsystemBase {

  private final double motorPercentOutput = 0.6;

  private final TalonSRX motor = new TalonSRX(RobotMap.CAN.intake);
  private final DoubleSolenoid pistons =
      new DoubleSolenoid(RobotMap.PCM.intakeFwd, RobotMap.PCM.intakeRev);

  private IntakeSubsystem() {
    motor.configFactoryDefault();
    toggle(false);
  }

  public void toggle(boolean on) {
    pistons.set(on ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    motor.set(ControlMode.PercentOutput, on ? motorPercentOutput : 0);
  }

  private static IntakeSubsystem instance = new IntakeSubsystem();

  public static IntakeSubsystem getInstance() {
    return instance;
  }
}
