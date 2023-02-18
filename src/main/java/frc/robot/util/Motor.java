package frc.robot.util;

public abstract class Motor {
    enum IdleMode {
        COAST,
        BRAKE,
    }
    public abstract void setIdle(IdleMode idle);
    public abstract void invertMotor(boolean flipped);
    public abstract void setSpeed(double speed);
    public abstract void setCurrentLimit(double limit);
    public abstract void setEncoderLimit(double low, double high);
    public abstract void disable();

    double lowLimit = Double.MIN_VALUE;
    double highLimit = Double.MAX_VALUE;
}