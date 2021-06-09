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
  }

  public abstract static class PCM {
    public static final int intakeFwd = 4;
    public static final int intakeRev = 5;
  }
}
