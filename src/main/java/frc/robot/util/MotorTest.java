package frc.robot.util;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.MotorTest;

import java.util.List;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import SushiFrcLib.Motor.MotorHelper;


public class MotorTest {
    // neos
    public static boolean coastOrBrake(CANSparkMax motor, int num){
        if(num==0){
          return (motor.getIdleMode()==IdleMode.kCoast);
          //motor.setIdleMode(IdleMode.kCoast);
        }
        else{
          return (motor.getIdleMode()==IdleMode.kBrake);
          //motor.setIdleMode(IdleMode.kBrake);
        }
    }
    
    public static boolean flipEncoder(CANSparkMax motor, Boolean flipped){
        //motor.setInverted(flipped);
        return(motor.getInverted()==flipped);
    }

    public static boolean setSpeed(CANSparkMax motor, double speed){
        motor.set(speed);
        return (motor.get() >= speed-.5 && motor.get()< speed+.5);
    }
    
    public static boolean setCurrentLimit(CANSparkMax motor, int currentLimit){
        //motor.setSmartCurrentLimit(currentLimit);
        return (motor.getOutputCurrent()<currentLimit);
    }
    
    public static boolean setEncoderLimit(CANSparkMax motor, double lowLimit, double highLimit){
        if (motor.getEncoder().getPosition() <lowLimit || motor.getEncoder().getPosition() >highLimit){
          //motor.disable();
          return false;
        }
        return true;
    }
}
