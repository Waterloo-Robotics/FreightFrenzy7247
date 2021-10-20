package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc.waterloo.h2oloobots.AttachmentControl;
import org.ftc.waterloo.h2oloobots.DriveTrain;
import org.ftc.waterloo.h2oloobots.TelemetryControl;

@TeleOp
public class Tele1 extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
    AttachmentControl attachmentControl = new AttachmentControl();
    TelemetryControl telemetryControl = new TelemetryControl();

    int waitForTelemetry = 0;

    public void runOpMode() {

        driveTrain.FourMotorInit(false, hardwareMap);
        attachmentControl.attachmentInit(hardwareMap, telemetry, 1);

        waitForStart();

        while (opModeIsActive()) {

            driveTrain.MecanumTeleOp(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, false, telemetry);
            attachmentControl.adjustLiftPosition(gamepad1.y, gamepad1.a);
            attachmentControl.duckMotorTeleop(gamepad1.x);
            if (gamepad1.b) attachmentControl.setLiftPosition(1);
            attachmentControl.liftMotorMove(gamepad1.dpad_up, gamepad1.dpad_down);

            telemetry.addData("Front Left Motor Power", driveTrain.fl.getPower());
            telemetry.addData("Front Right Motor Power", driveTrain.fr.getPower());
            telemetry.addData("Back Left Motor Power", driveTrain.bl.getPower());
            telemetry.addData("Back Right Motor Power", driveTrain.br.getPower());
            telemetry.addData("Lift Motor Position", attachmentControl.LiftMotor.getCurrentPosition());
            telemetry.addData("Duck Motor Power", attachmentControl.DuckMotor.getPower());
            telemetry.update();

        }

    }

}
