package org.usfirst.frc.team5541.robot;

import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick driver;
	Joystick shooter;
	int autoLoopCounter;
	public double shooterValue;
	Compressor compressor = new Compressor();
	Victor shooterWheel;
	CANTalon shooterTalon;
	Solenoid shooterSolenoid;
	Solenoid clawSolenoid;
	Solenoid pivotSolenoid;
	//Servo shooterServo;
	//boolean aButtonValue;
	//boolean bButtonValue;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
public void robotInit() {
		shooterSolenoid = new Solenoid(1, 2);
		clawSolenoid = new Solenoid(1, 0);
		pivotSolenoid = new Solenoid(1, 1);
		compressor = new Compressor(1);
		//CameraServer cams = CameraServer.getInstance();
		//cams.startAutomaticCapture("cam0");
    	driver = new Joystick(0);
    	shooter = new Joystick(1);
    	CANTalon rightTalon = new CANTalon(1);
    	CANTalon rightSlaveTalon = new CANTalon(2);
    	CANTalon leftTalon = new CANTalon(3);
    	CANTalon leftSlaveTalon = new CANTalon(4);
    	shooterTalon = new CANTalon(5);
    	shooterWheel = new Victor(0);
    	rightTalon.setSafetyEnabled(true);
    	rightSlaveTalon.setSafetyEnabled(true);
    	leftTalon.setSafetyEnabled(true);
    	leftSlaveTalon.setSafetyEnabled(true);
    	rightTalon.setExpiration(.1);
    	rightSlaveTalon.setExpiration(.1);
    	leftTalon.setExpiration(.1);
    	leftSlaveTalon.setExpiration(.1);
    	myRobot = new RobotDrive(rightTalon, rightSlaveTalon, leftTalon, leftSlaveTalon);
    	myRobot.setSensitivity(.75);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 150) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(0.75, 0.75); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /*
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	compressor.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//right is not reversed 
        myRobot.tankDrive(-(driver.getRawAxis(1)), (driver.getRawAxis(5)));
        shooterValue = (0.0);
        shooterWheel.set((shooter.getRawAxis(5)));
        shooterTalon.set(shooter.getRawAxis(1));
        if(shooter.getRawButton(1)){
        	shooterSolenoid.set(true);
        } else {
        	shooterSolenoid.set(false);
        }
        if(shooter.getRawButton(2)){
        	pivotSolenoid.set(true);
        } else if(shooter.getRawButton(3)){
        	pivotSolenoid.set(false);
        }
        if(shooter.getRawButton(5)){
        	clawSolenoid.set(true);
        } else if(shooter.getRawButton(6)) {
        	clawSolenoid.set(false);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
