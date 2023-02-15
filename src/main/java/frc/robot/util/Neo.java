package frc.robot.util;

import com.revrobotics.CANSparkMax;

public class Neo extends Motor {
    CANSparkMax motor;

    public Neo(CANSparkMax motor) {
        this.motor = motor;
    }

    @Override
    public void setIdle(IdleMode idle) {
        if (idle == IdleMode.COAST) {
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        } else {
            motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }
    }

    @Override
    public void invertMotor(boolean flipped){
        motor.setInverted(flipped);
        System.out.println(flipped + "hey");
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
        if (currentLimit!=0){
            motor.setSmartCurrentLimit((int)(currentLimit));
        }
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
}
