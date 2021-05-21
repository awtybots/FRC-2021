package frc.robot;

public abstract class RobotMap {
  public abstract static class CAN {
    public static final int leftDrive1 = 1;
    public static final int leftDrive2 = 2;
    public static final int rightDrive1 = 3;
    public static final int rightDrive2 = 4;

    public static final int intake = 5;
    public static final int spindexer = 6;
    public static final int tower = 7;

    public static final int turret = 8;
    public static final int adjustableHood = 9;
    public static final int shooter = 10;

    public static final int controlPanelSpinner = 13; // lmao
  }

  public abstract static class PCM {
    public static final int intakeFwd = 0;
    public static final int intakeRev = 4;
  }
}
