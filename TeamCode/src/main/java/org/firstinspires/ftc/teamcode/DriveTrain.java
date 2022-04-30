package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import java.util.Timer;
import java.util.TimerTask;

public class DriveTrain {
        // properties of the DriveTrain class
    private DcMotorEx[] rightMotor;
    private DcMotorEx[] leftMotor;
    private int driveType;
    private float wheelCircumference = 11.142f; // 90mm diameter wheels circumference in inches
    private float gearRatio = 40;

    private double leftPower;
    private double rightPower;

    public boolean timerOn = false;

            // constructor
    public DriveTrain (DcMotor motor1, DcMotor motor2) {
        rightMotor[0] = (DcMotorEx)motor1;
        leftMotor[0] = (DcMotorEx)motor2;
        driveType = 0;
    }
    public DriveTrain (DcMotor motor1, DcMotor motor2, int driveTypeIn) {
        rightMotor[0] = (DcMotorEx)motor1;
        leftMotor[0] = (DcMotorEx)motor2;
        driveType = driveTypeIn;
    }
    public DriveTrain (DcMotor[] motor1, DcMotor[] motor2, int driveTypeIn) {
        rightMotor = new DcMotorEx[motor1.length];
        leftMotor = new DcMotorEx[motor2.length];
        for (int ii = 0; ii < motor1.length; ii++) {
            rightMotor[ii] = (DcMotorEx) motor1[ii];
        }
        for (int ii = 0; ii < motor2.length; ii++) {
            leftMotor[ii] = (DcMotorEx) motor2[ii];
        }
        driveType = driveTypeIn;
    }
            // method to drive the robot
    public void setDrivePower (float powerRightY, float powerLeftY, float powerRightX, float powerLeftX) {
        if (driveType == 0) {
            // Tank Drive
            rightPower = powerRightY;
            leftPower = powerLeftY;
        } else if (driveType == 1) {
            //Arcade Drive
            rightPower = powerLeftY + powerLeftX;
            leftPower = powerLeftY - powerLeftX;
        } else if (driveType == 2) {
            // ZK - 2/12/2022 - Zacharian Hybrid Drive (Weird)
            rightPower = powerRightY + powerRightX;
            leftPower = powerLeftY - powerLeftX;
        } else {
            //Default to Tank Drive
            rightPower = powerRightY;
            leftPower = powerLeftY;
        }
        setMotorPowers();
    }

    public void setDriveTime (float driveForTime, float drivePower, int timeDrivetype) {
//        Thread.sleep((long) (driveForTime * 1000));

        Timer timer = new Timer();
        timerOn = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerOn = false;
                rightPower = 0;
                leftPower = 0;
                setMotorPowers();
            }
        }, (long) (driveForTime * 1000));
        rightPower = drivePower;
        leftPower = drivePower;
        setMotorPowers();

    }
    public void driveByRevolution (float distance) {
        for (int ii = 0; ii < rightMotor.length; ii++) {
            rightMotor[ii].setTargetPosition(Math.round(rightMotor[ii].getCurrentPosition() + (distance / wheelCircumference) * 28 * gearRatio));
        }
        for (int ii = 0; ii < leftMotor.length; ii++) {
            leftMotor[ii].setTargetPosition(Math.round(leftMotor[ii].getCurrentPosition() + (distance / wheelCircumference) * 28 * gearRatio));
        }
        rightPower = 0.5;
        leftPower = 0.5;
        setMotorPowers();
        checkPosition();
    }

    private void checkPosition () {
        for (int ii = 0; ii < rightMotor.length; ii++) {
        if (rightMotor[ii].getTargetPosition() < rightMotor[ii].getCurrentPosition() || leftMotor[ii].getTargetPosition() < leftMotor[ii].getCurrentPosition()) {
            rightPower = 0;
            leftPower = 0;
            setMotorPowers();
        } else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    checkPosition();
                }
            }, 10);
        }
        }
    }

    private void setMotorPowers () {
        for (int ii = 0; ii < rightMotor.length; ii++) {
            rightMotor[ii].setPower(rightPower);
        }
        for (int ii = 0; ii < leftMotor.length; ii++) {
            leftMotor[ii].setPower (leftPower);
        }
    }
/*      FWF - this is causing an error on init of the drive train.
    int numberOfTests = 0;
    double currentAverageRPM = 0;
    double storedPosition = rightMotor[0].getCurrentPosition();
    double currentDistanceMoved = 0;
    public double getMaxRPMs () {
        return 123684;
        / *
        rightPower = 1;
        leftPower = 1;
        setMotorPowers();
        while (numberOfTests < 10){
            currentAverageRPM = ((currentAverageRPM * numberOfTests) + rightMotor[0].getVelocity()) / (numberOfTests + 1);
            numberOfTests++;
        }
        rightPower = 0;
        leftPower = 0;
        setMotorPowers();
        return currentAverageRPM; * /
    }
*/
}
