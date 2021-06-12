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

    compressor.setClosedLoopControl(true);
  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
    ledOutput.set(DriverStation.getInstance().getAlliance() == Alliance.Red);
  }

  @Override
  public boolean isTestMode() {
    return true; // TODO make it false, this is for tuning and practice only
  }

  @Override
  public void addAutonOptions() {
    addAutonDefault("Shoot 3 and Reverse", new Shoot3AndReverse());
    addAutonOption("Drive Forward", new DriveForward());
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
    controller1.getTrgL().whenHeld(new ToggleIntakeMotorOnly());
    controller1.getTrgR().whenHeld(new ToggleIntake());

    controller2.getBtnA().whenHeld(new ManualShootingPreset(3700.0, 76));
    controller2.getBtnX().whenHeld(new ManualShootingPreset(4000.0, 65));
    controller2.getBtnB().whenHeld(new ManualShootingPreset(4500.0, 50));
    controller2.getBtnY().whenHeld(new AutoAimUsingTurret().alongWith(new AutoShoot()));
    controller2.getBtnBack().whenPressed(new ResetTurret().alongWith(new SetHoodAngle(AdjustableHoodSubsystem.maxAngle)));
    controller2.getBtnStart().whenHeld(new AutoAimUsingTurret());
    controller2.getDpadLeft().whenPressed(new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(-10), TurretSubsystem.getInstance()));
    controller2.getDpadRight().whenPressed(new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(10), TurretSubsystem.getInstance()));
    controller2.getBmpL().whenHeld(new ReverseTower());
    controller2.getBmpR().whenHeld(new ToggleTower());
    controller2.getTrgL().whenHeld(new ReverseSpindexer());
    controller2.getTrgR().whenHeld(new ToggleSpindexer());
  }
}
