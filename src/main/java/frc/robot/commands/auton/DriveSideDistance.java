package frc.robot.commands.auton;

import edu.wpi.first.wpiutil.math.MathUtil;

class DriveSideDistance {
  private final static double defaultMaxVelocity = 2.0; // meters per second
  private final static double defaultMaxAcceleration = 2.0; // meters per second per second

  private double maxVelocity;
  private double maxAcceleration;

  private double distanceGoal;
  double plannedTime;

  private double decelerationStartTime;

  private double outputFactor = 1.0;

  DriveSideDistance(double distance) {
    distanceGoal = distance;

    if (distanceGoal < 0.0) {
      distanceGoal = -distanceGoal;
      outputFactor = -1.0;
    }

    planFastest();
  }

  private void planFastest() {
    maxVelocity = defaultMaxVelocity;
    maxAcceleration = defaultMaxAcceleration;

    double timeToAccelerate = maxVelocity / maxAcceleration;
    double distanceWhileAccelerating = maxVelocity * timeToAccelerate; // accounts for both accel at beginning and decel
                                                                       // at the end

    if (distanceGoal >= distanceWhileAccelerating) {
      plannedTime = timeToAccelerate * 2.0 + (distanceGoal - distanceWhileAccelerating) / maxVelocity;
      decelerationStartTime = plannedTime - timeToAccelerate;
    } else {
      plannedTime = 2.0 * Math.sqrt(distanceGoal / maxAcceleration); // TODO double check this math
      decelerationStartTime = plannedTime / 2.0;
    }
  }

  void replanSlower(double newTime) {
    double factor = plannedTime / newTime;
    maxVelocity *= factor;
    maxAcceleration *= factor;
  }

  double tick(double currentTime) {
    double newVelocity;

    if (currentTime > plannedTime) {
      newVelocity = 0.0;
    } else if (currentTime > decelerationStartTime) {
      newVelocity = maxVelocity - maxAcceleration * (currentTime - decelerationStartTime);
    } else {
      newVelocity = Math.min(maxAcceleration * currentTime, maxVelocity);
    }

    return MathUtil.clamp(newVelocity, 0.0, maxVelocity) * outputFactor;
  }

}