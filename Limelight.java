package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Limelight {

    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();

    // Toggle for posting to SmartDashboard
    public static final boolean POST_TO_SMART_DASHBOARD = true;

    public void toggle ()
    {
        getValue("camMode").setNumber(1);
    }

    private static NetworkTableEntry getValue(String key) {
      if (tableInstance == null) {
        tableInstance = NetworkTableInstance.getDefault();
      }
  
      return tableInstance.getTable("limelight").getEntry(key);
    }
}
