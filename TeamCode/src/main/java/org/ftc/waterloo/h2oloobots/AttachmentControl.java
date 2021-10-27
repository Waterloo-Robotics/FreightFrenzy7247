package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AttachmentControl {

    public DcMotor DuckMotor;

    public Servo LiftServo;
    public DcMotor LiftMotor;

    boolean isDuckButtonPushed = false;
    boolean duckFunction = false;

    ElapsedTime duckTime = new ElapsedTime();

    public enum LiftMotorPosition {
        BOTTOM,
        LOW,
        MIDDLE,
        HIGH
    }

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");

        LiftMotor = hardwareMap.dcMotor.get("lift_motor");
        LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void duckMotorTeleop(boolean button) {

        if (button && !isDuckButtonPushed) {

            isDuckButtonPushed = true;

            duckFunction = true;

            duckTime.reset();

        } else if (!button) {

            isDuckButtonPushed = false;
        }

        if (duckFunction && duckTime.seconds() <= 2) {

            DuckMotor.setPower(-0.125 - duckTime.seconds());

        } else {

            DuckMotor.setPower(0);
            duckFunction = false;

        }

    }

    public void duckMotorAuto() {

        ElapsedTime e = new ElapsedTime();

        e.reset();

        while (e.seconds() <= 2) {

            DuckMotor.setPower(-0.125 - duckTime.seconds());

        }

        DuckMotor.setPower(0);

    }

    public void liftMotorMove(boolean upButton, boolean downButton) {

        if (upButton && LiftMotor.getCurrentPosition() < 4353) {

            LiftMotor.setPower(0.5);

        } else if (downButton && LiftMotor.getCurrentPosition() > 0) {

            LiftMotor.setPower(-0.5);

        } else {

            LiftMotor.setPower(0);

        }

    }

    boolean setLiftMotor = false;

    public void SetLiftMotorPos(boolean button, LiftMotorPosition liftMotorPosition) {

        if (button) {

            switch (liftMotorPosition) {

                case BOTTOM:

                    LiftMotor.setTargetPosition(0);

                break;

                case LOW:

                    LiftMotor.setTargetPosition(1719);

                break;

                case MIDDLE:

                    LiftMotor.setTargetPosition(3229);

                break;

                case HIGH:

                    LiftMotor.setTargetPosition(4406);

                break;
            }

            setLiftMotor = true;

            LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        if (setLiftMotor && LiftMotor.isBusy()) {

            if (LiftMotor.getCurrentPosition() > LiftMotor.getTargetPosition()) {

                LiftMotor.setPower(-0.5);

            } else if (LiftMotor.getCurrentPosition() < LiftMotor.getTargetPosition()) {

                LiftMotor.setPower(0.5);

            }

        } else if (setLiftMotor && !LiftMotor.isBusy()) {

            setLiftMotor = false;

            LiftMotor.setPower(0);
            LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

//    public void adjustLiftPosition(boolean upButton, boolean downButton) {
//
//        if (upButton) {
//
//            LiftServo.setPosition(LiftServo.getPosition() - 0.000175);
//
//        } else if (downButton) {
//
//            LiftServo.setPosition(LiftServo.getPosition() + 0.000175);
//
//        } else {
//
//            LiftServo.setPosition(LiftServo.getPosition());
//
//        }
//
//    }

//    public void setLiftPosition(double position) {
//
//        LiftServo.setPosition(position);
//
//    }

}
