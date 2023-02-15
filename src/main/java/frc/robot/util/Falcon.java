package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Falcon extends Motor{
    WPI_TalonFX motor;
    
    Falcon(WPI_TalonFX motor) {
        this.motor = motor;
    }

    @Override
    public void setIdle(IdleMode idle) {
        if (idle == IdleMode.COAST) {
            motor.setNeutralMode(NeutralMode.Coast);
        } else {
            motor.setNeutralMode(NeutralMode.Brake);
        }
    }

    @Override
    public void invertMotor(boolean flipped) {
        motor.setInverted(flipped);
    }
    
    @Override
    public void setSpeed(double newSpeed){
        double position = motor.getSelectedSensorPosition();
        if (!((newSpeed < 0 && position >= lowLimit) || position <= highLimit)){
            newSpeed = 0;
        }
        motor.set(newSpeed);
    }    
    
    // TODO: ask maanav
    @Override
    public void setCurrentLimit(double currentLimit) {
        SupplyCurrentLimitConfiguration CurrentLimit = new SupplyCurrentLimitConfiguration(true, currentLimit, currentLimit,0.1);
        motor.configSupplyCurrentLimit(CurrentLimit);
    }

    // public void setCurrentLimit(double currentLimit1, double currentLimit2, double threshhold) {
    //     SupplyCurrentLimitConfiguration CurrentLimit = new SupplyCurrentLimitConfiguration(true, currentLimit1, currentLimit2,threshhold);
    //     motor.configSupplyCurrentLimit(CurrentLimit);
    // }

    @Override
    public void setEncoderLimit(double lowLimit, double highLimit) {
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    @Override
    public void disable(){
        motor.disable();
    }
}
