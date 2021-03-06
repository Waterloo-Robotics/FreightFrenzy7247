package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.ftc.waterloo.h2oloobots.TeleOpControl;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.ftc.waterloo.h2oloobots.AttachmentControl;
import com.ftc.waterloo.h2oloobots.DriveTrain;
import com.ftc.waterloo.h2oloobots.TelemetryControl;

@Config
@TeleOp(name = "BLUE TeleOp", group = "!TeleOp")
public class BlueTeleOp extends LinearOpMode {

    public DriveTrain driveTrain = new DriveTrain();
    public AttachmentControl attachmentControl = new AttachmentControl();
    public TelemetryControl telemetryControl = new TelemetryControl();
    public TeleOpControl teleOpControl = new TeleOpControl();

    double flpower, frpower, blpower, brpower;

    int flpos, frpos, blpos, brpos;

    int lmpos, lhpos;

    boolean isBPushed = false;

    boolean isDpadRightPushed = false;

    ElapsedTime doubleClickTimer = new ElapsedTime();

    int clickCounter = 0;

    boolean resetCode = false;

    public AttachmentControl.LiftHingePosition liftHingePosition = AttachmentControl.LiftHingePosition.Back;

    public static AttachmentControl.DuckMotorDirection direction = AttachmentControl.DuckMotorDirection.REVERSE;

    public void runOpMode() {

        teleOpControl.init(hardwareMap, telemetry, driveTrain, attachmentControl, direction);

        waitForStart();

        while (opModeIsActive()) {

            teleOpControl.teleOpRun(driveTrain, attachmentControl, telemetryControl, telemetry, gamepad1, gamepad2);

        }

    }

}
