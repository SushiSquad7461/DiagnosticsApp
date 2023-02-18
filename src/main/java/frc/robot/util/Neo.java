package frc.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;

public class Neo extends Motor {
    CANSparkMax motor;
    public int canID;
    public String revErrors;
    public double currentLimit;

    public Neo(CANSparkMax motor) {
        this.motor = motor;
        this.canID = motor.getDeviceId();
        this.currentLimit = motor.getOutputCurrent();
    }

    @Override
    public void setIdle(IdleMode idle) {
        REVLibError errorChecker = REVLibError.kOk;
        if (idle == IdleMode.COAST) {
            errorChecker = motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        } else {
            errorChecker = motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }
        revErrors +=  "coast/brake of " + motor.getDeviceId() + " is " + errorChecker.toString() + ", ";
    }

    @Override
    public void invertMotor(boolean flipped){
        motor.setInverted(flipped);
    }

    @Override
    public void setSpeed(double newSpeed){
        double position = motor.getEncoder().getPosition();
        if (highLimit != 0){
            if (!((newSpeed < 0 && position >= lowLimit) || position <= highLimit)){
                newSpeed = 0;
            }
        }
        motor.set(newSpeed);
    }

    @Override
    public void setCurrentLimit(double currentLimit){
        REVLibError errorChecker = REVLibError.kOk;
        if (currentLimit != 0){
            errorChecker = motor.setSmartCurrentLimit((int)(currentLimit));
        }
        revErrors +=  "current limit of " + motor.getDeviceId() + " is " + errorChecker.toString() + ", ";
    }

    @Override
    public void setEncoderLimit(double lowLimit, double highLimit){
        this.lowLimit = lowLimit;
        if(highLimit != 0){
            motor.getEncoder().setPosition(0);
            this.highLimit = highLimit;
        }
    }

    public void disable() {
        motor.disable();
    }

    public String checkElecErrors() { //add to string array in motor test
        String elecErrors = "";
        if (motor.getFault(CANSparkMax.FaultID.kBrownout)){
            elecErrors += motor.getDeviceId() + " brownout, ";
        }

        if (motor.getFault(CANSparkMax.FaultID.kMotorFault)){
            elecErrors += motor.getDeviceId() + " motor fault, ";
        }

        if (motor.getFault(CANSparkMax.FaultID.kOvercurrent)){
            elecErrors += motor.getDeviceId() + " over current, ";
        }

        if (motor.getFault(CANSparkMax.FaultID.kStall)){
            elecErrors += motor.getDeviceId() + " stalling, ";
        }

        if (motor.getFault(CANSparkMax.FaultID.kHasReset)){
            elecErrors += motor.getDeviceId() + " has reset, ";
        }

        return elecErrors;
    }

}
