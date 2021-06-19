package util.controls;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Controller {
  private XboxController controller;
  private double kDeadzoneStick = 0.2;
  private double kDeadzoneTrigger = 0.1;

  public Controller(int port) {
    controller = new XboxController(port);
  }

  public JoystickButton btnA = createButton(XboxController.Button.kA.value);
  public JoystickButton btnX = createButton(XboxController.Button.kX.value);
  public JoystickButton btnY = createButton(XboxController.Button.kY.value);
  public JoystickButton btnB = createButton(XboxController.Button.kB.value);
  public JoystickButton btnBack = createButton(XboxController.Button.kBack.value);
  public JoystickButton btnStart = createButton(XboxController.Button.kStart.value);

  public JoystickButton bmpL = createButton(XboxController.Button.kBumperLeft.value);
  public JoystickButton bmpR = createButton(XboxController.Button.kBumperRight.value);

  public JoystickButton joystickClickL = createButton(XboxController.Button.kStickLeft.value);
  public JoystickButton joystickClickR = createButton(XboxController.Button.kStickRight.value);

  public POVButton dpadUp = new POVButton(controller, 0);
  public POVButton dpadRight = new POVButton(controller, 90);
  public POVButton dpadDown = new POVButton(controller, 180);
  public POVButton dpadLeft = new POVButton(controller, 270);

  public Trigger trgL = new Trigger(() -> getTrigger(Hand.kLeft) > 0);
  public Trigger trgR = new Trigger(() -> getTrigger(Hand.kRight) > 0);

  /**
   * Stream controller inputs to a [command][AnalogInputCommand] continuously
   *
   * @param[command] The command to send the controller inputs to
   */
  public void streamAnalogInputTo(AnalogInputCommand command) {
    command.controller = this;
    command.schedule();
  }

  public ControllerValues getControllerValues() {
    return new ControllerValues(
        getX(Hand.kLeft),
        getY(Hand.kLeft),
        getX(Hand.kRight),
        getY(Hand.kRight),
        getTrigger(Hand.kLeft),
        getTrigger(Hand.kRight));
  }

  private double getTrigger(Hand hand) {
    return deadzone(controller.getTriggerAxis(hand), kDeadzoneTrigger);
  }

  private double getX(Hand hand) {
    return deadzone(controller.getX(hand), kDeadzoneStick);
  }

  private double getY(Hand hand) {
    return deadzone(-controller.getY(hand), kDeadzoneStick);
  }

  // --- Utilities --- //
  private JoystickButton createButton(int buttonID) {
    return new JoystickButton(this.controller, buttonID);
  }

  private double deadzone(double x, double dz) {
    if (Math.abs(x) > dz) return (x - dz * Math.signum(x)) / (1.0 - dz);
    else return 0.0;
  }
}
