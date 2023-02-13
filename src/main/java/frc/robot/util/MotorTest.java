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
  private NetworkTableInstance inst;
  private NetworkTable table;
  private String[] tableArray;
  private StringArraySubscriber dataTable;
  private BooleanSubscriber changed;
  private BooleanSubscriber running;
  static MotorTest instance;
  private List<MotorTestInternalSpark> motorListSpark;
  private List<MotorTestInternalFalcon> motorListFalcon;

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
      changed = table.getBooleanTopic("Changed?").subscribe(false);
      running = table.getBooleanTopic("Running?").subscribe(false);
      tableArray = dataTable.get();

      instance = null;
      motorListSpark = new ArrayList<MotorTestInternalSpark>();
      motorListFalcon = new ArrayList<MotorTestInternalFalcon>();

    }

    public void isUpdatedSpark(){
      if (changed.get()){ 
        tableArray = dataTable.get();
        for (int i = 0; i < motorListSpark.size(); i++){
          coastOrBrake(motorListSpark.get(i).motor, i);
          invertMotor(motorListSpark.get(i).motor, i);
          setCurrentLimit(motorListSpark.get(i).motor, i);
          setEncoderLimit(motorListSpark.get(i).motor, i);
        }
      }
    }

    public void isUpdatedFalcon(){
      if (changed.get()){
        tableArray = dataTable.get();
        for (int i = 0; i < motorListSpark.size(); i++){
          coastOrBrake(motorListFalcon.get(i).motor, i);
          invertMotor(motorListFalcon.get(i).motor, i);
          setCurrentLimit(motorListFalcon.get(i).motor, i);
          setEncoderLimit(motorListFalcon.get(i).motor, i);
        }
      }
    }

    public void isRun(){
      if (running.get()){
        tableArray = dataTable.get();
        for (int i = 0; i < motorListSpark.size(); i++){
          setSpeed(motorListSpark.get(i).motor, i);
          System.out.println("spinning");
        }
        for (int i = 0; i < motorListFalcon.size(); i++){
          setSpeed(motorListFalcon.get(i).motor, i);
        }
      }
    }

    public void isStop(){
      if (!(running.get())){
        for (int i = 0; i < motorListSpark.size(); i++){
          stopMotors(motorListSpark.get(i).motor);
        }
        for (int i = 0; i < motorListFalcon.size(); i++){
          stopMotors(motorListFalcon.get(i).motor);
        }
      }
    }

    public void coastOrBrake(CANSparkMax motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");

      IdleMode isCoast;
      if ((motorArray[6]) == "false"){
        isCoast = IdleMode.kBrake;
      } else {
        isCoast = IdleMode.kCoast;
      }
      motorListSpark.get(idx).setIdleMode(motor, isCoast);
    }

    public void coastOrBrake(WPI_TalonFX motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      NeutralMode isCoast;
      if ((motorArray[6]) == "false"){
        isCoast = NeutralMode.Brake;
      } else {
        isCoast = NeutralMode.Coast;
      }
      motorListFalcon.get(idx).setIdleMode(motor, isCoast);
    }
    

    public void invertMotor(CANSparkMax motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      boolean isInverted = motorArray[7] == "false";
      motorListSpark.get(idx).invertMotor(motor, isInverted);
    }
    public void invertMotor(WPI_TalonFX motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      boolean isInverted = motorArray[7] == "false";
      motorListFalcon.get(idx).invertMotor(motor, isInverted);
    }

    public void setSpeed(CANSparkMax motor, int idx) {
      if (tableArray[idx].getClass() != null) {
        String[] motorArray = (tableArray[idx]).split(" ");
        double constSpeed = (Double.parseDouble(motorArray[4]));
        double joystickSpeed = (Double.parseDouble(motorArray[5]));
        double joystickVal = 1;
        motorListSpark.get(idx).setSpeed(motor, constSpeed + joystickVal * joystickSpeed);
      } else {
        motorListSpark.get(idx).motor.disable();
      }
    }

    public void setSpeed(WPI_TalonFX motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      double constSpeed = (Double.parseDouble(motorArray[4]));
      double joystickSpeed = (Double.parseDouble(motorArray[5]));
      double joystickVal = 1;
      motorListFalcon.get(idx).setSpeed(motor, constSpeed + joystickVal * joystickSpeed);
  }
    
    public void stopMotors(CANSparkMax motor){
      motor.disable();
    }

    public void stopMotors(WPI_TalonFX motor){
      motor.disable();
    }

    public void setCurrentLimit(CANSparkMax motor, int idx){
      for (int i = 0; i < tableArray.length; i++){
        String[] motorArray = (tableArray[i]).split(" ");
        int currLimit = (int)(Double.parseDouble(motorArray[8]));
        motorListSpark.get(i).setCurrentLimit(motor, currLimit);
      }
  }

  public void setCurrentLimit(WPI_TalonFX motor, int idx){
    String[] motorArray = (tableArray[idx]).split(" ");
    int currLimit = (int)(Double.parseDouble(motorArray[8]));
    motorListFalcon.get(idx).setCurrentLimit(motor, currLimit);
}
    
    public void setEncoderLimit(CANSparkMax motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      double lowLimit = (Double.parseDouble(motorArray[9]));
      double highLimit = (Double.parseDouble(motorArray[10]));     
      motorListSpark.get(idx).setEncoderLimit(motor, lowLimit, highLimit);
    }
    public void setEncoderLimit(WPI_TalonFX motor, int idx){
      String[] motorArray = (tableArray[idx]).split(" ");
      double lowLimit = (Double.parseDouble(motorArray[9]));
      double highLimit = (Double.parseDouble(motorArray[10]));     
      motorListFalcon.get(idx).setEncoderLimit(motor, lowLimit, highLimit);
    }

    public void registerSparkMotor(MotorTestInternalSpark motorInternalSpark) {
        motorListSpark.add(motorInternalSpark);
        System.out.println(motorListSpark.size()+"hey");
    }
    public void registerFalconMotor(MotorTestInternalFalcon motorInternalFalcon) {
      motorListFalcon.add(motorInternalFalcon);
  }
}
