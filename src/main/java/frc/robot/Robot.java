package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotMap.LimelightPipelines;
import frc.robot.commands.auton.sequences.*;
import frc.robot.commands.teleop.*;
import frc.robot.subsystems.*;

import org.awtybots.frc.botplus.CompetitionBot;
import org.awtybots.frc.botplus.commands.Controller;
import org.awtybots.frc.botplus.sensors.vision.Limelight;

public class Robot extends CompetitionBot {

  public static Limelight limelight = new Limelight(RobotMap.Dimensions.limelightMountingHeight, RobotMap.Dimensions.limelightMountingAngle);
  public static PowerDistributionPanel pdp = new PowerDistributionPanel();

  private DigitalOutput ledOutput = new DigitalOutput(0);
  private Compressor compressor = new Compressor();

  @Override
  public void robotInit() {
    super.robotInit();

    // forcibly instantiates all of them
    AdjustableHoodSubsystem.getInstance();
    DrivetrainSubsystem.getInstance();
    IntakeSubsystem.getInstance();
    ShooterSubsystem.getInstance();
    SpindexerSubsystem.getInstance();
    TowerSubsystem.getInstance();
    TurretSubsystem.getInstance();

    compressor.setClosedLoopControl(true);
    new Thread(() -> {
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Robot.limelight.setPipeline(LimelightPipelines.idle);
    }).start();
  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
    ledOutput.set(DriverStation.getInstance().getAlliance() == Alliance.Red);
  }

  @Override
  public void setTestMode() {
    Companion.setTestMode(true); // TODO make it false, this is for tuning and practice only
  }

  @Override
  public void addAutonOptions() {
    addAutonDefault("Shoot 3 and Reverse", new Shoot3AndReverse());
  }

  @Override
  public void teleopInit() {
    super.teleopInit();

    SpindexerSubsystem.getInstance().toggle(false); // TODO change to true
    Robot.limelight.setPipeline(LimelightPipelines.idle);
  }

  @Override
  public void teleopPeriodic() {
    super.teleopPeriodic();
  }

  @Override
  public void disabledInit() {
    super.disabledInit();

    Robot.limelight.setPipeline(LimelightPipelines.idle);
  }

  @Override
  public void bindIO() {
    Controller controller1 = new Controller(0);
    Controller controller2 = new Controller(1);

    controller1.streamAnalogInputTo(new TeleopDrive());
    controller1.getBtnY().whenHeld(new InstantCommand(() -> TurretSubsystem.getInstance().returnToStart(), TurretSubsystem.getInstance())
        .alongWith(new SetHoodAngle(AdjustableHoodSubsystem.maxAngle)));
    controller1.getBmpL().whenHeld(new ToggleIntakeMotorOnly());
    controller1.getBmpR().whenHeld(new ToggleIntake());

    controller2.getBtnA().whenHeld(new ToggleShooter(3700.0).alongWith(new SetHoodAngle(AdjustableHoodSubsystem.maxAngle))); // TODO reset turret
    controller2.getBtnX().whenHeld(new ToggleShooter(4000.0));
    controller2.getBtnB().whenHeld(new ToggleShooter(4500.0));
    controller2.getBtnY().whenHeld(new AutoAimUsingTurret().alongWith(new AutoShoot()));
    controller2.getBtnStart().whenHeld(new AutoAimUsingTurret());
    controller2.getDpadLeft().whenHeld(new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(-10), TurretSubsystem.getInstance()));
    controller2.getDpadRight().whenHeld(new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(10), TurretSubsystem.getInstance()));
    controller2.getBmpL().whenHeld(new ReverseIntake());
    controller2.getBmpR().whenHeld(new ToggleTower());
    controller2.getTrgL().whenHeld(new ReverseSpindexer());
    controller2.getTrgR().whenHeld(new ToggleSpindexer());
  }
}
