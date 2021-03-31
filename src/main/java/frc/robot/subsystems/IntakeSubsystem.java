package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class IntakeSubsystem {
    private TalonSRX intakeMotor = new TalonSRX(5);
    private DoubleSolenoid intakePistons = new DoubleSolenoid(0,1);

    public IntakeSubsystem() {
        intakePistons.set(Value.kForward);
    }

    public void toggleIntake(boolean on) {
        if(on) {
            intakeMotor.set(ControlMode.PercentOutput, 0.6);
            intakePistons.set(Value.kReverse);
        } else {
            intakeMotor.set(ControlMode.PercentOutput, 0.0);
            intakePistons.set(Value.kForward);
        }
        
    }
}
