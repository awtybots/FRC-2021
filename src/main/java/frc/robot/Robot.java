package frc.robot;

import frc.robot.commands.teleop.*;
import org.awtybots.frc.botplus.CompetitionBot;
import org.awtybots.frc.botplus.commands.Controller;
import org.awtybots.frc.botplus.sensors.vision.Limelight;

public class Robot extends CompetitionBot {

  public static Limelight limelight = new Limelight(0.8, 20); // TODO mounting height (meters), mounting angle (degrees)

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
    controller1.getBmpL().whenHeld(new ToggleIntakeMotorOnly());
    controller1.getBmpR().whenHeld(new ToggleIntake());

    controller2.getBtnA().whenHeld(new ToggleShooter(4000.0));
    controller2.getBtnB().whenHeld(new ToggleShooter(5000.0));
    controller2.getBtnX().whenHeld(new ToggleShooter(6000.0));
    controller2.getBtnY().whenHeld(new AutoAim().alongWith(new AutoShoot()));
    controller2.getBmpL().whenHeld(new ReverseTower());
    controller2.getBmpR().whenHeld(new ToggleTower());
  }
}
