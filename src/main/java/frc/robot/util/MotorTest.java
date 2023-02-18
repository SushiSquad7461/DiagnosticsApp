package frc.robot.util;

import edu.wpi.first.networktables.*;
import frc.robot.util.MotorTest;
import frc.robot.util.Motor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revrobotics.REVLibError;

public class MotorTest {
  private NetworkTableInstance inst;
  private NetworkTable table;

  private String[] tableArray;
  private String[] motorArray;

  private StringArraySubscriber dataTable;
  private StringArrayPublisher motorTable;
  private BooleanSubscriber running;
  private REVLibError errorChecker;
  
  static MotorTest instance;
  private List<Motor> motorList;

  private int counter;

  public static MotorTest getInstance() {
    if (instance == null){
      instance = new MotorTest();
    }
    return instance;
  }

    public MotorTest() {
      inst = NetworkTableInstance.getDefault();
      table = inst.getTable("dataTable");
      dataTable = table.getStringArrayTopic("tableValues").subscribe(null);
      running = table.getBooleanTopic("Running?").subscribe(false);
      tableArray = dataTable.get();

      motorTable = table.getStringArrayTopic("motors").publish();
      motorArray = new String[2]; //make variable, constant

      instance = null;
      motorList = new ArrayList<Motor>();

      counter = 0;
    }

    public void updateMotors() {
      tableArray = dataTable.get();

      
      if (running.get()) {
        for (int i = 0; i < motorList.size(); i++) {
        System.out.println(tableArray[i]);
          if (tableArray.length > i && !tableArray[i].equals("containsNull")) {
            coastOrBrake(i);
            invertMotor(i);
            setCurrentLimit(i);
            setEncoderLimit(i);
            setSpeed(i);
          } else {
            motorList.get(i).disable();
          }
        }

      }
      else {
        isStop(tableArray);
      }
    }

    public void isStop(String[] tableArray){
      for (int i = 0; i < motorList.size(); i++){
        stopMotors(motorList.get(i));
      }
    }

    public void coastOrBrake(int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      if ((motorArray[6]) == "false"){
        motorList.get(idx).setIdle(Motor.IdleMode.BRAKE);
      } else {
        motorList.get(idx).setIdle(Motor.IdleMode.COAST);
      }
    }

    public void invertMotor(int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      boolean isInverted = Boolean.parseBoolean(motorArray[7]);
      motorList.get(idx).invertMotor(isInverted);
    }
    
    public void setSpeed(int idx) {
      if (tableArray[idx] != null) {
        String[] motorArray = (tableArray[idx]).split(" ");
        double constSpeed = (Double.parseDouble(motorArray[4]));
        double joystickSpeed = (Double.parseDouble(motorArray[5]));
        double joystickVal = 1;
        motorList.get(idx).setSpeed(constSpeed + joystickVal * joystickSpeed);
      } else {
        motorList.get(idx).disable();
      }
    }
    
    public void stopMotors(Motor motor){
      motor.disable();
    }

    public void setCurrentLimit(int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      int currLimit = (int)(Double.parseDouble(motorArray[8]));
      motorList.get(idx).setCurrentLimit(currLimit);
  }
    public void setEncoderLimit(int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      double low = (Double.parseDouble(motorArray[9]));
      double high = (Double.parseDouble(motorArray[10]));     
      motorList.get(idx).setEncoderLimit(low, high);
    }

    public void registerMotor(Motor motor, String subsystem, String motorName, int id, int pdhPort) {
        motorList.add(motor);
        motorArray[counter] = subsystem + " " + motorName + " " + id + " " + pdhPort + " 0.0 0.0 0 0 0.0 0.0 0.0 0";
        counter++;
        System.out.println(Arrays.toString(motorArray));
        motorTable.set(motorArray);
    }

}
