package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;

public class AttachmentControl {

    public DcMotor DuckMotor;

    public Servo LiftServo;
    public DcMotor LiftMotor;

    public DcMotor LiftHinge;

    DcMotor IntakeMotor;

    boolean redIsDuckButtonPushed = false;
    boolean redDuckFunction = false;

    boolean blueIsDuckButtonPushed = false;
    boolean blueDuckFunction = false;

    ElapsedTime redDuckTime = new ElapsedTime();
    ElapsedTime blueDuckTime = new ElapsedTime();

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

        LiftHinge = hardwareMap.dcMotor.get("lift_hinge");
        LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftHinge.setTargetPosition(0);
        LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftHinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        IntakeMotor = hardwareMap.dcMotor.get("intake_motor");

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void hingeMotorManual(boolean forwardButton, boolean backwardButton) {

        if (forwardButton) {

            LiftHinge.setPower(0.5);

        } else if (backwardButton) {

            LiftHinge.setPower(-0.5);

        } else {

            LiftHinge.setPower(0);

        }

    }

    public enum LiftHingePosition {

        Back,
        Forward

    }

    public void setHingePos(LiftHingePosition liftHingePosition) {

        switch (liftHingePosition) {

            case Back:

                LiftHinge.setTargetPosition(0);

            break;

            case Forward:

                LiftHinge.setTargetPosition(1824);


            break;

        }

        if (LiftHinge.getCurrentPosition() != LiftHinge.getTargetPosition()) {

            LiftHinge.setPower(0.6);

        } else {

            LiftHinge.setPower(0);

        }

    }



    boolean intakeOn = false;
    boolean isIntakeButtonPushed = false;

    public void intakeMotorTeleOp(boolean IntakeButton, boolean OuttakeButton) {

        if (IntakeButton && !isIntakeButtonPushed) {

            if (intakeOn == false) {

                intakeOn = true;

            } else {

                intakeOn = false;

            }

            isIntakeButtonPushed = true;

        } else if (!IntakeButton) {

            isIntakeButtonPushed = false;

        }

        if (intakeOn && !OuttakeButton) {

            IntakeMotor.setPower(1);

        } else if (OuttakeButton) {

            IntakeMotor.setPower(-1);

            intakeOn = false;

        } else {

            IntakeMotor.setPower(0);

        }


    }

    double duckMotorPower = 0;

    public void redDuckMotorTeleop(boolean button) {

        if (button && !redIsDuckButtonPushed) {

            redIsDuckButtonPushed = true;

            redDuckFunction = true;

            redDuckTime.reset();

        } else if (!button) {

            redIsDuckButtonPushed = false;
        }

        if (redDuckFunction && redDuckTime.seconds() <= 2) {

            duckMotorPower = -0.125 - redDuckTime.seconds();

            DuckMotor.setPower(duckMotorPower);

        } else {

            DuckMotor.setPower(0);
            redDuckFunction = false;

        }

    }


    double startPowerBlue = -0.125;

    public void blueDuckMotorTeleop(boolean button) {

        if (button && !blueIsDuckButtonPushed) {

            blueIsDuckButtonPushed = true;

            blueDuckFunction = true;

            blueDuckTime.reset();

        } else if (!button) {

            blueIsDuckButtonPushed = false;
        }

        if (blueDuckFunction && blueDuckTime.seconds() <= 6) {

            duckMotorPower = blueDuckTime.seconds();
            DuckMotor.setPower(duckMotorPower);

        } else {

            DuckMotor.setPower(0);
            blueDuckFunction = false;

        }

    }

    public void duckMotorAutoRed() {

        ElapsedTime timer = new ElapsedTime();

        timer.reset();

        duckMotorPower = -0.125;

        while (timer.seconds() <= 2) {

            duckMotorPower -= timer.seconds();

            DuckMotor.setPower(duckMotorPower);

        }

        DuckMotor.setPower(0);

    }

    public void duckMotorAutoBlue() {

        ElapsedTime timer = new ElapsedTime();

        timer.reset();

        duckMotorPower = 0.125;

        while (timer.seconds() <= 2) {

            duckMotorPower += timer.seconds();

            DuckMotor.setPower(duckMotorPower);

        }

        DuckMotor.setPower(0);

    }

    public void liftMotorMove(boolean upButton, boolean downButton) {

        if (upButton && LiftMotor.getCurrentPosition() < 4062) {

            LiftMotor.setPower(0.7);

        } else if (downButton && LiftMotor.getCurrentPosition() > 0) {

            LiftMotor.setPower(-0.7);

        } else {

            LiftMotor.setPower(0);

        }

    }

    public static boolean setLiftMotor = false;

    public void SetLiftMotorPosTeleOp(boolean button, LiftMotorPosition liftMotorPosition, boolean upButton, boolean downButton) {

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

        }

        if (setLiftMotor && LiftMotor.getCurrentPosition() > LiftMotor.getTargetPosition()) {

            LiftMotor.setPower(-0.9);

        } else {

            setLiftMotor = false;

            if (upButton && LiftMotor.getCurrentPosition() < 4062) {

                LiftMotor.setPower(0.7);

            } else if (downButton && LiftMotor.getCurrentPosition() > 0) {

                LiftMotor.setPower(-0.7);

            } else {

                LiftMotor.setPower(0);

            }

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
