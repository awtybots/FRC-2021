package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotMap;

public class DriveCurve extends SequentialCommandGroup {

  /**
   * a smooth arc path for the robot to follow
   * @param arcRadius radius of arc drawn by path of robot
   * @param degrees degrees of circle to make arc from (positive means curve right)
   */
  public DriveCurve(double arcRadius, double degrees) {
    double radians = Math.toRadians(degrees);

    double centerArcLength = arcRadius * radians;
    double differentialArcLength = (RobotMap.Dimensions.trackWidth * 0.5) * radians;

    double leftArcLength = centerArcLength + differentialArcLength;
    double rightArcLength = centerArcLength - differentialArcLength;

    addCommands(new DriveDistance(leftArcLength, rightArcLength));
  }
}
