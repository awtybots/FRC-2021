package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class SpindexerSubsystem {
    private TalonSRX spindexerMotor = new TalonSRX(5);

    public void toggleSpindexer(boolean on) {
        if(on) {
            spindexerMotor.set(ControlMode.PercentOutput, 0.6);
        } else {
            spindexerMotor.set(ControlMode.PercentOutput, 0.0);
        }
        
    }
}