package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc.waterloo.h2oloobots.AttachmentControl;

@TeleOp
public class AttachmentTest extends LinearOpMode {

    AttachmentControl attachmentControl = new AttachmentControl();
    boolean isButtonPushed = false;
    boolean manual = false;

    public AttachmentControl.LiftHingePosition liftHingePosition = AttachmentControl.LiftHingePosition.Back;

    public void runOpMode() {

        attachmentControl.attachmentInit(hardwareMap, telemetry, null);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_right && !isButtonPushed) {

                if (manual == false) {
                    manual = true;
                } else if (manual == true) {
                    manual = false;

                    attachmentControl.LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    attachmentControl.LiftHinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }

                isButtonPushed = true;

            } else if (!gamepad1.dpad_right) {

                isButtonPushed = false;

            }

            attachmentControl.liftMotorMove(gamepad1.dpad_up, gamepad1.dpad_down);

            if (gamepad1.x) {

                liftHingePosition = AttachmentControl.LiftHingePosition.Back;

            } else if (gamepad1.b) {

                liftHingePosition = AttachmentControl.LiftHingePosition.Forward;

            }

            if (manual) {

                attachmentControl.hingeMotorManual(gamepad1.y, gamepad1.a);

            } else {

                attachmentControl.setHingePos(liftHingePosition);

            }

            telemetry.addData("Lift Hinge Position", attachmentControl.LiftHinge.getCurrentPosition());
            telemetry.update();

        }

    }

}
