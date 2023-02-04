package frc.robot.util;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorTest;
import java.util.List;

import com.revrobotics.CANSparkMax.IdleMode;


public class MotorTest {

  static MotorTest instance = null;
  static List<MotorTestInternalSpark> motorList;

  public static MotorTest GetInstance(){
    if (instance == null){
      instance = new MotorTest();
    }
    return instance;
  }

    public MotorTest(){

    }

    public void isUpdated(){
      if (SmartDashboard.getString("changed?", null) == "true"){
        for (int i = 0; i < motorList.size(); i++){
          coastOrBrake(motorList.get(i).motor);
          invertMotor(motorList.get(i).motor);
          setCurrentLimit(motorList.get(i).motor);
          setEncoderLimit(motorList.get(i).motor);
        }
      }
    }

    public void isRun(){
      if (SmartDashboard.getString("running?", null) == "true"){
        for (int i = 0; i < motorList.size(); i++){
          setSpeed(motorList.get(i).motor);
        }
      }
    }

    public void isStop(){
      if (SmartDashboard.getString("running", null) == "false"){
        for (int i = 0; i < motorList.size(); i++){
          stopMotors(motorList.get(i).motor);
        }
      }
    }

    public static void coastOrBrake(CANSparkMax motor){
      String[] array = SmartDashboard.getStringArray("dataTable", null);
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        IdleMode isCoast;
        if ((motorArray[6]) == "false"){
          isCoast = IdleMode.kBrake;
        } else {
          isCoast = IdleMode.kCoast;
        }
        motorList.get(i).setIdleMode(motor, isCoast);
      }
    }
    

    public static void invertMotor(CANSparkMax motor){
      String[] array = SmartDashboard.getStringArray("dataTable", null);
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        boolean isInverted = motorArray[7] == "false";
        motorList.get(i).invertMotor(motor, isInverted);
      }
    }

    public static void setSpeed(CANSparkMax motor){
        String[] array = SmartDashboard.getStringArray("dataTable", null);
        for (int i = 0; i < array.length; i++){
          String[] motorArray = (array[i]).split(" ");
          double constSpeed = (Double.parseDouble(motorArray[4]));
          double joystickSpeed = (Double.parseDouble(motorArray[5]));
          double joystickVal = 1;
          motorList.get(i).setSpeed(motor, constSpeed + joystickVal * joystickSpeed);
        }
    }
    
    public static void stopMotors(CANSparkMax motor){
      motor.disable();
    }

    public static void setCurrentLimit(CANSparkMax motor){
      String[] array = SmartDashboard.getStringArray("dataTable", null);
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        int currLimit = (Integer.parseInt(motorArray[8]));
        motorList.get(i).setCurrentLimit(motor, currLimit);
      }
  }
    
    public static void setEncoderLimit(CANSparkMax motor){
      String[] array = SmartDashboard.getStringArray("dataTable", null);
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        double lowLimit = (Double.parseDouble(motorArray[9]));
        double highLimit = (Double.parseDouble(motorArray[10]));     
        motorList.get(i).setEncoderLimit(motor, lowLimit, highLimit);
      }
    }

    public void registerMotor(MotorTestInternalSpark motorInternalSpark, String string, String string2,
        String string3) {
        motorList.add(motorInternalSpark);
    }
}
