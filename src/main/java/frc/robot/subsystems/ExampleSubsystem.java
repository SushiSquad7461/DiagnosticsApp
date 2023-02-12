// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.util.MotorTest;
import frc.robot.util.MotorTestInternalSpark;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;

public class ExampleSubsystem extends SubsystemBase {
  //private TalonFX driveMotor;
  //WPI_TalonFX motor = new WPI_TalonFX(11);
  public CANSparkMax mod1Drive;
  public CANSparkMax mod2Drive;
  public CANSparkMax mod3Drive;
  public CANSparkMax mod4Drive;


  MotorTestInternalSpark motorInternalSpark1;
  MotorTestInternalSpark motorInternalSpark2;
  MotorTestInternalSpark motorInternalSpark3;
  MotorTestInternalSpark motorInternalSpark4;
  // static ExampleSubsystem instance = null;

  // public static ExampleSubsystem GetInstance(){
  //   if (instance == null){
  //     instance = new ExampleSubsystem();
  //   }
  //   return instance;
  // }

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem(){
    mod1Drive = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless); //id is correct
    mod2Drive = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless); //check id later
    // mod3Drive = new CANSparkMax(7, CANSparkMax.MotorType.kBrushless); //check id later
    // mod4Drive = new CANSparkMax(10, CANSparkMax.MotorType.kBrushless); //check id later

    motorInternalSpark1 = new MotorTestInternalSpark(mod1Drive);
    motorInternalSpark2 = new MotorTestInternalSpark(mod2Drive);
    // motorInternalSpark3 = new MotorTestInternalSpark(mod3Drive);
    // motorInternalSpark4 = new MotorTestInternalSpark(mod4Drive);
    
    MotorTest motorTest = MotorTest.getInstance();
    motorTest.registerSparkMotor(motorInternalSpark1);
    motorTest.registerSparkMotor(motorInternalSpark2);
    // motorTest.registerSparkMotor(motorInternalSpark3);
    // motorTest.registerSparkMotor(motorInternalSpark4);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
