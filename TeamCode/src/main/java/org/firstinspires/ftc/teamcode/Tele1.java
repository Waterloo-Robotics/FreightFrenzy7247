package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc.waterloo.h2oloobots.AttachmentControl;
import org.ftc.waterloo.h2oloobots.DriveTrain;
import org.ftc.waterloo.h2oloobots.TelemetryControl;

@TeleOp
@Config
public class Tele1 extends LinearOpMode {

    public DriveTrain driveTrain = new DriveTrain();
    public AttachmentControl attachmentControl = new AttachmentControl();
    public TelemetryControl telemetryControl = new TelemetryControl();

    AttachmentControl.LiftMotorPosition liftMotorPosition;

    int waitForTelemetry = 0;

    TelemetryPacket packet = new TelemetryPacket();

    FtcDashboard ftcDashboard = FtcDashboard.getInstance();

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        attachmentControl.attachmentInit(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {

            driveTrain.MecanumTeleOp(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true, telemetry);
            attachmentControl.duckMotorTeleop(gamepad1.dpad_left);
            attachmentControl.liftMotorMove(gamepad1.y, gamepad1.a);
            attachmentControl.SetLiftMotorPos(gamepad1.a, AttachmentControl.LiftMotorPosition.BOTTOM);
            attachmentControl.SetLiftMotorPos(gamepad1.y, AttachmentControl.LiftMotorPosition.HIGH);
            attachmentControl.SetLiftMotorPos(gamepad1.b, AttachmentControl.LiftMotorPosition.LOW);
            attachmentControl.SetLiftMotorPos(gamepad1.x, AttachmentControl.LiftMotorPosition.MIDDLE);

            String direction = "";
            double leftMax = Math.max(driveTrain.fl.getPower(), driveTrain.bl.getPower());
            double rightMax = Math.max(driveTrain.fr.getPower(), driveTrain.br.getPower());
            if (getDirection(driveTrain.fl.getPower()) == -1 && getDirection(driveTrain.bl.getPower()) == -1 && getDirection(driveTrain.fr.getPower()) == 1 && getDirection(driveTrain.br.getPower()) == 1) direction = "Moving Forward";
            if (getDirection(driveTrain.fl.getPower()) == 1 && getDirection(driveTrain.bl.getPower()) == 1 && getDirection(driveTrain.fr.getPower()) == -1 && getDirection(driveTrain.br.getPower()) == -1) direction = "Moving Backward";
            if (getDirection(driveTrain.fl.getPower()) == -1 && getDirection(driveTrain.bl.getPower()) == 1 && getDirection(driveTrain.fr.getPower()) == -1 && getDirection(driveTrain.br.getPower()) == 1) direction = "Strafing Right";
            if (getDirection(driveTrain.fl.getPower()) == 1 && getDirection(driveTrain.bl.getPower()) == -1 && getDirection(driveTrain.fr.getPower()) == 1 && getDirection(driveTrain.br.getPower()) == -1) direction = "Strafing Left";
            if (getDirection(driveTrain.fl.getPower()) == -1 && getDirection(driveTrain.bl.getPower()) == -1 && getDirection(driveTrain.fr.getPower()) == -1 && getDirection(driveTrain.br.getPower()) == -1) direction = "Turning Right";
            if (getDirection(driveTrain.fl.getPower()) == 1 && getDirection(driveTrain.bl.getPower()) == 1 && getDirection(driveTrain.fr.getPower()) == 1 && getDirection(driveTrain.br.getPower()) == 1) direction = "Turning Left";
            telemetry.addLine(direction + " at " + Math.max(leftMax, rightMax) + "% Speed");
            packet.addLine(direction + " at " + Math.max(leftMax, rightMax) + "% Speed");
            telemetry.update();
            ftcDashboard.sendTelemetryPacket(packet);

        }

    }

    public double getDirection(double motorPower) {

        if (motorPower != 0) {

            return (motorPower) / Math.abs(motorPower);

        } else {

            return 0;

        }

    }

    public void sendTelemetry(String caption, Object value) {

        telemetry.addData(caption, value);
        packet.put(caption, value);

    }

}
