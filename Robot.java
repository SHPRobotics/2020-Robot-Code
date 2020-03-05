/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.NetworkTable;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;


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

  CANSparkMax leadMotorRight = new CANSparkMax(3, MotorType.kBrushless);
  CANEncoder leadRightEncoder = new CANEncoder(leadMotorRight);
  CANSparkMax followMotorRight = new CANSparkMax(4, MotorType.kBrushless);
  CANSparkMax leadMotorLeft = new CANSparkMax(1, MotorType.kBrushless);
  CANEncoder leadLeftEncoder = new CANEncoder(leadMotorLeft);
  CANSparkMax followMotorLeft = new CANSparkMax(2, MotorType.kBrushless);

  CANSparkMax shooter1 = new CANSparkMax(5, MotorType.kBrushed);
  CANSparkMax shooter2 = new CANSparkMax(6, MotorType.kBrushed);
  CANSparkMax hIntake = new CANSparkMax(8, MotorType.kBrushed);
  CANSparkMax vIntake = new CANSparkMax(9, MotorType.kBrushed);
  CANSparkMax hopper = new CANSparkMax(7, MotorType.kBrushed);

  Joystick leftJoy = new Joystick(0);
  Joystick rightJoy = new Joystick(1);
  XboxController xbox = new XboxController(2);

  DifferentialDrive diffDrive = new DifferentialDrive(leadMotorLeft, leadMotorRight);

  Limelight vision = new Limelight(rightJoy, diffDrive);
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leadMotorRight.restoreFactoryDefaults();
    followMotorRight.restoreFactoryDefaults();
    leadMotorLeft.restoreFactoryDefaults();
    followMotorLeft.restoreFactoryDefaults();
    shooter1.restoreFactoryDefaults();
    shooter2.restoreFactoryDefaults();
    hIntake.restoreFactoryDefaults();
    vIntake.restoreFactoryDefaults();
    hopper.restoreFactoryDefaults();

    followMotorRight.follow(leadMotorRight);
    followMotorLeft.follow(leadMotorLeft);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  public void GRRAA ()
  {
    shooter1.set(1.0);
    shooter2.set(1.0);

    for(int i=0; i < 4; i++)
    {
      double startT = System.currentTimeMillis();

      while (System.currentTimeMillis() - startT < 700)
      {
        double startT2 = System.currentTimeMillis();

        while (System.currentTimeMillis() - startT2 < 150)
        {
          hopper.set(-0.3);
        } 
      }
    }
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
      double startTime = System.currentTimeMillis(); 
      
      while (System.currentTimeMillis() - startTime < 150)
      {       
        leadMotorLeft.set(0.2);
        leadMotorRight.set(-0.2);
      }

      leadMotorLeft.set(0.0);
      leadMotorRight.set(0.0);
    default:      
      break;
    }
  }

  /**
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    //Drive
    if(rightJoy.getY() < -0.15 || rightJoy.getY() > 0.15)
    {
      leadMotorRight.set(0.75 * rightJoy.getY());
    }
    else
    {
      leadMotorRight.set(0); 
    }

    if(leftJoy.getY() < -0.15 || leftJoy.getY() > 0.15)
    {
      leadMotorLeft.set(-0.75 * leftJoy.getY());
    }
    else
    {
      leadMotorLeft.set(0);
    }
    
    //H & V Intake && James is stupid and gaee && Square button is intake && X button is out
    if(xbox.getAButton()) 
    {
      hIntake.set(-1);
      vIntake.set(0.6);
    } 
    else if (xbox.getBButton())
    {
      hIntake.set(1);
      vIntake.set(-.6);
    }
    else
    {
      hIntake.set(0.0);
      vIntake.set(0.0);
    }

    //Shooter
    if (xbox.getRawButtonPressed(6)) 
    {
      shooter1.set(-0.8);
      shooter2.set(-0.8);
    } 
    else if (xbox.getRawButtonPressed(5))
    {
      shooter1.set(0.0);
      shooter2.set(0.0);
    }

    if(xbox.getRawButtonPressed(7))
    {
      GRRAA();
    }
    //Hopper
    if(xbox.getRawButtonPressed(3)) //circle
    {
      double startTime = System.currentTimeMillis(); 
      
      while (System.currentTimeMillis() - startTime < 150)
      {       
          hopper.set(0.3);
      }
    }
    else if (xbox.getRawButtonPressed(4)) //triangle
    {
      double startTime = System.currentTimeMillis(); 
      
      while (System.currentTimeMillis() - startTime < 150)
      {       
        hopper.set(-0.3);
      }
    }
    else
    {
      hopper.set(0.0);
    }

    //vision.checkTarget();
    vision.setCamera(1);
  }

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
