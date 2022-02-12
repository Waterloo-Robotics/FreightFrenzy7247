package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.ftc.waterloo.h2oloobots.AttachmentControl;
import com.ftc.waterloo.h2oloobots.DriveTrain;
import com.ftc.waterloo.h2oloobots.TelemetryControl;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Camera.ContourPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

//@Disabled
@Autonomous(name = "Blue Alliance Duck Side", group = "!")
public class BlueDuckAutoEncoder extends LinearOpMode {

    DriveTrain driveTrain = new DriveTrain();
    AttachmentControl attachmentControl = new AttachmentControl();
    TelemetryControl telemetryControl = new TelemetryControl();
    ContourPipeline pipeline;

    private OpenCvCamera webcam;

    private static final int CAMERA_WIDTH  = 640; // width  of wanted camera resolution
    private static final int CAMERA_HEIGHT = 360; // height of wanted camera resolution

    double CrLowerUpdate = 150;
    double CbLowerUpdate = 120;
    double CrUpperUpdate = 255;
    double CbUpperUpdate = 255;

    double lowerruntime = 0;
    double upperruntime = 0;

    public static int rectArea = 400;

    ContourPipeline.DuckPosition duckPosition = ContourPipeline.DuckPosition.Middle;

    int DuckMotorPos = 0;

    public void runOpMode() {

        driveTrain.FourMotorInit(true, hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.EncoderAutoInit(26.9, 28);

        attachmentControl.attachmentInit(hardwareMap, telemetry, AttachmentControl.DuckMotorDirection.REVERSE);

        ElapsedTime timer = new ElapsedTime();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //OpenCV Pipeline
        webcam.setPipeline(pipeline = new ContourPipeline());

        pipeline.ConfigurePipeline(0, 0,0,0,  CAMERA_WIDTH, CAMERA_HEIGHT);


        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        telemetry.update();

        waitForStart();

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(640,360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        FtcDashboard.getInstance().startCameraStream(webcam, 24);

        timer.reset();
        while (timer.seconds() <= 4.5) {

            duckPosition = ContourPipeline.duckPosition;

            telemetry.addData("Duck Position", duckPosition);
            telemetry.update();

        }

        webcam.closeCameraDevice();

        switch (duckPosition) {

            case Left:

                DuckMotorPos = 1254;

                break;

            case Middle:

                DuckMotorPos = 2610;

                break;

            case Right:

                DuckMotorPos = 4062;

                break;

        }

        attachmentControl.IntakeMotor.setPower(1);

        attachmentControl.LiftHinge.setTargetPosition(1880);
        attachmentControl.LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        attachmentControl.LiftHinge.setPower(0.6);

        driveTrain.EncoderAutoMecanumDrive(
                15,
                0,
                0,
                0.9,
                10
        );

        driveTrain.EncoderAutoMecanumDrive(
                0,
                0,
                -75,
                0.9,
                10
        );

        driveTrain.EncoderAutoMecanumDrive(
                -36,
                0,
                0,
                0.9,
                10
        );

        driveTrain.EncoderAutoMecanumDrive(
                0,
                -20,
                0,
                0.6,
                5
        );

        attachmentControl.duckMotorAutoBlue();

        driveTrain.EncoderAutoMecanumDrive(
                0,
                38,
                5,
                0.9,
                5
        );

        attachmentControl.LiftMotor.setTargetPosition(DuckMotorPos);
        attachmentControl.LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        attachmentControl.LiftMotor.setPower(1);

        int counter = 0;

        while (attachmentControl.LiftMotor.isBusy()) {

            attachmentControl.LiftMotor.setPower(0.9);

            if (counter > 10) {

                attachmentControl.IntakeMotor.setPower(1);

            }

            counter++;
        }

        attachmentControl.LiftMotor.setPower(0);

        driveTrain.EncoderAutoMecanumDrive(
                30,
                0,
                0,
                0.9,
                10
        );

        driveTrain.EncoderAutoMecanumDrive(
                2,
                0,
                0,
                0.9,
                10
        );

        attachmentControl.IntakeMotor.setPower(-1);
        sleep(500);

        driveTrain.EncoderAutoMecanumDrive(
                -2,
                0,
                0,
                1,
                10
        );

        attachmentControl.IntakeMotor.setPower(0);

        attachmentControl.LiftMotor.setTargetPosition(0);
        attachmentControl.LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        attachmentControl.LiftMotor.setPower(0.9);

        driveTrain.EncoderAutoMecanumDrive(
                -30,
                0,
                0,
                0.8,
                10
        );

        driveTrain.EncoderAutoMecanumDrive(
                0,
                -10,
                0,
                0.8,
                5
        );

        while (attachmentControl.LiftMotor.isBusy()) {

            attachmentControl.LiftMotor.setPower(0.9);

        }

        attachmentControl.LiftMotor.setPower(0);

//        attachmentControl.LiftHinge.setTargetPosition(0);
//        attachmentControl.LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        attachmentControl.LiftHinge.setPower(0.6);
//
//        while (attachmentControl.LiftHinge.isBusy()) {
//
//
//
//        }

    }

}
