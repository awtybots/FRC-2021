package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.automatic.ManualShootingPreset;
import frc.robot.commands.auton.sequences.*;
import frc.robot.commands.teleop.*;
import org.awtybots.frc.botplus.commands.Controller;
import org.awtybots.frc.botplus.sensors.vision.Limelight;

public class Robot extends TimedRobot {

  public static final boolean testMode = false;
  private SendableChooser<Command> autonChooser = new SendableChooser<Command>();
  private Command autonCommand;

  private Compressor compressor = new Compressor();
  public static PowerDistributionPanel pdp = new PowerDistributionPanel();
  public static Limelight limelight =
      new Limelight(
          RobotMap.Dimensions.limelightMountingHeight, RobotMap.Dimensions.limelightMountingAngle);

  @Override
  public void robotInit() {
    addAutonOptions();
    autonCommand = autonChooser.getSelected();
    if (autonCommand != null) autonCommand.schedule();
    else System.err.println("No Default Autonomous Set");
    compressor.setClosedLoopControl(true);
  }

  public void addAutonOptions() {
    addAutonDefault("Drive Forward Time and Shoot 3", new DriveForwardTimeAndShoot3(0.75));
    addAutonOption("Shoot 3 and Drive Forward Time", new Shoot3AndDriveForwardTime(0.75));
    addAutonOption("Shoot 3 from the Line", new Shoot3FromLine());
    addAutonOption("Drive Forward Time", new DriveForwardTime(1.5));
  }

  public void bindIO() {
    Controller controller1 = new Controller(0);
    Controller controller2 = new Controller(1);

    // ---- First Controller ---- //
    controller1.streamAnalogInputTo(new TeleopDrive());
    controller1.getTrgR().whenHeld(new ToggleIntake());
    controller1.getBtnA().whenHeld(new ToggleClimber(ToggleClimber.Forward));
    controller1.getBtnBack().whenHeld(new ToggleClimber(ToggleClimber.Reverse));
    // controller1.getTrgL().whenHeld(new ToggleIntakeMotorOnly()); // TODO why?

    // ---- Second Controller ---- //
    /// -- Manual Shots -- ///
    controller2.getBtnA().whenHeld(new ManualShootingPreset(4000.0, 76, false)); // against wall
    controller2.getBtnX().whenHeld(new ManualShootingPreset(5000.0, 58, true)); // mid range?
    controller2.getBtnB().whenHeld(new ManualShootingPreset(5700.0, 57, true)); // long range
    /// -- Toggles and Reverse -- ///
    controller2.getBmpL().whenHeld(new ReverseTower());
    controller2.getBmpR().whenHeld(new ToggleTower());
    controller2.getTrgL().whenHeld(new ReverseIndexer());
    controller2.getTrgR().whenHeld(new ToggleIndexer());
  }

  @Override
  public void teleopInit() {
    bindIO();
    Robot.limelight.setDriverMode(true);
  }

  @Override
  public void disabledInit() {
    Robot.limelight.setDriverMode(true);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  private void addAutonDefault(String name, Command command) {
    autonChooser.setDefaultOption(name, command);
  }

  private void addAutonOption(String name, Command command) {
    autonChooser.addOption(name, command);
  }
}
