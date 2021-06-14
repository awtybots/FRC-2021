package frc.robot;

public abstract class RobotMap {
  public abstract static class CAN {
    public static final int leftDrive1 = 13;
    public static final int leftDrive2 = 14;
    public static final int rightDrive1 = 11;
    public static final int rightDrive2 = 12;

    public static final int intake = 5;
    public static final int spindexer = 7;
    public static final int tower = 9;

    public static final int turret = 8;
    public static final int adjustableHood = 10;
    public static final int shooter = 15;

    public static final int controlPanelSpinner = 6; // lmao

    public static final int indexerL = -1; // TODO
    public static final int indexerR = -1;

    public static final int climber = 16;
  }

  public abstract static class PDP {
    public static final int spindexer = 4;
    public static final int tower = 13;
    public static final int adjustableHood = 12;
    public static final int climber = 2;
  }

  public abstract static class PCM {
    public static final int intakeFwd = 5;
    public static final int intakeRev = 4;
  }

  public abstract static class LimelightPipelines {
    public static final int powerPort = 0;
    public static final int idle = 1;
  }

  public abstract static class Dimensions {
    public static final double limelightMountingAngle = 23; // degrees TODO tune
    public static final double limelightMountingHeight = feetInchesMeters(20.9); // TODO doublecheck

    public static final double powerPortHeight = feetInchesMeters(8, 2.25);
    public static final double powerPortVisionTargetOffset =
        feetInchesMeters(8.5); // TODO remove this is a fudge number

    public static final double trackWidth =
        feetInchesMeters(26.755); // distance between the left and right wheels, meters
  }

  public static double feetInchesMeters(int feet, double inches) {
    inches += feet * 12.0;
    double centimeters = inches * 2.54;
    double meters = centimeters / 100.0;
    return meters;
  }

  public static double feetInchesMeters(double inches) {
    double centimeters = inches * 2.54;
    double meters = centimeters / 100.0;
    return meters;
  }
}
