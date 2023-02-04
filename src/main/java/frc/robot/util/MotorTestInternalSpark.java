package frc.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

public class MotorTestInternalSpark {
    
    CANSparkMax motor;
    double lowLimit = Double.MIN_VALUE;
    double highLimit = Double.MAX_VALUE;
    
    public MotorTestInternalSpark(CANSparkMax motor){
        this.motor = motor;
    }

    public void setIdleMode(CANSparkMax motor, IdleMode idleMode){
        motor.setIdleMode(idleMode);
    }
    
    public void invertMotor(CANSparkMax motor, Boolean flipped){
        motor.setInverted(flipped);
    }

    public void setSpeed(CANSparkMax motor, double newSpeed){
        double position = motor.getEncoder().getPosition();
        if (!((newSpeed < 0 && position >= lowLimit) || position <= highLimit)){
            newSpeed = 0;
        }
        motor.set(newSpeed);
    }
    
    public void setCurrentLimit(CANSparkMax motor, int currentLimit){
        motor.setSmartCurrentLimit(currentLimit);
    }
    
    public void setEncoderLimit(CANSparkMax motor, double lowLimit, double highLimit){
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
        
    }

}
