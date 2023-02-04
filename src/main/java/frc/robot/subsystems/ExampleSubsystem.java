// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.MotorTest;
import frc.robot.util.MotorTestInternalSpark;

import java.util.List;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.util.MotorTest;

import SushiFrcLib.Motor.MotorHelper;

public class ExampleSubsystem extends SubsystemBase {
  //private TalonFX driveMotor;
  //WPI_TalonFX motor = new WPI_TalonFX(11);
  CANSparkMax mod1Drive;
  CANSparkMax mod2Drive;
  CANSparkMax mod3Drive;
  CANSparkMax mod4Drive;
  MotorTestInternalSpark motorInternalSpark1;
  MotorTestInternalSpark motorInternalSpark2;
  MotorTestInternalSpark motorInternalSpark3;
  MotorTestInternalSpark motorInternalSpark4;
  MotorTest motorTest;
  ExampleSubsystem instance = null;

  public ExampleSubsystem GetInstance(){
    if (instance == null){
      instance = new ExampleSubsystem();
    }
    return instance;
  }

  /** Creates a new ExampleSubsystem. */
  private ExampleSubsystem(){
    mod1Drive = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
    mod2Drive = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);
    mod3Drive = new CANSparkMax(7, CANSparkMax.MotorType.kBrushless);
    mod4Drive = new CANSparkMax(10, CANSparkMax.MotorType.kBrushless);

    motorInternalSpark1 = new MotorTestInternalSpark(mod1Drive);
    motorInternalSpark2 = new MotorTestInternalSpark(mod2Drive);
    motorInternalSpark3 = new MotorTestInternalSpark(mod3Drive);
    motorInternalSpark4 = new MotorTestInternalSpark(mod4Drive);
    
    motorTest = MotorTest.GetInstance();
    motorTest.registerMotor(motorInternalSpark1, "motor1", "canID", "pdhPort");
    motorTest.registerMotor(motorInternalSpark2, "motor2", "canID", "pdhPort");
    motorTest.registerMotor(motorInternalSpark3, "motor3", "canID", "pdhPort");
    motorTest.registerMotor(motorInternalSpark4, "motor4", "canID", "pdhPort");

  }

  /**
   * Example command factory method.
   *
   * @return a command
   */

   // Falcons
  public void setSpeed(WPI_TalonFX motor, double speed){
    motor.set(speed);
  }

  public void coastOrBrake(WPI_TalonFX motor, int num){
    if(num==1){
      motor.setNeutralMode(NeutralMode.Brake);
    }
    else{
      motor.setNeutralMode(NeutralMode.Coast);
    }
  }

  // public void flipMotor(Boolean flipped){
  //   motor.set(speed*-1);
  // }

  public void flipEncoder(WPI_TalonFX motor, Boolean flipped){
    motor.setInverted(flipped);

  }

  public void setCurrentLimit(WPI_TalonFX motor, double currentLimit){
    SupplyCurrentLimitConfiguration CurrentLimit = new SupplyCurrentLimitConfiguration(true, currentLimit, currentLimit,0.0);
    motor.configSupplyCurrentLimit(CurrentLimit);
    
  }

  public void setEncoderLimit(WPI_TalonFX motor, double lowLimit, double highLimit){
    if (motor.getSelectedSensorPosition()<lowLimit || motor.getSelectedSensorPosition()>highLimit){
      motor.disable();
    }
  }


  @Override
  public void periodic() {
    SmartDashboard.putNumber("Motor Speed", mod1Drive.get());
  }

  public void runMotor(CANSparkMax motor, List<String> inputs){
      MotorTest.setSpeed(motor, Double.parseDouble(inputs.get(0)));
      MotorTest.coastOrBrake(motor, Integer.parseInt(inputs.get(1)));
      MotorTest.invertMotor(motor,inputs.get(2).equals("1"));
      MotorTest.setCurrentLimit(motor, Integer.parseInt(inputs.get(3)) );
      MotorTest.setEncoderLimit(motor, Integer.parseInt(inputs.get(4)), Integer.parseInt(inputs.get(5)));
  }

  public void newTestInput(List<String> inputs) {
    CANSparkMax testMotor;

    System.out.println(inputs);

    switch (inputs.get(0)) {
      case "mod1Drive":
        testMotor = mod1Drive;
        break;
      default:
        testMotor = null;
        break;
    }

    if (testMotor != null) {
      runMotor(testMotor, inputs.subList(1, inputs.size()));
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
