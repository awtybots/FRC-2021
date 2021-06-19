package util.controls;

public class ControllerValues {
  public double leftStickX = 0.0;
  public double leftStickY = 0.0;
  public double rightStickX = 0.0;
  public double rightStickY = 0.0;
  public double leftTrigger = 0.0;
  public double rightTrigger = 0.0;

  public ControllerValues() {}

  public ControllerValues(
      double leftStickX,
      double leftStickY,
      double rightStickX,
      double rightStickY,
      double leftTrigger,
      double rightTrigger) {
    this.leftStickX = leftStickX;
    this.leftStickY = leftStickY;
    this.rightStickX = rightStickX;
    this.rightStickY = rightStickY;
    this.leftTrigger = leftTrigger;
    this.rightTrigger = rightTrigger;
  }
}
