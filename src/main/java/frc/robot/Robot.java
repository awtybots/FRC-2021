package frc.robot;

import frc.robot.commands.*;
import org.awtybots.frc.botplus.CompetitionBot;
import org.awtybots.frc.botplus.commands.Controller;

public class Robot extends CompetitionBot {

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

    controller1.streamAnalogInputTo(new TeleopDrive());
  }
}
