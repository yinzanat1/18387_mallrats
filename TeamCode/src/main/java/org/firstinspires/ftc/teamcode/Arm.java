package org.firstinspires.ftc.teamcode;

    /*
        properties:
            private rightArm dcmotor
            private leftArm dcmotor
            private rightStart integer
            private leftStart integer

        methods:
            void moveArm (targetPosition)
            void rotateArm (direction, times)  // -1 is non stop, 0 to stop
            double getCurrentPosition ()
            boolean atTarget ()
     */

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {
    private DcMotorEx theArm;
    private int startPos = 0;
    private static double oneDegree = 1120.0/360; //1 degrees = 3.1111

    public Arm (DcMotorEx armIn) {
        theArm = armIn;
        startPos = theArm.getCurrentPosition();
    }

    public void moveArm (double targetAngle) {
        //one rotation = 1120 ticks in the woods
        theArm.setTargetPosition ((int)(targetAngle*oneDegree));
        theArm.setPower (0.2*Math.signum (theArm.getTargetPosition() - theArm.getCurrentPosition()));
    }
    public void rotateArm (int directionMultiplier, double rotationTimes) {
        theArm.setTargetPosition((int)(1120*directionMultiplier*rotationTimes));
        theArm.setPower(directionMultiplier);
    }
    public double getCurrentAngle () {
        return (double)(theArm.getCurrentPosition()/oneDegree);
    }
    public boolean atTarget () {
        if (Math.signum(theArm.getPower()) == Math.signum (theArm.getCurrentPosition() - theArm.getTargetPosition())){
            theArm.setPower (0);
            return true;
        }
        return false;
    }
    public boolean atTargetPrecision () {
        theArm.setPower ((theArm.getTargetPosition() - theArm.getCurrentPosition())/560.0);
        return 0==theArm.getPower();
    }
    public void setArmPower (double inPower) {
        theArm.setPower (inPower);
    }
}

