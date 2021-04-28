/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  WPI_TalonSRX frontLeftTalon = new WPI_TalonSRX(1);
  WPI_TalonSRX backLeftTalon = new WPI_TalonSRX(0);
  WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(2);
  WPI_TalonSRX backRightTalon = new WPI_TalonSRX(3);

  Joystick leftJoy = new Joystick(0);
  Joystick rightJoy = new Joystick(1);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    // camera.setResolution(720,640);
    // camera.setFPS(30);

    frontLeftTalon.configFactoryDefault();
    backLeftTalon.configFactoryDefault();
    frontRightTalon.configFactoryDefault();
    backRightTalon.configFactoryDefault();

    frontLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    backLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    frontRightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    backRightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

  }

 // For Testing Purposes 

 /* public void tick() {
    System.out.println(leftJoy.getY());
    System.out.println(rightJoy.getY());

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    tick();
  } */

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

// Mecanum Drive with 2 joysticks (Completely Functional)

double leftY = -leftJoy.getY();
double leftX = -leftJoy.getX();
double rightY = rightJoy.getY();

frontLeftTalon.set(ControlMode.PercentOutput, rightY + leftY + leftX);
backLeftTalon.set(ControlMode.PercentOutput, rightY + leftY - leftX);
frontRightTalon.set(ControlMode.PercentOutput, rightY - leftY + leftX);
backRightTalon.set(ControlMode.PercentOutput, rightY - leftY - leftX);

// For Testing Purposes

 /* if (leftJoy.getY() > 0.1) {
    frontLeftTalon.set(ControlMode.PercentOutput, 1);
  } 
  if (leftJoy.getY() < -0.1) {
    backLeftTalon.set(ControlMode.PercentOutput, 1);
  }
  if (rightJoy.getY() > 0.1) {
    frontRightTalon.set(ControlMode.PercentOutput, 1);
  }
  if (rightJoy.getY() < -0.1) {
    backRightTalon.set(ControlMode.PercentOutput, 1);
  } */


   // For Testing Purposes 
   // tick();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
