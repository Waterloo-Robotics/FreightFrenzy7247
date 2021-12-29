package org.firstinspires.ftc.teamcode.Other;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.ftc.waterloo.h2oloobots.DriveTrain;

@Disabled
@Config
@Autonomous(name = "Auto1", group = "Group")
public class Auto1 extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
//    AttachmentControl attachmentControl = new AttachmentControl();

    double SPEED = 0.5;

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
//        attachmentControl.attachmentInit(hardwareMap, telemetry);

        driveTrain.EncoderAutoInit(100, 26.9, 28);

        waitForStart();


        driveTrain.EncoderAutoMecanumDrive(12, 0, 0, 0.5, 5);

        telemetry.addData("Front Right Target Position", driveTrain.fr.getTargetPosition());
        telemetry.addData("Front Left Target Position", driveTrain.fl.getTargetPosition());
        telemetry.addData("Back Right Target Position", driveTrain.br.getTargetPosition());
        telemetry.addData("Back Left Target Position", driveTrain.bl.getTargetPosition());
        telemetry.update();

        driveTrain.EncoderAutoMecanumDrive(0, 12, 0, 0.5, 5);

        telemetry.addData("Front Right Target Position", driveTrain.fr.getTargetPosition());
        telemetry.addData("Front Left Target Position", driveTrain.fl.getTargetPosition());
        telemetry.addData("Back Right Target Position", driveTrain.br.getTargetPosition());
        telemetry.addData("Back Left Target Position", driveTrain.bl.getTargetPosition());
        telemetry.update();

        driveTrain.EncoderAutoMecanumDrive(0, 0, 90, 0.5, 5);

        telemetry.addData("Front Right Target Position", driveTrain.fr.getTargetPosition());
        telemetry.addData("Front Left Target Position", driveTrain.fl.getTargetPosition());
        telemetry.addData("Back Right Target Position", driveTrain.br.getTargetPosition());
        telemetry.addData("Back Left Target Position", driveTrain.bl.getTargetPosition());
        telemetry.update();

        driveTrain.EncoderAutoMecanumDrive(0, 0, -90, 0.5, 5);

        telemetry.addData("Front Right Target Position", driveTrain.fr.getTargetPosition());
        telemetry.addData("Front Left Target Position", driveTrain.fl.getTargetPosition());
        telemetry.addData("Back Right Target Position", driveTrain.br.getTargetPosition());
        telemetry.addData("Back Left Target Position", driveTrain.bl.getTargetPosition());
        telemetry.update();

        driveTrain.EncoderAutoMecanumDrive(0, -12, 0, 0.5, 5);

        telemetry.addData("Front Right Target Position", driveTrain.fr.getTargetPosition());
        telemetry.addData("Front Left Target Position", driveTrain.fl.getTargetPosition());
        telemetry.addData("Back Right Target Position", driveTrain.br.getTargetPosition());
        telemetry.addData("Back Left Target Position", driveTrain.bl.getTargetPosition());
        telemetry.update();

        driveTrain.EncoderAutoMecanumDrive(-12, 0, 0, 0.5, 5);

        telemetry.addData("Front Right Target Position", driveTrain.fr.getTargetPosition());
        telemetry.addData("Front Left Target Position", driveTrain.fl.getTargetPosition());
        telemetry.addData("Back Right Target Position", driveTrain.br.getTargetPosition());
        telemetry.addData("Back Left Target Position", driveTrain.bl.getTargetPosition());
        telemetry.update();

        sleep(10000);

    }

}
