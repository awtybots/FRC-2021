package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotMap.LimelightPipelines;
import frc.robot.commands.teleop.*;
import frc.robot.subsystems.*;

import org.awtybots.frc.botplus.CompetitionBot;
import org.awtybots.frc.botplus.commands.Controller;
import org.awtybots.frc.botplus.sensors.vision.Limelight;

public class Robot extends CompetitionBot {

  public static Limelight limelight = new Limelight(0.533, 28);
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
          Thread.sleep(5000);
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
    Companion.setTestMode(true); // TODO make it false, this is for tuning only
  }

  @Override
  public void addAutonOptions() {
    // addAutonDefault("name", command);
    // addAutonOption("name", command);
    // addAutonOption("name", command);
    // addAutonOption("name", command);
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    super.teleopPeriodic();
  }

  @Override
  public void bindIO() {
    Controller controller1 = new Controller(0);
    Controller controller2 = new Controller(1);

    controller1.streamAnalogInputTo(new TeleopDrive());
    controller1.getBtnY().whenHeld(new InstantCommand(() -> TurretSubsystem.getInstance().returnToStart(), TurretSubsystem.getInstance()));
    // controller1.getBtnX().whenHeld(new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(-10), TurretSubsystem.getInstance()));
    // controller1.getBtnB().whenHeld(new InstantCommand(() -> TurretSubsystem.getInstance().setRelativeGoalAngle(10), TurretSubsystem.getInstance()));
    controller1.getBmpL().whenHeld(new ToggleIntakeMotorOnly());
    controller1.getBmpR().whenHeld(new ToggleIntake());

    controller2.getBtnA().whenHeld(new ToggleShooter(4000.0));
    controller2.getBtnB().whenHeld(new ToggleShooter(5000.0));
    controller2.getBtnX().whenHeld(new ToggleShooter(6000.0));
    controller2.getBtnY().whenHeld(new AutoAimUsingTurret());//.alongWith(new AutoShoot()));
    controller2.getBmpL().whenHeld(new ReverseIntake());
    controller2.getBmpR().whenHeld(new ToggleTower());
    controller2.getTrgL().whenHeld(new ReverseSpindexer());
    controller2.getTrgR().whenHeld(new ToggleSpindexer());
  }
}
