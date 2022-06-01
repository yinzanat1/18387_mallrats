package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Date;
import java.util.Random;

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
    private Servo potatoHead;
    private Servo jumpingJacks;
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

        potatoHead = hardwareMap.get(Servo.class, "potatoHead");
        jumpingJacks = hardwareMap.get(Servo.class, "jumpingJacks");

        Boolean dpadUpPressed = false;
        Boolean aPushed = false;
        Boolean yPushed = false;
        String[][] thingsToSay = {
                {"I am a potato", "My name is Squigle", "Hands in the air", "hello", "Bonjour, je m'appelle Squigle, et twah", "Hiya friendo", "Hi everybody", "help i em on fire", "anyone want a high five?"},
                {"that's the point", "Run", "Look Over There. ... Made you look ha ha ha", "Pull my finger ha ha ha", "I am pointing", "Ha ha ha ha ha ha", "I like your eyes"}
        };
        Random rando = new Random();
//rightArmMotor.setTargetPosition(0);
//rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArm = new Arm(rightArmMotor);
        leftArm = new Arm(leftArmMotor);

        theColorinator = hardwareMap.get(ColorSensor.class, "sensorColorRange");

        telemetry.addData("rightMode", rightArmMotor.getMode());
        telemetry.addData("leftMode", leftArmMotor.getMode());

        float rightBumperPressed = 0;
        float leftBumperPressed = 0;
        int leftWave = 0;
        int rightPoint = 0;
        double savedTime = 0;
int franTest = 0;
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
/*
    telemetry.addData takes an object instead of a literal,
    we might be able to use that to write our dance scheduler.
 */


// Mr Fran testing something
/*telemetry.addData("y", gamepad1.y);
telemetry.addData("current", rightArmMotor.getCurrentPosition());
telemetry.addData("target", rightArmMotor.getTargetPosition());
telemetry.addData("FranTest", franTest);
telemetry.update();
if (gamepad1.y) {
    if (franTest == 0) {
        franTest = 1;
        if (rightArmMotor.getTargetPosition() > 90){
            rightArmMotor.setTargetPosition(0);
        } else {
            rightArmMotor.setTargetPosition(280);
        }
        rightArmMotor.setPower(0.5);
    }
} else {
    franTest = 0;
}*/
// done Mr Fran code
            rightArm.setArmPower(-0.5*(gamepad1.right_trigger - rightBumperPressed));
            leftArm.setArmPower(-0.5*(gamepad1.left_trigger - leftBumperPressed));
            if (gamepad1.x && leftWave == 0) {
                leftWave = 1;
                telemetry.speak(thingsToSay[0][rando.nextInt(thingsToSay[0].length)]);
                telemetry.update();
            }
            if (leftWave % 2 == 1) {
                leftArm.moveArm(179);
                if (leftArm.atTargetPrecision()) {
                    leftWave++;
                }
            }
            if (leftWave == 2 || leftWave == 4) {
                leftArm.moveArm(145);
                if (leftArm.atTargetPrecision()) {
                    leftWave++;
                }
            }
            if (leftWave == 6) {
                leftArm.moveArm(0);
                if (leftArm.atTargetPrecision()) {
                    leftWave = 0;
                }
            }

            if (gamepad1.b && rightPoint == 0) {
                //telemetry.speak("b Pressed");
                telemetry.update();
                rightPoint = 1;
            }
            if (rightPoint == 1) {
                //rightArm.moveArm(90);
                telemetry.addData("CURRENT angle", rightArm.getCurrentAngle());
                telemetry.addData("CURRENT position", rightArm.getCurrentAngle() * (1120.0/360));
                telemetry.addData("TARGET position", rightArm.moveArm2(90));
                telemetry.update();
                rightPoint++;
            }
            telemetry.addData("POWAH", rightArmMotor.getPower() * 10000.0);
            franTest++;
            telemetry.addData("fran", franTest);
            telemetry.update();
            if (rightPoint == 2 && rightArm.atTargetPrecision()) {
                telemetry.speak(thingsToSay[1][rando.nextInt(thingsToSay[1].length)]);
                //telemetry.speak("That's the point");
                telemetry.update();
                rightPoint++;
                savedTime = System.currentTimeMillis();
            }

            if (rightPoint == 3 && System.currentTimeMillis() - 1500 > savedTime) {
                //rightArm.moveArm(0);
                //telemetry.speak(Integer.toString(rightArm.moveArm2(0)));
                telemetry.addData("CURRENT angle", rightArm.getCurrentAngle());
                telemetry.addData("CURRENT position", rightArm.getCurrentAngle() * (1120.0 / 360));
                telemetry.addData("TARGET position", rightArm.moveArm2(0));
                telemetry.update();
                rightPoint++;
            }

            if (rightPoint == 4 && rightArm.atTargetPrecision()) {
                rightPoint = 0;
            }

            potatoHead.setPosition((gamepad1.right_stick_y/2)+0.5);

            if (gamepad1.dpad_up) {
                if (!dpadUpPressed) {
                    dpadUpPressed = true;
                    jumpingJacks.setPosition(1 - jumpingJacks.getPosition());
                }
            } else {
                dpadUpPressed = false;
            }

            if (gamepad1.a) {
                if (!aPushed) {
                    aPushed = true;
                    telemetry.speak(domino.getHue().toString());
                }
            } else {
                aPushed = false;
            }
           /* if (gamepad1.y) {
                if (!yPushed) {
                    yPushed = true;
                    if (danceStep == 0) {
                        telemetry.speak(domino.getColorNumber().toString());
                    } else {
                        danceStep = 0;
                    }
                }
            } else {
                yPushed = false;
            } */
//set dance step to 0 to stop dance
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

