package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.ftc.waterloo.h2oloobots.AttachmentControl;
import com.ftc.waterloo.h2oloobots.DriveTrain;

@Autonomous(name = "Warehouse Park Only")
public class BlueWarehouseOnly extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
    AttachmentControl attachmentControl = new AttachmentControl();

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.EncoderAutoInit(100, 26.9, 28);

        attachmentControl.attachmentInit(hardwareMap, telemetry);

        waitForStart();

        driveTrain.EncoderAutoMecanumDrive(
                36,
                0,
                0,
                0.9,
                10
        );

    }

}
