package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ContourPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class CameraCrashDuringInitTest extends LinearOpMode {

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

    public enum DuckPosition {

        left,
        middle,
        right

    }

    BlueDuckAutoEncoder.DuckPosition duckPosition = BlueDuckAutoEncoder.DuckPosition.middle;

    FtcDashboard dashboard = FtcDashboard.getInstance();

    public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //OpenCV Pipeline
        webcam.setPipeline(pipeline = new ContourPipeline());

        pipeline.ConfigurePipeline(0, 0,0,0,  CAMERA_WIDTH, CAMERA_HEIGHT);

        telemetry = dashboard.getTelemetry();
        FtcDashboard.getInstance().startCameraStream(webcam, 24);

        telemetry.update();

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(640,360, OpenCvCameraRotation.UPSIDE_DOWN);
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

            if(pipeline.getRectArea() > rectArea) {

                if (pipeline.getRectMidpointX() > 350) {

                    duckPosition = BlueDuckAutoEncoder.DuckPosition.left;

                } else if (pipeline.getRectMidpointX() > 220) {

                    duckPosition = BlueDuckAutoEncoder.DuckPosition.middle;

                } else {

                    duckPosition = BlueDuckAutoEncoder.DuckPosition.right;

                }

            }

            telemetry.addData("Duck Position", duckPosition);
            telemetry.update();

        }

    }

}
