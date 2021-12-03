package org.ftc.waterloo.h2oloobots;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    public DcMotor IntakeMotor;

    boolean isDuckButtonPushed = false;
    boolean duckFunction = false;

    boolean blueIsDuckButtonPushed = false;
    boolean blueDuckFunction = false;

    public static ElapsedTime duckTime = new ElapsedTime();
    public static ElapsedTime blueDuckTime = new ElapsedTime();

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

    ElapsedTime timer = new ElapsedTime();
    boolean isRLMButtonPushed = false;
    int RLMButtonCounter = 0;

    public void resetLiftMotor(boolean button) {

        if (button) {

            if (!isRLMButtonPushed) {

                if (RLMButtonCounter == 0 || RLMButtonCounter == 1 && timer.seconds() > 1) {

                    timer.reset();

                    RLMButtonCounter = 1;

                } else if (RLMButtonCounter == 1 && timer.seconds() <= 1) {

                    RLMButtonCounter = 0;

                    LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                }

                isRLMButtonPushed = true;

            }

        } else {

            isRLMButtonPushed = false;

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

    public static double duckPower = 0;

    public void duckMotorTeleop(boolean button) {

        if (button && !isDuckButtonPushed) {

            isDuckButtonPushed = true;

            duckFunction = true;

            duckTime.reset();

        } else if (!button) {

            isDuckButtonPushed = false;
        }

        if (duckFunction && duckTime.seconds() <= 2 && !blueDuckFunction) {

            duckPower = -0.125 - duckTime.seconds();

            DuckMotor.setPower(duckPower);

        } else {

            DuckMotor.setPower(0);
            duckFunction = false;

        }

    }


    public enum DuckMotorAlliance {
        Blue,
        Red
    }

    public static DuckMotorAlliance duckMotorAlliance = DuckMotorAlliance.Red;

    public static double blueDuckPower = 0.125;

    public void duckMotorSwitch(boolean button) {

        if (button && !blueIsDuckButtonPushed) {

            if (DuckMotor.getDirection() == DcMotorSimple.Direction.FORWARD) {

                DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                duckMotorAlliance = DuckMotorAlliance.Blue;

            } else if (DuckMotor.getDirection() == DcMotorSimple.Direction.REVERSE) {

                DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                duckMotorAlliance = DuckMotorAlliance.Red;

            }

        }

    }

    public void duckMotorAutoRed() {

        ElapsedTime timer = new ElapsedTime();

        timer.reset();

        DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        duckPower = -0.125;

        while (timer.seconds() <= 2) {

            duckPower -= timer.seconds();

            DuckMotor.setPower(duckPower);

        }

        DuckMotor.setPower(0);

    }

    public void duckMotorAutoBlue() {

        ElapsedTime timer = new ElapsedTime();

        timer.reset();

        DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        blueDuckPower = 0.125;

        while (timer.seconds() <= 2) {

            blueDuckPower += timer.seconds();

            DuckMotor.setPower(blueDuckPower);

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

    public void SetLiftMotorPosTeleOp(boolean FDbutton, boolean FUbutton , boolean upButton, boolean downButton) {

        if (FDbutton) {

            LiftMotor.setTargetPosition(0);

            setLiftMotor = true;

        } else if (FUbutton) {

            LiftMotor.setTargetPosition(4062);

            setLiftMotor = true;

        }

        if (setLiftMotor && LiftMotor.getCurrentPosition() > LiftMotor.getTargetPosition() && LiftMotor.getTargetPosition() == 0) {

            LiftMotor.setPower(-0.9);

        } else if (setLiftMotor && LiftMotor.getCurrentPosition() < LiftMotor.getTargetPosition() && LiftMotor.getTargetPosition() == 4062) {

            LiftMotor.setPower(0.9);

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
