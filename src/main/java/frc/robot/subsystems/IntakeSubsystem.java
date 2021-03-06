package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import org.awtybots.frc.botplus.motors.Pro775;

public class IntakeSubsystem extends SubsystemBase {

  // private final double motorPercentOutput = 0.6;

  // private final Pro775 motor = new Pro775(RobotMap.CAN.intake, 1.0);
  // private final DoubleSolenoid pistons =
  //     new DoubleSolenoid(RobotMap.PCM.intakeFwd, RobotMap.PCM.intakeRev);

  // private IntakeSubsystem() {
  //   motor.getMotorController().configFactoryDefault();

  //   toggle(false);
  // }

  // public void toggle(boolean on) {
  //   pistons.set(on ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
  //   toggleMotorOnly(on);
  // }

  // public void toggleMotorOnly(boolean on) {
  //   motor.getMotorController().set(ControlMode.PercentOutput, on ? motorPercentOutput : 0);
  // }

  // private static IntakeSubsystem instance;

  // public static IntakeSubsystem getInstance() {
  //   if (instance == null) instance = new IntakeSubsystem();
  //   return instance;
  // }
}
