package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.DrivetrainSubsystem;
import org.awtybots.frc.botplus.CompetitionBot;
import org.awtybots.frc.botplus.commands.Controller;
import org.awtybots.frc.botplus.sensors.vision.Limelight;

public class Robot extends CompetitionBot {

  public static Limelight limelight = new Limelight(0.8, 20);

  @Override
  public void addAutonOptions() {
    // addAutonDefault("name", command);
    // addAutonOption("name", command);
    // addAutonOption("name", command);
    // addAutonOption("name", command);
  }

  @Override
  public void bindIO() {
    Controller controller1 = new Controller(0);
    Controller controller2 = new Controller(1);

    controller1.streamAnalogInputTo(new TeleopDrive());
    controller1.getBmpL().whenHeld(new ToggleIntakeMotorOnly());
    controller1.getBmpR().whenHeld(new ToggleIntake());

    // controller2.getBtnA().whenHeld(new SetShooterSpeed(Shooter.MANUAL_RPM_1));
    // controller2.getBtnB().whenHeld(new SetShooterSpeed(Shooter.MANUAL_RPM_2));
    // controller2.getBtnX().whenHeld(new SetShooterSpeed(Shooter.MANUAL_RPM_3));
    controller2.getBtnY().whenHeld(new AutoAim());
    controller2.getBmpL().whenHeld(new ReverseTower());
    controller2.getBmpR().whenHeld(new ToggleIndexerTower());
  }

  @Override
  public void testInit() {
    SmartDashboard.putData(DrivetrainSubsystem.getInstance().getConfig());
  }
}
