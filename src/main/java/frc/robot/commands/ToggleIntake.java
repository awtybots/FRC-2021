package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleIntake extends CommandBase{
    @Override
    public void initialize() {
        Robot.IntakeSubsystem.toggleIntake(true);
    }
    @Override
    public void end(boolean interrupted) {
        Robot.IntakeSubsystem.toggleIntake(false);
    }
}
