package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TestRobot extends LinearOpMode{
    private DcMotorEx rightMotor;
    private DcMotorEx leftMotor;
    private DcMotorEx rightArmMotor;
    private DcMotorEx leftArmMotor;
    private ColorSensor theColorinator;

    private DcMotorEx[] leftMotorArray = new DcMotorEx[1];
    private DcMotorEx[] rightMotorArray = new DcMotorEx[1];
    private Arm rightArm;
    private Arm leftArm;
    @Override
    public void runOpMode() {
        rightMotorArray[0] = hardwareMap.get(DcMotorEx.class, "rightMotor");
        leftMotorArray[0] = hardwareMap.get(DcMotorEx.class, "leftMotor");
        leftMotorArray[0].setDirection(DcMotorSimple.Direction.REVERSE);
    /*    rightMotorArray[1] = hardwareMap.get(DcMotorEx.class, "right1");
        leftMotorArray[1] = hardwareMap.get(DcMotorEx.class, "left1");
        rightMotorArray[1].setDirection(DcMotorSimple.Direction.REVERSE);
    */
        DriveTrain myDrive = new DriveTrain(rightMotorArray, leftMotorArray, 1);
        /* */
        rightArmMotor = hardwareMap.get(DcMotorEx.class, "rightArm");
        leftArmMotor = hardwareMap.get(DcMotorEx.class, "leftArm");

        rightArm = new Arm(rightArmMotor);
        leftArm = new Arm(leftArmMotor);

        theColorinator = hardwareMap.get(ColorSensor.class, "sensorColorRange");

        float rightBumperPressed = 0;
        float leftBumperPressed = 0;

        TheDomino domino = new TheDomino(theColorinator);
        telemetry.addData("arm", rightArmMotor.getCurrentPosition());
        telemetry.update();
        /* */
        waitForStart();
        telemetry.speak("Let's Party!!");
        telemetry.update();

        //rightArm.moveArm(90);
        while (opModeIsActive()) {
            //float powerRightY, float powerLeftY, float powerRightX, float powerLeftX
            myDrive.setDrivePower(gamepad1.right_stick_y, gamepad1.left_stick_y,gamepad1.right_stick_x, gamepad1.left_stick_x);
            if (gamepad1.right_bumper) {
                rightBumperPressed = 1;
            } else {
                rightBumperPressed = 0;
            }
            if (gamepad1.left_bumper) {
                leftBumperPressed = 1;
            } else {
                leftBumperPressed = 0;
            }
            rightArm.setArmPower(-0.5*(gamepad1.right_trigger - rightBumperPressed));
            leftArm.setArmPower(-0.5*(gamepad1.left_trigger - leftBumperPressed));
            //rightArm.atTargetPrecision();
            telemetry.update();
            if (isStopRequested()) {
                telemetry.speak("party's over");
                telemetry.update();
                rightArm.moveArm(0);
                while (!rightArm.atTargetPrecision()) {
                }
            }
        }
    }
}

