package org.firstinspires.ftc.teamcode.Other;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class TestTeleop extends LinearOpMode {

    DcMotor fl, fr, bl, br;

    public void runOpMode() {

        fl = hardwareMap.dcMotor.get("front_left");
        fr = hardwareMap.dcMotor.get("front_right");
        bl = hardwareMap.dcMotor.get("back_left");
        br = hardwareMap.dcMotor.get("back_right");

        waitForStart();

        while (opModeIsActive()) {

            fl.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
            fr.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
            bl.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);
            br.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);

            telemetry.addData("Front Left", fl.getPower());
            telemetry.addData("Front Right", fr.getPower());
            telemetry.addData("Back Left", bl.getPower());
            telemetry.addData("Back Right", br.getPower());
            telemetry.update();

        }

    }

}
