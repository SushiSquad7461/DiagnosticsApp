package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringArraySubscriber;
import frc.robot.util.MotorTest;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax.IdleMode;


public class MotorTest {
  private static NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private static NetworkTable table = inst.getTable("dataTable");
  private static StringArraySubscriber dataTable = table.getStringArrayTopic("tableValues").subscribe(null);
  private static BooleanSubscriber changed = table.getBooleanTopic("Changed?").subscribe(false);
  private static BooleanSubscriber running = table.getBooleanTopic("Running?").subscribe(false);

  public void run(){
    inst.startClient4("systems-check");
    inst.setServerTeam(7461);
    inst.setServer("systems-check", NetworkTableInstance.kDefaultPort4);
    inst.startDSClient();

    while (true){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex){
        System.out.println("interrupted");
        return;
      }
      
    }
  }

  static MotorTest instance = null;
  static private List<MotorTestInternalSpark> motorListSpark = new ArrayList<MotorTestInternalSpark>();
  static private List<MotorTestInternalFalcon> motorListFalcon = new ArrayList<MotorTestInternalFalcon>();

  public static MotorTest GetInstance(){
    if (instance == null){
      instance = new MotorTest();
    }
    return instance;
  }

    public MotorTest(){

    }

    public void isUpdatedSpark(){
      if (changed.get()){ //this works
        for (int i = 0; i < motorListSpark.size(); i++){
          coastOrBrake(motorListSpark.get(i).motor);
          invertMotor(motorListSpark.get(i).motor);
          setCurrentLimit(motorListSpark.get(i).motor);
          setEncoderLimit(motorListSpark.get(i).motor);
        }
      }
    }

    public void isUpdatedFalcon(){
      if (changed.get()){
        for (int i = 0; i < motorListSpark.size(); i++){
          coastOrBrake(motorListFalcon.get(i).motor);
          invertMotor(motorListFalcon.get(i).motor);
          setCurrentLimit(motorListFalcon.get(i).motor);
          setEncoderLimit(motorListFalcon.get(i).motor);
        }
      }
    }

    public void isRun(){
      if (running.get()){
        for (int i = 0; i < motorListSpark.size(); i++){
          setSpeed(motorListSpark.get(i).motor);
        }
        for (int i = 0; i < motorListFalcon.size(); i++){
          setSpeed(motorListFalcon.get(i).motor);
        }
      }
    }

    public void isStop(){
      if (!running.get()){
        for (int i = 0; i < motorListSpark.size(); i++){
          stopMotors(motorListSpark.get(i).motor);
        }
        for (int i = 0; i < motorListFalcon.size(); i++){
          stopMotors(motorListFalcon.get(i).motor);
        }
      }
    }

    public static void coastOrBrake(CANSparkMax motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        IdleMode isCoast;
        if ((motorArray[6]) == "false"){
          isCoast = IdleMode.kBrake;
        } else {
          isCoast = IdleMode.kCoast;
        }
        motorListSpark.get(i).setIdleMode(motor, isCoast);
      }
    }

    public static void coastOrBrake(WPI_TalonFX motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        NeutralMode isCoast;
        if ((motorArray[6]) == "false"){
          isCoast = NeutralMode.Brake;
        } else {
          isCoast = NeutralMode.Coast;
        }
        motorListFalcon.get(i).setIdleMode(motor, isCoast);
      }
    }
    

    public static void invertMotor(CANSparkMax motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        boolean isInverted = motorArray[7] == "false";
        motorListSpark.get(i).invertMotor(motor, isInverted);
      }
    }
    public static void invertMotor(WPI_TalonFX motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        boolean isInverted = motorArray[7] == "false";
        motorListFalcon.get(i).invertMotor(motor, isInverted);
      }
    }

    public static void setSpeed(CANSparkMax motor){
      String[] array = dataTable.get();
        for (int i = 0; i < array.length; i++){
          String[] motorArray = (array[i]).split(" ");
          double constSpeed = (Double.parseDouble(motorArray[4]));
          double joystickSpeed = (Double.parseDouble(motorArray[5]));
          double joystickVal = 1;
          motorListSpark.get(i).setSpeed(motor, constSpeed + joystickVal * joystickSpeed);
        }
    }

    public static void setSpeed(WPI_TalonFX motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        double constSpeed = (Double.parseDouble(motorArray[4]));
        double joystickSpeed = (Double.parseDouble(motorArray[5]));
        double joystickVal = 1;
        motorListFalcon.get(i).setSpeed(motor, constSpeed + joystickVal * joystickSpeed);
      }
  }
    
    public static void stopMotors(CANSparkMax motor){
      motor.disable();
    }

    public static void stopMotors(WPI_TalonFX motor){
      motor.disable();
    }

    public static void setCurrentLimit(CANSparkMax motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        int currLimit = (Integer.parseInt(motorArray[8]));
        motorListSpark.get(i).setCurrentLimit(motor, currLimit);
      }
  }

  public static void setCurrentLimit(WPI_TalonFX motor){
    String[] array = dataTable.get();
    for (int i = 0; i < array.length; i++){
      String[] motorArray = (array[i]).split(" ");
      int currLimit = (Integer.parseInt(motorArray[8]));
      motorListFalcon.get(i).setCurrentLimit(motor, currLimit);
    }
}
    
    public static void setEncoderLimit(CANSparkMax motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        double lowLimit = (Double.parseDouble(motorArray[9]));
        double highLimit = (Double.parseDouble(motorArray[10]));     
        motorListSpark.get(i).setEncoderLimit(motor, lowLimit, highLimit);
      }
    }
    public static void setEncoderLimit(WPI_TalonFX motor){
      String[] array = dataTable.get();
      for (int i = 0; i < array.length; i++){
        String[] motorArray = (array[i]).split(" ");
        double lowLimit = (Double.parseDouble(motorArray[9]));
        double highLimit = (Double.parseDouble(motorArray[10]));     
        motorListFalcon.get(i).setEncoderLimit(motor, lowLimit, highLimit);
      }
    }

    public void registerSparkMotor(MotorTestInternalSpark motorInternalSpark) {
        motorListSpark.add(motorInternalSpark);
        motorInternalSpark.motor.set(0.5);
    }
    public void registerFalconMotor(MotorTestInternalFalcon motorInternalFalcon) {
      motorListFalcon.add(motorInternalFalcon);
  }
}
