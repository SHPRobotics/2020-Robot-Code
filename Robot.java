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
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


//import edu.wpi.first.wpilibj.drive.DifferentialDrive;

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

  //DifferentialDrive diffDrive = new DifferentialDrive(leadMotorLeft, leadMotorRight);
  //NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  //Limelight vision = new Limelight(rightJoy, diffDrive);
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

    //table.getEntry("camMode").setNumber(0);
  }

  public void GRRAA ()
  {
    double watch = System.currentTimeMillis();
    
    while (System.currentTimeMillis() - watch <= 200)
    {
      if (xbox.getBackButton())
      {
        vIntake.set(1);
      }
      else if (xbox.getXButton())
      {
        vIntake.set(-1);
      }
      else
      {
        vIntake.set(0);
      }

      if (System.currentTimeMillis() - watch <= 100)
      {
        hopper.set(-0.3);
      }
      else if (150 < System.currentTimeMillis() - watch && System.currentTimeMillis() - watch <= 200)
      {
        hopper.set(0);
      }
    }
  }

  public void forward (double inches)
  {
    double leadLeftOutput1 = leadLeftEncoder.getPosition();
    double weightAdjust = 1.02;
    double speed = 0.2;
    double distance = 0.5 * inches;

    while(leadLeftEncoder.getPosition() < distance + leadLeftOutput1) 
    {
      leadMotorLeft.set(speed);
      leadMotorRight.set(-speed*weightAdjust);
    }
    leadMotorLeft.set(0.0);
    leadMotorRight.set(0.0);
  }

  public void backward (double inches)
  {
    double leadLeftOutput1 = leadLeftEncoder.getPosition();
    double weightAdjust = 1.02;
    double speed = 0.2;
    double distance = 0.5 * inches;

    while(leadLeftEncoder.getPosition() > -distance + leadLeftOutput1) 
    {
      leadMotorLeft.set(-speed);
      leadMotorRight.set(speed*weightAdjust);
    }
    leadMotorLeft.set(0.0);
    leadMotorRight.set(0.0);
  }

  public void turnRight (double degrees) {
    double distance = degrees * 10.69/90;
    double weightAdjust = 1.0115;
    double speed = 0.2;

    double leadLeftOutput1 = leadLeftEncoder.getPosition();
    
    while(leadLeftEncoder.getPosition() < distance + leadLeftOutput1) 
    {
      leadMotorLeft.set(speed);
      leadMotorRight.set(speed*weightAdjust);
    }
    leadMotorLeft.set(0.0);
    leadMotorRight.set(0.0);
  }

  public void turnLeft (double degrees) {
    double distance = degrees * 10.75/90;
    double weightAdjust = 1.02;
    double speed = 0.2;
    double leadLeftOutput1 = leadLeftEncoder.getPosition();
    
    while(leadLeftEncoder.getPosition() > -distance + leadLeftOutput1) 
    {
      leadMotorLeft.set(-speed);
      leadMotorRight.set(-speed*weightAdjust);
    }
    leadMotorLeft.set(0.0);
    leadMotorRight.set(0.0);
  }

  public void brake () {

    double leftVelocity = leadLeftEncoder.getVelocity()/2000;
    double rightVelocity = leadRightEncoder.getVelocity()/2000;

    SmartDashboard.putNumber("leftVelocity", leftVelocity);
    SmartDashboard.putNumber("rightVelocity", rightVelocity);

    double watch = System.currentTimeMillis();
    double time = 150; //ms

    if(rightVelocity > 0) {
      while (leadRightEncoder.getVelocity()/2000 > 0.1 && System.currentTimeMillis() < watch + time) {
        leadMotorLeft.set(-leftVelocity);
        leadMotorRight.set(-rightVelocity);
      }
    }
    else {
      while (leadRightEncoder.getVelocity()/2000 < 0.1 && System.currentTimeMillis() < watch + time) {
        leadMotorLeft.set(-leftVelocity);
        leadMotorRight.set(-rightVelocity);
      }
    }

    

    leadMotorLeft.set(0.0);
    leadMotorRight.set(0.0);
  }

  public void wait (int milliseconds) {
    double watch = System.currentTimeMillis();

    while (System.currentTimeMillis() - watch <= milliseconds) {
      leadMotorLeft.set(0.0);
      leadMotorRight.set(0.0);
    }
  }

  public void shoot(double power)
  {
    shooter1.set(power);
    shooter2.set(power);
  }

  public void DOTDOOTDOOT ()
  {
    double watch = System.currentTimeMillis();
    
    while (System.currentTimeMillis() - watch <= 9000) 
    {

      if (System.currentTimeMillis() - watch <= 2000)
      {
        shooter1.set(-0.6);
        shooter2.set(-0.6);
      }
      else if (2000 <= System.currentTimeMillis() - watch && System.currentTimeMillis() - watch <= 8000)
      {
        GRRAA();
      }
      else if (8000 <= System.currentTimeMillis() - watch && System.currentTimeMillis() - watch <= 9000)
      {
        shooter1.set(0);
        shooter2.set(0);
      }
    }
  }

  public void IntakeDOOTDOOT ()
  {
    double watch = System.currentTimeMillis();
    
    while (System.currentTimeMillis() - watch <= 7000) 
    {
      if (xbox.getBackButton())
      {
        vIntake.set(1);
      }
      else if (xbox.getXButton())
      {
        vIntake.set(-1);
      }
      else
      {
        vIntake.set(0);
      }

      if (System.currentTimeMillis() - watch <= 2000)
      {
        shooter1.set(-0.8);
        shooter2.set(-0.8);
        
      }
      else if (2000 <= System.currentTimeMillis() - watch && System.currentTimeMillis() - watch <= 7000)
      {
        GRRAA();
      }
    }
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
    //System.out.print(System.currentTimeMillis());
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

    //DOTDOOTDOOT();
    forward(15);
    brake();
    /*wait(300);
    backward(36);
    brake();
    wait(300);*/
    /*turnRight(90);
    brake();
    wait(300);
    turnLeft(90);
    brake();
    wait(300);*/
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
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    double tx = table.getEntry("tx").getDouble(0.0);
    double ty = table.getEntry("ty").getDouble(0.0);
    table.getEntry("camMode").setNumber(1);

    //System.out.print("tx: " + tx +"\r");
    //System.out.print("ty: " + ty +"\r");
    SmartDashboard.putNumber("tx", tx);
    SmartDashboard.putNumber("ty", ty);

    //Drive remember to change this back to 0.75
    if(rightJoy.getY() < -0.15 || rightJoy.getY() > 0.15)
    {
      leadMotorRight.set(1 * rightJoy.getY());
    }
    else
    {
      leadMotorRight.set(0); 
    }

    if(leftJoy.getY() < -0.15 || leftJoy.getY() > 0.15)
    {
      leadMotorLeft.set(-1 * leftJoy.getY());
    }
    else
    {
      leadMotorLeft.set(0);
    }
    
    //H & V Intake && James is stupid and gaee && Square button is intake && X button is out
    if(xbox.getAButton()) //intake & hop in
    {
      hIntake.set(-0.6);
      vIntake.set(1);
      hopper.set(0.1);
    }
    else if (xbox.getBButton()) //intake out
    {
      hIntake.set(0.6);
      vIntake.set(-1);
    }
    /*else if (xbox.getRawButtonPressed(3)) //squre and hopin
    {
      double startTime = System.currentTimeMillis(); 
   
      while (System.currentTimeMillis() - startTime < 150)
      {       
        hopper.set(0.3);
      }
    }*/
    else if (xbox.getRawButtonPressed(4)) //triangle & hopout
    {
      double startTime = System.currentTimeMillis(); 
      
      while (System.currentTimeMillis() - startTime < 150)
      {       
        hopper.set(-0.3);
      }
    }
    else if (xbox.getRawButton(8))
    {
      IntakeDOOTDOOT();
    }
    else
    {
      hIntake.set(0.0);
      vIntake.set(0.0);
      hopper.set(0);
    }

    //shooter remember to change back to 0.8
    if (xbox.getRawButtonPressed(6)) 
    {
      //shooter1.set(-1);
      //shooter2.set(-1);
      shooter1.setVoltage(-12);
      shooter2.setVoltage(-12);
      SmartDashboard.putNumber("shooter1 voltage", shooter1.getBusVoltage());
      SmartDashboard.putNumber("shooter2 voltage", shooter2.getBusVoltage());
    }
    else if (xbox.getRawButtonPressed(5))
    {
      shooter1.set(0.0);
      shooter2.set(0.0);
    }
    //vision.checkTarget();
    //vision.setCamera(1);
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
