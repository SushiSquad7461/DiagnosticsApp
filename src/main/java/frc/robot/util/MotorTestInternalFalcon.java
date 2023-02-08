package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class MotorTestInternalFalcon {

    WPI_TalonFX motor;
    double lowLimit = Double.MIN_VALUE;
    double highLimit = Double.MAX_VALUE;

    public MotorTestInternalFalcon(WPI_TalonFX motor){
        this.motor = motor;
    }

    public void setIdleMode(WPI_TalonFX motor, NeutralMode nuetralMode) {
        motor.setNeutralMode(nuetralMode);
    }

    public void invertMotor(WPI_TalonFX motor, Boolean flipped) {
        motor.setInverted(flipped);
    }
    
    public void setSpeed(WPI_TalonFX motor, double newSpeed){
        double position = motor.getSelectedSensorPosition();
        if (!((newSpeed < 0 && position >= lowLimit) || position <= highLimit)){
            newSpeed = 0;
        }
        motor.set(newSpeed);
    }    
    
    public void setCurrentLimit(WPI_TalonFX motor, double currentLimit) {
        SupplyCurrentLimitConfiguration CurrentLimit = new SupplyCurrentLimitConfiguration(true, currentLimit, currentLimit,0.0);
        motor.configSupplyCurrentLimit(CurrentLimit);
    }    

    public void setEncoderLimit(WPI_TalonFX motor, double lowLimit, double highLimit) {
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    public void disable(WPI_TalonFX motor){
        motor.disable();
    }
}
