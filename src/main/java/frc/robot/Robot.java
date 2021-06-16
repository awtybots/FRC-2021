package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auton.sequences.*;
import frc.robot.commands.teleop.*;
import frc.robot.commands.teleop.automatic.*;
import frc.robot.subsystems.*;
import org.awtybots.frc.botplus.CompetitionBot;
import org.awtybots.frc.botplus.commands.Controller;
import org.awtybots.frc.botplus.sensors.vision.Limelight;

public class Robot extends CompetitionBot {

  public static Limelight limelight =
      new Limelight(
          RobotMap.Dimensions.limelightMountingHeight, RobotMap.Dimensions.limelightMountingAngle);
  public static PowerDistributionPanel pdp = new PowerDistributionPanel();

  private Compressor compressor = new Compressor();

  @Override
  public void robotInit() {
    super.robotInit();
    compressor.setClosedLoopControl(true);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public boolean isTestMode() {
    return false;
  }

  @Override
  public void addAutonOptions() {
    addAutonDefault("Drive Forward Time and Shoot 3", new DriveForwardTimeAndShoot3(0.75));
    // addAutonOption("Shoot 3 and Drive Forward Time", new Shoot3AndDriveForwardTime(1.5));
    // addAutonOption("Shoot 3 from the Line", new Shoot3FromLine());
    // addAutonOption("Drive Forward Time", new DriveForwardTime(1.5));
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
    Robot.limelight.setDriverMode(true);
  }

  @Override
  public void teleopPeriodic() {
    super.teleopPeriodic();
  }

  @Override
  public void disabledInit() {
    super.disabledInit();
    Robot.limelight.setDriverMode(true);
  }

  @Override
  public void bindIO() {
    Controller controller1 = new Controller(0);
    Controller controller2 = new Controller(1);

    controller1.streamAnalogInputTo(new TeleopDrive());
    controller1.getTrgL().whenHeld(new ToggleIntakeMotorOnly());
    controller1.getTrgR().whenHeld(new ToggleIntake());
    controller1.getBtnA().whenHeld(new ToggleClimber(ToggleClimber.Forward));
    controller1.getBtnBack().whenHeld(new ToggleClimber(ToggleClimber.Reverse));

    // controller1.getBtnX().whenHeld(new ManualHoodRun(true));
    // controller1.getBtnB().whenHeld(new ManualHoodRun(false));

    controller2.getBtnA().whenHeld(new ManualShootingPreset(4000.0, 76, false)); // against wall
    controller2.getBtnX().whenHeld(new ManualShootingPreset(5000.0, 58, true)); // mid range?
    controller2.getBtnB().whenHeld(new ManualShootingPreset(5700.0, 57, true)); // long range
    // controller2
    //     .getBtnY()
    //     .whenHeld(
    //         new AutoAimUsingTurret()
    //             .alongWith(new AutoShoot())); // fancy pants shot w/limelight, turret, physics
    // math

    controller2.getBtnBack().whenPressed(new ResetHoodDumb());
    //         new ResetTurret() // hood all the way back, turret dead center
    //             .alongWith(new SetHoodLaunchAngle(AdjustableHoodSubsystem.maxLaunchAngle)));
    // controller2.getBtnStart().whenHeld(new AutoAimUsingTurret());
    // controller2.getDpadLeft().whenPressed( // manually move turret left 10 degrees
    //   new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(-10),
    //     TurretSubsystem.getInstance()
    //   ));
    // controller2.getDpadRight().whenPressed( // manually move turret right 10 degrees
    //     new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(10),
    //     TurretSubsystem.getInstance()
    //   ));
    controller2.getBmpL().whenHeld(new ReverseTower());
    controller2.getBmpR().whenHeld(new ToggleTower());
    controller2.getTrgL().whenHeld(new ReverseIndexer());
    controller2.getTrgR().whenHeld(new ToggleIndexer());
  }
}
