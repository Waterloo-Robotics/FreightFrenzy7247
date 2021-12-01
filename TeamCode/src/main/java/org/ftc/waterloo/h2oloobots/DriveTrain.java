package org.ftc.waterloo.h2oloobots;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {

    public DcMotor fl, fr, bl, br;

    public DcMotor left, right;

    double COUNTS_PER_INCH;

    double COUNTS_PER_DEGREE;

    public void TwoWheelInit(boolean RUN_USING_ENCODER, HardwareMap hardwareMap) {

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        if (RUN_USING_ENCODER) {
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void TwoWheelDriveTeleOp(double FBInput, double PivotInput, boolean RUN_USING_ENCODER, Telemetry telemetry) {

        right.setPower(FBInput - PivotInput);
        left.setPower(-FBInput - PivotInput);

        if (RUN_USING_ENCODER) {
            telemetry.addData("Right Encoder", String.valueOf(right.getCurrentPosition()));
            telemetry.addData("Left Encoder", String.valueOf(left.getCurrentPosition()));
            telemetry.update();
        }

    }

    public void FourMotorInit(boolean RUN_USING_ENCODER, HardwareMap hardwareMap, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {

        fl = hardwareMap.dcMotor.get("front_left");
        fr = hardwareMap.dcMotor.get("front_right");
        bl = hardwareMap.dcMotor.get("back_left");
        br = hardwareMap.dcMotor.get("back_right");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (RUN_USING_ENCODER) {
            fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    double fldir = 0;
    double frdir = 0;
    double bldir = 0;
    double brdir = 0;

    public void MecanumTeleOp(double FBInput, double LRInput, double PivotInput, boolean RUN_USING_ENCODER, Telemetry telemetry) {

        fr.setPower((-FBInput - LRInput - PivotInput) * 0.8);
        br.setPower((-FBInput + LRInput - PivotInput) * 0.8);
        fl.setPower((-FBInput + LRInput + PivotInput) * 0.8);
        bl.setPower((-FBInput - LRInput + PivotInput) * 0.8);

        if (RUN_USING_ENCODER) {
//            telemetry.addData("Front Right Encoder", String.valueOf(fr.getCurrentPosition()));
//            telemetry.addData("Front Left Encoder", String.valueOf(fl.getCurrentPosition()));
//            telemetry.addData("Back Right Encoder", String.valueOf(br.getCurrentPosition()));
//            telemetry.addData("Back Left Encoder", String.valueOf(bl.getCurrentPosition()));
//            telemetry.update();
        }

//        fldir = (fl.getPower() / Math.abs(fl.getPower()));
//        frdir = (fr.getPower() / Math.abs(fr.getPower()));
//        bldir = (bl.getPower() / Math.abs(bl.getPower()));
//        brdir = (br.getPower() / Math.abs(br.getPower()));

    }

    public void FWDTeleOp(double FBInput, double PivotInput, boolean RUN_USING_ENCODER, Telemetry telemetry) {

        fr.setPower(-FBInput - PivotInput);
        fl.setPower(FBInput - PivotInput);
        br.setPower(-FBInput - PivotInput);
        bl.setPower(FBInput - PivotInput);

        if (RUN_USING_ENCODER) {
            telemetry.addData("Front Right Encoder", String.valueOf(fr.getCurrentPosition()));
            telemetry.addData("Front Left Encoder", String.valueOf(fl.getCurrentPosition()));
            telemetry.addData("Back Right Encoder", String.valueOf(br.getCurrentPosition()));
            telemetry.addData("Back Left Encoder", String.valueOf(bl.getCurrentPosition()));
            telemetry.update();
        }

    }

    public void EncoderAutoInit(double WHEEL_DIAMETER_MM, double GEAR_RATIO, double COUNTS_PER_REVOLUTION) {

        double WHEEL_DIAMETER_INCHES = WHEEL_DIAMETER_MM / 25.4;

        COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_INCHES * 3.1415);

        COUNTS_PER_DEGREE = (COUNTS_PER_REVOLUTION * 50) / 90;

    }

    public void EncoderAutoMecanumDrive(double INCHES_FB, double INCHES_LR, double DEGREES_TURN, double SPEED, int time) {

        ElapsedTime timer = new ElapsedTime();

        int frTargetPosition = (int) fr.getCurrentPosition() - (int) (COUNTS_PER_INCH * INCHES_FB) - (int) (COUNTS_PER_INCH * INCHES_LR) - (int) (COUNTS_PER_DEGREE * DEGREES_TURN);
        int flTargetPosition = (int) fl.getCurrentPosition() - (int) (COUNTS_PER_INCH * INCHES_FB) + (int) (COUNTS_PER_INCH * INCHES_LR) + (int) (COUNTS_PER_DEGREE * DEGREES_TURN);
        int brTargetPosition = (int) br.getCurrentPosition() - (int) (COUNTS_PER_INCH * INCHES_FB) + (int) (COUNTS_PER_INCH * INCHES_LR) - (int) (COUNTS_PER_DEGREE * DEGREES_TURN);
        int blTargetPosition = (int) bl.getCurrentPosition() - (int) (COUNTS_PER_INCH * INCHES_FB) - (int) (COUNTS_PER_INCH * INCHES_LR) + (int) (COUNTS_PER_DEGREE * DEGREES_TURN);

        fr.setTargetPosition((int) frTargetPosition);
        br.setTargetPosition((int) brTargetPosition);
        fl.setTargetPosition((int) flTargetPosition);
        bl.setTargetPosition((int) blTargetPosition);

        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        timer.reset();

        fr.setPower(SPEED);
        br.setPower(SPEED);
        fl.setPower(SPEED);
        bl.setPower(SPEED);

        while ((fr.isBusy() || br.isBusy() || fl.isBusy() || bl.isBusy()) && timer.seconds() <= time) {

        }

        fr.setPower(0);
        br.setPower(0);
        fl.setPower(0);
        bl.setPower(0);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ElapsedTime waitTimer = new ElapsedTime();
        waitTimer.reset();
        while (waitTimer.seconds() <= 0.125) {

        }

    }

}
