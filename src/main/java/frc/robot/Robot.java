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
import util.controls.Controller;
import util.vision.Limelight;

public class Robot extends TimedRobot {

  public static final boolean testMode = false;
  private SendableChooser<Command> autonChooser = new SendableChooser<Command>();
  private Command autonCommand;

  private Compressor compressor = new Compressor();
  public static PowerDistributionPanel PDP = new PowerDistributionPanel();
  public static Limelight limelight =
      new Limelight(Dimensions.limelightMountingHeight, Dimensions.limelightMountingAngle);

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
    controller1.trgR.whenActive(new ToggleIntake());
    controller1.btnA.whenHeld(new ToggleClimber(ToggleClimber.Forward));
    controller1.btnBack.whenHeld(new ToggleClimber(ToggleClimber.Reverse));

    // ---- Second Controller ---- //
    /// -- Manual Shots -- ///
    controller2.btnA.whenHeld(new ManualShootingPreset(4000.0, 76, false)); // against wall
    controller2.btnX.whenHeld(new ManualShootingPreset(5000.0, 58, true)); // mid range?
    controller2.btnB.whenHeld(new ManualShootingPreset(5700.0, 57, true)); // long range
    /// -- Toggles and Reverse -- ///
    controller2.bmpL.whenHeld(new ReverseTower());
    controller2.bmpR.whenHeld(new ToggleTower());
    controller2.trgL.whenActive(new ReverseIndexer());
    controller2.trgR.whenActive(new ToggleIndexer());
  }

  @Override
  public void teleopInit() {
    bindIO();
    Robot.limelight.toggleDriverMode(true);
  }

  @Override
  public void disabledInit() {
    Robot.limelight.toggleDriverMode(true);
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
