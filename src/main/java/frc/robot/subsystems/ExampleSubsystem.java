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

import java.util.ArrayList;

import com.revrobotics.CANSparkMax;

public class ExampleSubsystem extends SubsystemBase {
  //private TalonFX driveMotor;
  //WPI_TalonFX motor = new WPI_TalonFX(11);

  public static ArrayList<Integer> motorList = new ArrayList<Integer>() {
    {
      add(20);
      add(7);
      add(2);
    }
  };
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem(){ // ids for neoSwerve: 5,4,10
    MotorTest motorTest = MotorTest.getInstance();
    for (int i = 0; i < motorList.size(); i++) {
      CANSparkMax motor = (new CANSparkMax(motorList.get(i), CANSparkMax.MotorType.kBrushless));
      motorTest.registerMotor(new Neo(motor), "subsytem0", "motor" + i, motor.getDeviceId(), i);
    }
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
