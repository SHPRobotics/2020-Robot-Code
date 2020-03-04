package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class Drive {

    private Joystick leftJoy;
    private Joystick rightJoy;
    private CANSparkMax leadMotorRight;
    private CANEncoder leadRightEncoder;
    private CANSparkMax leadMotorLeft;
    private CANEncoder leadLeftEncoder;

    public Drive (Joystick ljoy, Joystick rjoy, CANSparkMax lmr, CANSparkMax lml,
     CANEncoder lre, CANEncoder lle)
    {
        leftJoy = ljoy;
        leadMotorRight = lmr;
        leadRightEncoder = lre;
        leadMotorLeft = lml;
        leadLeftEncoder = lle;
    }

    public void move () 
    {
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

        //Encoder Test
        /*
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
        }
        */
    }
}