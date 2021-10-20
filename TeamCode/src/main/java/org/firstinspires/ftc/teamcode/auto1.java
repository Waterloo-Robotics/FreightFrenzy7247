package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.ftc.waterloo.h2oloobots.AttachmentControl;
import org.ftc.waterloo.h2oloobots.DriveTrain;

@Autonomous(name = "Auto1", group = "Group")
public class auto1 extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
    AttachmentControl attachmentControl = new AttachmentControl();

    double SPEED = 0.5;

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap);
        attachmentControl.attachmentInit(hardwareMap, telemetry, 1);

        waitForStart();

        driveTrain.EncoderAutoMecanumDrive(12, 0, 0, 0.5, 5);

    }

}
