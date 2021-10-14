package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftc.waterloo.h2oloo.AttachmentControl;
import org.ftc.waterloo.h2oloo.DriveTrain;

@TeleOp
public class Tele1 extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
    AttachmentControl attachmentControl = new AttachmentControl();

    public void runOpMode() {

        driveTrain.FourMotorInit(false, hardwareMap);
        attachmentControl.attachmentInit(hardwareMap, telemetry, 1);

        waitForStart();

        while (opModeIsActive()) {

            driveTrain.MecanumTeleOp(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, false, telemetry);
            attachmentControl.adjustLiftPosition(telemetry, gamepad1.y, gamepad1.a);

        }

    }

}
