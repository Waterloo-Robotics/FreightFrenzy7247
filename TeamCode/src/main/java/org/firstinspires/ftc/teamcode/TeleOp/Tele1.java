package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.ftc.waterloo.h2oloobots.AttachmentControl;
import org.ftc.waterloo.h2oloobots.DriveTrain;
import org.ftc.waterloo.h2oloobots.TelemetryControl;

@Config
@TeleOp(name = "Main Drive Code", group = "!TeleOp")
public class Tele1 extends LinearOpMode {

    public DriveTrain driveTrain = new DriveTrain();
    public AttachmentControl attachmentControl = new AttachmentControl();
    public TelemetryControl telemetryControl = new TelemetryControl();

    double flpower, frpower, blpower, brpower;

    int flpos, frpos, blpos, brpos;

    int lmpos, lhpos;

    boolean isBPushed = false;

    boolean isDpadRightPushed = false;

    ElapsedTime doubleClickTimer = new ElapsedTime();

    int clickCounter = 0;

    boolean resetCode = false;

    public AttachmentControl.LiftHingePosition liftHingePosition = AttachmentControl.LiftHingePosition.Back;

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        attachmentControl.attachmentInit(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {

            flpower = driveTrain.fl.getPower();
            frpower = driveTrain.fr.getPower();
            blpower = driveTrain.bl.getPower();
            brpower = driveTrain.br.getPower();

            flpos = driveTrain.fl.getCurrentPosition();
            frpos = driveTrain.fr.getCurrentPosition();
            blpos = driveTrain.bl.getCurrentPosition();
            brpos = driveTrain.br.getCurrentPosition();

            lmpos = attachmentControl.LiftMotor.getCurrentPosition();
            lhpos = attachmentControl.LiftHinge.getCurrentPosition();

            driveTrain.MecanumTeleOp(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true, telemetry);

            attachmentControl.blueDuckMotorTeleop(gamepad2.dpad_left);
            attachmentControl.redDuckMotorTeleop(gamepad2.dpad_right);

            if (liftHingePosition == AttachmentControl.LiftHingePosition.Forward) {

                attachmentControl.SetLiftMotorPosTeleOp(gamepad2.dpad_down || gamepad1.a, AttachmentControl.LiftMotorPosition.BOTTOM, gamepad2.y, gamepad2.a);

            } else if (liftHingePosition == AttachmentControl.LiftHingePosition.Back) {

                attachmentControl.LiftMotor.setPower(0);

            }

            if (gamepad1.b && !isBPushed) {

                if (!resetCode) {

                    if (liftHingePosition == AttachmentControl.LiftHingePosition.Back) {

                        liftHingePosition = AttachmentControl.LiftHingePosition.Forward;

                    } else if (liftHingePosition == AttachmentControl.LiftHingePosition.Forward && attachmentControl.LiftMotor.getCurrentPosition() <= 50) {

                        liftHingePosition = AttachmentControl.LiftHingePosition.Back;

                    }

                }

                isBPushed = true;

            } else if (!gamepad1.b) {

                isBPushed = false;

            }

            attachmentControl.setHingePos(liftHingePosition);

            attachmentControl.intakeMotorTeleOp(gamepad1.right_trigger > 0.9, gamepad1.right_bumper);

            telemetryControl.telemetryUpdate(telemetry, "Lift Hinge Position", String.valueOf(liftHingePosition));
            telemetryControl.telemetryUpdate(telemetry, "Lift Motor Position", String.valueOf(lmpos));
            telemetryControl.motorTelemetryUpdate(telemetry, flpower, frpower, blpower, brpower);
            telemetryControl.update(telemetry);

        }

    }

}
