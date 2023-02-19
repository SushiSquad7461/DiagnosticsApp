// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.hal.can.CANMessageNotFoundException;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Motor;
import frc.robot.util.MotorTest;
//import frc.robot.util.MotorTestInternalSpark;
import frc.robot.util.Neo;

import com.revrobotics.CANSparkMax;

public class ExampleSubsystem extends SubsystemBase {
  //private TalonFX driveMotor;
  //WPI_TalonFX motor = new WPI_TalonFX(11);
  public CANSparkMax mod1Drive;
  public CANSparkMax mod2Drive;
  public CANSparkMax mod3Drive;

  Neo neo1;
  Neo neo2;
  Neo neo3;
  // when adding new motors go to motortest, and change numMotors to the correct number of motors

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem(){ // ids for neoSwerve: 5,4,10
    try {
      mod1Drive = new CANSparkMax(5, CANSparkMax.MotorType.kBrushless);
      mod2Drive = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);
      mod3Drive = new CANSparkMax(10, CANSparkMax.MotorType.kBrushless);
    } catch (CANMessageNotFoundException e) {
      System.out.println("Wrong motor id");
    }
    

    neo1 = new Neo(mod1Drive);
    neo2 = new Neo(mod2Drive);
    neo3 = new Neo(mod3Drive);

    MotorTest motorTest = MotorTest.getInstance();
    motorTest.registerMotor(neo1, "subsystem0", "motor1", neo1.canID, 0);
    motorTest.registerMotor(neo2, "subsystem0", "motor2", neo2.canID, 1);
    motorTest.registerMotor(neo3, "subsystem0", "motor2", neo3.canID, 2);

  }

  /**
   * Example command factory method.
   
   * @return a command
   */
  @Override
  public void periodic() {
    //neo1.setSpeed(0.1);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
