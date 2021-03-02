package frc.robot;

public abstract class RobotMap {
  public abstract static class CAN {
    public static final int leftDrive1 = 1;
    public static final int leftDrive2 = 2;
    public static final int rightDrive1 = 3;
    public static final int rightDrive2 = 4;

    public static final int intake = 9;

    public static final int indexerL = 8;
    public static final int indexerR = 7;

    public static final int tower1 = 5;
    public static final int tower2 = 6;

    public static final int shooter = 10;

    public static final int controlPanelSpinner = 13;
  }

  public abstract static class PCM {
    public static final int intakeFwd = 0;
    public static final int intakeRev = 4;
  }
}
