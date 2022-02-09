package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "e")
public class TestTeleOp extends LinearOpMode {

    public DcMotor e;

    public Servo ee;

    public void runOpMode() {

        e = hardwareMap.dcMotor.get("e");

        ee = hardwareMap.servo.get("ee");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            e.setPower(gamepad1.right_stick_x);

            if (gamepad1.a) {

                ee.setPosition(1);

            } else if (gamepad1.x) {

                ee.setPosition(0);

            }

            telemetry.addData("ee position", ee.getPosition());
            telemetry.update();

        }

    }

}
