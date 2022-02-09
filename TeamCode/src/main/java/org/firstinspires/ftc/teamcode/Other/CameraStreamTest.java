package org.firstinspires.ftc.teamcode.Other;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Camera.ContourPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@TeleOp
public class CameraStreamTest extends LinearOpMode {

//    public static final String VUFORIA_LICENSE_KEY = "AUhZBUP/////AAABmYEGpdLRPksVnc0ztTr0AVMWkvz/IqsD3cuBMKME0ZRQfnHZVGjZvnw138iHecuD+jNRvjNyidYb2ZgXwzaSru+n6xtkfyQvN7GU2s/kXkxMtJm5EGwMUkDqULQCEnqtm68Cc0FfKCV+aygL1qRRMHwfttGd82y5GqqnaEejg9Ummb/e7tGIaHsSlQJ9Met3Wwo9CzXCMZUa+SOq2orh0b2dv0Gj0xi4vzjBKdllxE6aXRYgXfq2h7Nxnx3MrdgnyUTn5FEJicPbXU4knlZEXE2+qSSmMeCaXw4KzSF/e5nDilQYgTYxRqE06Qzu1t0xqZQsIHnAdkFjmEdLpFwePjthqUUl2mRr7jGCNqZgmH1u";

    private static final int CAMERA_WIDTH  = 640; // width  of wanted camera resolution
    private static final int CAMERA_HEIGHT = 360; // height of wanted camera resolution

    OpenCvCamera webcam;
    ContourPipeline pipeline;

    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //OpenCV Pipeline
        webcam.setPipeline(pipeline = new ContourPipeline());

        pipeline.ConfigurePipeline(0, 0,0,0,  CAMERA_WIDTH, CAMERA_HEIGHT);


        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
        // gives Vuforia more time to exit before the watchdog notices
        msStuckDetectStop = 2500;

//        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
//        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
//        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        FtcDashboard.getInstance().startCameraStream(webcam, 24);

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

        waitForStart();

        while (opModeIsActive()) {

            FtcDashboard.getInstance().startCameraStream(webcam, 24);

        }
    }

}
