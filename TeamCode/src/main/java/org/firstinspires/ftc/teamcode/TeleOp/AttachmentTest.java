package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.ftc.waterloo.h2oloobots.AttachmentControl;

@TeleOp
public class AttachmentTest extends LinearOpMode {

    AttachmentControl attachmentControl = new AttachmentControl();
    boolean isButtonPushed = false;
    boolean hingeManual = false;

    boolean isMarkerServoButtonPushed = false;
    boolean markerManual = false;
    boolean isMarkerManualPushed = false;

    public AttachmentControl.LiftHingePosition liftHingePosition = AttachmentControl.LiftHingePosition.Back;

    boolean MarkerServoUp = false;
    boolean MarkerServoDown = false;

    public void runOpMode() {

        attachmentControl.attachmentInit(hardwareMap, telemetry, null);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_right && !isButtonPushed) {

                if (hingeManual == false) {
                    hingeManual = true;
                } else if (hingeManual == true) {
                    hingeManual = false;

                    attachmentControl.LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    attachmentControl.LiftHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }

                isButtonPushed = true;

            } else if (!gamepad1.dpad_right) {

                isButtonPushed = false;

            }

            if (gamepad1.right_stick_y > 0.1) {

                MarkerServoUp = true;
                MarkerServoDown = false;

            } else if (gamepad1.right_stick_y < -0.1) {

                MarkerServoDown = true;
                MarkerServoUp = false;

            } else {

                MarkerServoUp = false;
                MarkerServoDown = false;

            }

            attachmentControl.horizontalServoManual(gamepad2.dpad_left, gamepad2.dpad_right);

            attachmentControl.verticalServoManual(gamepad2.dpad_up, gamepad2.dpad_down);

            attachmentControl.markerServoManual(gamepad2.left_trigger > 0.1, gamepad2.left_bumper);

//            attachmentControl.markerServoManual(MarkerServoUp, MarkerServoDown);


//            attachmentControl.markerServoTest(gamepad1.right_bumper, isMarkerServoButtonPushed, MarkerServoUp, MarkerServoDown);

            attachmentControl.liftMotorMove(gamepad1.dpad_up, gamepad1.dpad_down);

            if (gamepad1.x) {

                liftHingePosition = AttachmentControl.LiftHingePosition.Back;

            } else if (gamepad1.b) {

                liftHingePosition = AttachmentControl.LiftHingePosition.Forward;

            }

            if (hingeManual) {

                attachmentControl.hingeMotorManual(gamepad1.y, gamepad1.a);

            } else {

                attachmentControl.setHingePos(liftHingePosition);

            }

            telemetry.addData("Lift Hinge Position", attachmentControl.LiftHinge.getCurrentPosition());
            telemetry.addData("Lift Motor Position", attachmentControl.LiftMotor.getCurrentPosition());
            telemetry.addData("Vertical Servo Position", attachmentControl.VerticalServo.getPosition());
            telemetry.addData("Horizontal Servo Position", attachmentControl.HorizontalServo.getPosition());
            telemetry.addData("Marker Servo Speed", attachmentControl.MarkerServo.getPower());
            telemetry.update();

        }

    }

}
