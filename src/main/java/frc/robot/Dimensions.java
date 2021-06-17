package frc.robot;

public abstract class Dimensions {
  public static final double limelightMountingAngle = 23; // degrees TODO doublecheck
  public static final double limelightMountingHeight = inchesToMeters(20.9); // TODO doublecheck

  public static final double powerPortHeight = feetInchesToMeters(8, 2.25);
  public static final double powerPortVisionTargetOffset =
      inchesToMeters(8.5); // distance between center of vision target and center of goal

  public static final double trackWidth =
      inchesToMeters(26.755); // distance between the left and right wheels,

  public static final double climberWinchCircumference = 3; // inches

  private static double feetInchesToMeters(int feet, double inches) {
    inches += feet * 12.0;
    double centimeters = inches * 2.54;
    double meters = centimeters / 100.0;
    return meters;
  }

  private static double inchesToMeters(double inches) {
    double centimeters = inches * 2.54;
    double meters = centimeters / 100.0;
    return meters;
  }
}
