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

import javax.lang.model.util.ElementScanner6;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



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
  CANEncoder hopperEncoder = new CANEncoder(hopper);

  Joystick leftJoy = new Joystick(0);
  Joystick rightJoy = new Joystick(1);
  XboxController xbox = new XboxController(2);

  /*Drive talonDrive = new Drive(leftJoy, rightJoy, leadMotorLeft, leadMotorRight);
  Limelight vision = new Limelight(diffTalonDrive, rightJoy);*/

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

    /*public static forward (distance)
    {
      double leadRightOutput1 = leadRightEncoder.getPosition();

      while(leadRightEncoder.getPosition() > -distance + leadRightOutput1) {
        leadMotorLeft.set(-0.5);
        leadMotorRight.set(-0.5);
      }
    } */
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
    if(rightJoy.getY() < -0.1 || rightJoy.getY() > 0.1)
    {
      leadMotorRight.set(rightJoy.getY());
    }
    else
    {
      leadMotorRight.set(0); 
    }

    if(leftJoy.getY() < -0.1 || leftJoy.getY() > 0.1)
    {
      leadMotorLeft.set(-leftJoy.getY());
    }
    else
    {
      leadMotorLeft.set(0);
    }
    
    /*
    //Encoder Test
    
    if(xbox.getRawButtonPressed(7)) {
      double leadLeftOutput1 = leadLeftEncoder.getPosition();
      
      while(leadLeftEncoder.getPosition() < 10 + leadLeftOutput1) {
        leadMotorLeft.set(0.5);
        leadMotorRight.set(0.5);
      }
    } 
    
    if (xbox.getRawButtonPressed(8)) 
    {
      double leadRightOutput1 = leadRightEncoder.getPosition();

      while(leadRightEncoder.getPosition() > -10 + leadRightOutput1) {
        leadMotorLeft.set(-0.5);
        leadMotorRight.set(-0.5);
      }
    } 
    */
    //H & V Intake && James is stupid and gaee
    if(xbox.getAButton()) 
    {
      hIntake.set(-0.5);
      vIntake.set(1);
    } 
    else if (xbox.getBButton())
    {
      hIntake.set(0.5);
      vIntake.set(-1);
    }
    else
    {
      hIntake.set(0.0);
      vIntake.set(0.0);
    }

    //Shooter
    
    if (xbox.getRawButtonPressed(5)) 
    {
      shooter1.set(-1.0);
      shooter2.set(-1.0);
    } 
    else if (xbox.getRawButtonPressed(6))
    {
      shooter1.set(0.0);
      shooter2.set(0.0);
    }
    
    //Hopper aka Circle Button
    if(xbox.getYButton())
    {
      hopper.set(-0.5);
    }
    else if (xbox.getXButton())
    {
      hopper.set(0.5);
    }
    else
    {
      hopper.set(0.0);
    }
    /*
    if(xbox.getRawButtonPressed(3)) 
    {
      for(int i = 0; i < 4; i++)
      {
        double startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 1) 
        {
          hopper.set(0.5);
        }
      }
    }
    else if (xbox.getRawButtonPressed(4))
    {
      for(int i = 0; i < 4; i++)
      {
        double startTime = System.currentTimeMillis();
        
        while (System.currentTimeMillis() - startTime < 1) 
        {
          hopper.set(-0.5);
        }
      }
    }
    else 
    {
      hopper.set(0);
    }*/
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
