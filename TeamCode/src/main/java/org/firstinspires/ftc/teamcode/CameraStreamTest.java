package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@TeleOp
public class CameraStreamTest extends LinearOpMode {

    public static final String VUFORIA_LICENSE_KEY = "AUhZBUP/////AAABmYEGpdLRPksVnc0ztTr0AVMWkvz/IqsD3cuBMKME0ZRQfnHZVGjZvnw138iHecuD+jNRvjNyidYb2ZgXwzaSru+n6xtkfyQvN7GU2s/kXkxMtJm5EGwMUkDqULQCEnqtm68Cc0FfKCV+aygL1qRRMHwfttGd82y5GqqnaEejg9Ummb/e7tGIaHsSlQJ9Met3Wwo9CzXCMZUa+SOq2orh0b2dv0Gj0xi4vzjBKdllxE6aXRYgXfq2h7Nxnx3MrdgnyUTn5FEJicPbXU4knlZEXE2+qSSmMeCaXw4KzSF/e5nDilQYgTYxRqE06Qzu1t0xqZQsIHnAdkFjmEdLpFwePjthqUUl2mRr7jGCNqZgmH1u";

    @Override
    public void runOpMode() throws InterruptedException {
        // gives Vuforia more time to exit before the watchdog notices
        msStuckDetectStop = 2500;

        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        waitForStart();

        while (opModeIsActive());
    }

}
