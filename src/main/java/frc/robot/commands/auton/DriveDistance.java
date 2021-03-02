package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveDistance extends CommandBase {
  private final static double gapTime = 0.5;

  private DriveSideDistance driveSideDistanceL;
  private DriveSideDistance driveSideDistanceR;

  private double plannedTime;
  private Timer timer;

  public DriveDistance(double distance) {
    this(distance, distance);
  }

  public DriveDistance(double distanceL, double distanceR) {
    addRequirements(DrivetrainSubsystem.getInstance());
    driveSideDistanceL = new DriveSideDistance(distanceL);
    driveSideDistanceR = new DriveSideDistance(distanceR);

    if (driveSideDistanceL.plannedTime < driveSideDistanceR.plannedTime) {
      plannedTime = driveSideDistanceR.plannedTime;
      driveSideDistanceL.replanSlower(plannedTime);
    } else {
      plannedTime = driveSideDistanceL.plannedTime;
      driveSideDistanceR.replanSlower(plannedTime);
    }

    timer = new Timer();
  }

  @Override
  public void initialize() {
    timer.start();
  }

  public void execute() {
    double currentTime = timer.get();
    double velocityL = driveSideDistanceL.tick(currentTime);
    double velocityR = driveSideDistanceR.tick(currentTime);
    DrivetrainSubsystem.getInstance().setMotorVelocityOutput(velocityL, velocityR);
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted)
      DrivetrainSubsystem.getInstance().kill();
    else
      DrivetrainSubsystem.getInstance().softStop();
  }

  @Override
  public boolean isFinished() {
    return timer.get() > plannedTime + gapTime;
  }
}
