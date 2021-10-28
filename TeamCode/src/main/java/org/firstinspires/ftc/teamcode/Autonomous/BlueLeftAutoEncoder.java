package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;
import org.ftc.waterloo.h2oloobots.AttachmentControl;
import org.ftc.waterloo.h2oloobots.DriveTrain;

@Autonomous
public class BlueLeftAutoEncoder extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
    AttachmentControl attachmentControl = new AttachmentControl();

    public static final String VUFORIA_LICENSE_KEY = "AUhZBUP/////AAABmYEGpdLRPksVnc0ztTr0AVMWkvz/IqsD3cuBMKME0ZRQfnHZVGjZvnw138iHecuD+jNRvjNyidYb2ZgXwzaSru+n6xtkfyQvN7GU2s/kXkxMtJm5EGwMUkDqULQCEnqtm68Cc0FfKCV+aygL1qRRMHwfttGd82y5GqqnaEejg9Ummb/e7tGIaHsSlQJ9Met3Wwo9CzXCMZUa+SOq2orh0b2dv0Gj0xi4vzjBKdllxE6aXRYgXfq2h7Nxnx3MrdgnyUTn5FEJicPbXU4knlZEXE2+qSSmMeCaXw4KzSF/e5nDilQYgTYxRqE06Qzu1t0xqZQsIHnAdkFjmEdLpFwePjthqUUl2mRr7jGCNqZgmH1u";

    public void runOpMode() {

        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        FtcDashboard.getInstance().startCameraStream(vuforia, 24);

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.EncoderAutoInit(100, 26.9, 28);

//        attachmentControl.attachmentInit(hardwareMap, telemetry);

        waitForStart();

        driveTrain.EncoderAutoMecanumDrive(8,
                22,
                0,
                1,
                5
        );

//        attachmentControl.duckMotorAuto();
        sleep(2250);

        driveTrain.EncoderAutoMecanumDrive(
                8,
                -50,
                -90,
                1,
                10
        );

        driveTrain.EncoderAutoMecanumDrive(
                69,
                0,
                0,
                1,
                10
        );

    }

}
