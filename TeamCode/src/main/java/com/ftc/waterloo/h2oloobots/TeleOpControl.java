package com.ftc.waterloo.h2oloobots;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class TeleOpControl {

    public DriveTrain driveTrain = new DriveTrain();
    public AttachmentControl attachmentControl = new AttachmentControl();
    public TelemetryControl telemetryControl = new TelemetryControl();

    double flpower, frpower, blpower, brpower;

    int flpos, frpos, blpos, brpos;

    int lmpos, lhpos;

    boolean isBPushed = false;

    boolean isGP2RBPushed = false;

    ElapsedTime doubleClickTimer = new ElapsedTime();

    int clickCounter = 0;

    boolean resetCode = false;

    public AttachmentControl.LiftHingePosition liftHingePosition = AttachmentControl.LiftHingePosition.Back;


    public void init(HardwareMap hardwareMap, Telemetry telemetry, DriveTrain driveTrain, AttachmentControl attachmentControl, AttachmentControl.DuckMotorDirection direction) {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.FLOAT);
        attachmentControl.attachmentInit(hardwareMap, telemetry, direction, false);

    }

    public void teleOpRun(DriveTrain driveTrain, AttachmentControl attachmentControl, TelemetryControl telemetryControl, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {

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

        driveTrain.MecanumTeleOp(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true, attachmentControl);

        attachmentControl.duckMotorTeleop(gamepad2.dpad_left, gamepad2.dpad_right || gamepad2.left_bumper);

        attachmentControl.SetLiftMotorPosTeleOp(gamepad2.dpad_down, gamepad2.dpad_up, gamepad2.x, gamepad2.y, gamepad2.a);

        attachmentControl.resetLiftMotor(gamepad2.right_trigger > 0.1);



        if ((gamepad1.b) && !isBPushed) {

                if (liftHingePosition == AttachmentControl.LiftHingePosition.Back) {

                    liftHingePosition = AttachmentControl.LiftHingePosition.Forward;

                } else if (liftHingePosition == AttachmentControl.LiftHingePosition.Forward && attachmentControl.LiftMotor.getCurrentPosition() <= 50) {

                    liftHingePosition = AttachmentControl.LiftHingePosition.Back;

                }

            isBPushed = true;

        } else if (!gamepad1.b) {

            isBPushed = false;

        }

        isGP2RBPushed = attachmentControl.markerServoTeleOp(gamepad2.right_bumper, isGP2RBPushed);

        attachmentControl.setHingePos(liftHingePosition);

        attachmentControl.intakeMotorTeleOp(gamepad1.right_trigger > 0.9, gamepad1.right_bumper);

        telemetryControl.telemetryUpdate(telemetry, "Lift Hinge Position", String.valueOf(liftHingePosition));
        telemetryControl.telemetryUpdate(telemetry, "Lift Motor Position", String.valueOf(lmpos));
        telemetryControl.motorTelemetryUpdate(telemetry, flpower, frpower, blpower, brpower);

        telemetryControl.update(telemetry);

    }

}
