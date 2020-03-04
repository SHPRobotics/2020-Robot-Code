package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;



public class Hopper {
    
    private CANSparkMax motor;
    private XboxController xbox;
    private CANEncoder encoder;
    
    public Hopper (XboxController xboxController, CANSparkMax hopperMotor, CANEncoder hopperEncoder){
        
        xbox = xboxController;
        motor = hopperMotor;
        encoder = hopperEncoder;

    }
    
    public void rotate () 
    {
        if(xbox.getRawButtonPressed(3)) 
        {
          int i = 0;
          while(i < 4)
          {
            double startTime = System.currentTimeMillis(); 
    
            while (System.currentTimeMillis() - startTime < 1)
            {
              double motorPos = encoder.getPosition();
              
              while(encoder.getPosition() > -10 + motorPos)
              {
                motor.set(-0.5);
              }
              
              i++;
            }
    
          }
        }
        else if (xbox.getRawButtonPressed(4))
        {
          int i = 0;
          while(i < 4)
          {
            double startTime = System.currentTimeMillis(); 
    
            while (System.currentTimeMillis() - startTime < 1)
            {
              double motorPos = encoder.getPosition();
              
              while(encoder.getPosition() < 10 + motorPos)
              {
                motor.set(0.5);
              }
    
              i++;
            }
    
          }
        }
        else 
        {
          motor.set(0);
        }
    }
}
