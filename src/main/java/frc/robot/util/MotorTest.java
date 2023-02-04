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

  static MotorTest instance = null;
  static List<MotorTestInternalSpark> motorList;

  public static MotorTest GetInstance(){
    if (instance == null){
      instance = new MotorTest();
    }
    return instance;
  }

    private MotorTest(){

    }

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

    public void runMotor(CANSparkMax motor, List<String> inputs){
      //speed = Double.parseDouble(inputs.get(0));
      MotorTest.coastOrBrake(motor, Integer.parseInt(inputs.get(1)));

      MotorTest.setCurrentLimit(motor, Integer.parseInt(inputs.get(3)) );
      MotorTest.setEncoderLimit(motor, Integer.parseInt(inputs.get(4)), Integer.parseInt(inputs.get(5)));
    }
    

    public static boolean invertMotor(CANSparkMax motor, int motorNum){
        String[] array = SmartDashboard.getStringArray("dataTable", null);
        String[] motorArray = (array[motorNum]).split(" ");

        boolean flipped = (motorArray[7].equals("1"));
        motor.setInverted(flipped);
        return(motor.getInverted()==flipped);
    }


    //SETSPEED(MOTOR1)
    public static void setSpeed(CANSparkMax motor){
        String[] array = SmartDashboard.getStringArray("dataTable", null);
        for (int i = 0; i < array.length; i++){
          String[] motorArray = (array[i]).split(" ");
          double speed = (Double.parseDouble(motorArray[4]));
          motorList.get(i).setSpeed(motor, speed);
        }
    }
    
    public static boolean setCurrentLimit(CANSparkMax motor, int currentLimit){
        return (motor.getOutputCurrent() < currentLimit);
    }
    
    public static boolean setEncoderLimit(CANSparkMax motor, double lowLimit, double highLimit){
        if (motor.getEncoder().getPosition() <lowLimit || motor.getEncoder().getPosition() >highLimit){
          //motor.disable();
          return false;
        }
        return true;
    }

    public void registerMotor(MotorTestInternalSpark motorInternalSpark, String string, String string2,
        String string3) {
        motorList.add(motorInternalSpark);
    }
}
