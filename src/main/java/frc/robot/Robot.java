package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.TeleopDrive;
import frc.robot.commands.ToggleIntake;
import frc.robot.commands.ToggleSpindexer;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

  public static DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  public static XboxController controller1 = new XboxController(0);
  public static SpindexerSubsystem spindexerSubsystem = new SpindexerSubsystem();

  @Override
  public void robotInit() {}

  /**
   * This runs after the mode specific periodic functions, but before LiveWindow and SmartDashboard
   * integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    new TeleopDrive().schedule();

    JoystickButton buttonA = new JoystickButton(controller1, Button.kA.value);
    JoystickButton buttonB = new JoystickButton(controller1, Button.kB.value);

    buttonA.whenHeld(new ToggleIntake());
    buttonB.whenHeld(new ToggleSpindexer());
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
