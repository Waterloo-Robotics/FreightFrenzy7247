package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc.waterloo.h2oloobots.AttachmentControl;
import org.ftc.waterloo.h2oloobots.DriveTrain;
import org.ftc.waterloo.h2oloobots.TelemetryControl;

@Config
@TeleOp(name = "Main Drive Code", group = "!TeleOp")
public class Tele1 extends LinearOpMode {

    public DriveTrain driveTrain = new DriveTrain();
    public AttachmentControl attachmentControl = new AttachmentControl();
    public TelemetryControl telemetryControl = new TelemetryControl();

    AttachmentControl.LiftMotorPosition liftMotorPosition;


    TelemetryPacket packet = new TelemetryPacket();

    FtcDashboard ftcDashboard = FtcDashboard.getInstance();

    double fldir, frdir, bldir, brdir = 0;

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        attachmentControl.attachmentInit(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {

            fldir = getDirection(driveTrain.fl.getPower());
            frdir = getDirection(driveTrain.fr.getPower());
            bldir = getDirection(driveTrain.bl.getPower());
            brdir = getDirection(driveTrain.br.getPower());

            driveTrain.MecanumTeleOp(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true, telemetry);
            attachmentControl.blueDuckMotorTeleop(gamepad1.dpad_left);
            attachmentControl.blueDuckMotorTeleop(gamepad1.dpad_right);
            attachmentControl.liftMotorMove(gamepad1.y, gamepad1.a);
//            attachmentControl.SetLiftMotorPos(gamepad1.a, AttachmentControl.LiftMotorPosition.BOTTOM);
//            attachmentControl.SetLiftMotorPos(gamepad1.y, AttachmentControl.LiftMotorPosition.HIGH);
//            attachmentControl.SetLiftMotorPos(gamepad1.b, AttachmentControl.LiftMotorPosition.LOW);
//            attachmentControl.SetLiftMotorPos(gamepad1.x, AttachmentControl.LiftMotorPosition.MIDDLE);
            attachmentControl.intakeMotorTeleOp(gamepad1.right_trigger > 0.9, gamepad1.right_bumper);

            String direction = "";
            double leftMax = Math.max(driveTrain.fl.getPower(), driveTrain.bl.getPower());
            double rightMax = Math.max(driveTrain.fr.getPower(), driveTrain.br.getPower());
            packet.clearLines();
            if (fldir != 0 && frdir != 0 && bldir != 0 && brdir != 0) {
                if (fldir == -1 && bldir == -1 && frdir == 1 && brdir == 1)
                    direction = "Moving Forward";
                if (fldir == 1 && bldir == 1 && frdir == -1 && brdir == -1)
                    direction = "Moving Backward";
                if (fldir == -1 && bldir == 1 && frdir == -1 && brdir == 1)
                    direction = "Strafing Right";
                if (fldir == 1 && bldir == -1 && frdir == 1 && brdir == -1)
                    direction = "Strafing Left";
                if (fldir == -1 && bldir == -1 && frdir == -1 && brdir == -1)
                    direction = "Turning Right";
                if (fldir == 1 && bldir == 1 && frdir == 1 && brdir == 1)
                    direction = "Turning Left";
                telemetry.addLine(direction + " at " + Math.max(leftMax, rightMax) + "% Speed");
                packet.addLine(direction + " at " + Math.max(leftMax, rightMax) + "% Speed");
            } else {

                telemetry.addLine("Stopped");
                packet.addLine("Stopped");

            } //telemetry junk
            telemetry.addData("Front Right Encoder", String.valueOf(driveTrain.fr.getCurrentPosition()));
            telemetry.addData("Front Left Encoder", String.valueOf(driveTrain.fl.getCurrentPosition()));
            telemetry.addData("Back Right Encoder", String.valueOf(driveTrain.br.getCurrentPosition()));
            telemetry.addData("Back Left Encoder", String.valueOf(driveTrain.bl.getCurrentPosition()));
            packet.put("Front Right Encoder", String.valueOf(driveTrain.fr.getCurrentPosition()));
            packet.put("Front Left Encoder", String.valueOf(driveTrain.fl.getCurrentPosition()));
            packet.put("Back Right Encoder", String.valueOf(driveTrain.br.getCurrentPosition()));
            packet.put("Back Left Encoder", String.valueOf(driveTrain.bl.getCurrentPosition()));
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
