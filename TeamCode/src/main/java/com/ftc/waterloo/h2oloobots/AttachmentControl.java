package com.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AttachmentControl {

    public DcMotor DuckMotor;

    public DcMotor LiftMotor;

    public DcMotor LiftHinge;

    public DcMotor IntakeMotor;

    public CRServo MarkerServo;

    public Servo VerticalServo;

    public Servo HorizontalServo;

    boolean isDuckButtonPushed = false;
    boolean duckFunction = false;

    boolean isDuckSwitchButtonPushed = false;
    boolean blueDuckFunction = false;

    public static ElapsedTime duckTime = new ElapsedTime();

    public enum DuckMotorDirection {
        FORWARD,
        REVERSE
    }

    public double markerServoPosition = 0.985;

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry, DuckMotorDirection direction) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");
        if (direction == DuckMotorDirection.FORWARD || direction == null) {

            DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        } else if (direction == DuckMotorDirection.REVERSE) {

            DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        DuckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LiftMotor = hardwareMap.dcMotor.get("lift_motor");
        LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        LiftHinge = hardwareMap.dcMotor.get("lift_hinge");
        LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftHinge.setTargetPosition(0);
        LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftHinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeMotor = hardwareMap.dcMotor.get("intake_motor");

        MarkerServo = hardwareMap.crservo.get("marker_servo");

        VerticalServo = hardwareMap.servo.get("vert_servo");
        VerticalServo.scaleRange(0.04, 0.375);
        VerticalServo.setPosition(0.545);

        HorizontalServo = hardwareMap.servo.get("horiz_servo");
        HorizontalServo.scaleRange(0, 0.73);
        HorizontalServo.setPosition(0);

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry, DuckMotorDirection direction, boolean RESET_LIFT_HINGE) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");
        if (direction == DuckMotorDirection.FORWARD || direction == null) {

            DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        } else if (direction == DuckMotorDirection.REVERSE) {

            DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        DuckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LiftMotor = hardwareMap.dcMotor.get("lift_motor");
        LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        LiftHinge = hardwareMap.dcMotor.get("lift_hinge");
        if (RESET_LIFT_HINGE) LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftHinge.setTargetPosition(0);
        LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftHinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeMotor = hardwareMap.dcMotor.get("intake_motor");

        MarkerServo = hardwareMap.crservo.get("marker_servo");

        VerticalServo = hardwareMap.servo.get("vert_servo");
        VerticalServo.scaleRange(0.04, 0.375);
        VerticalServo.setPosition(0.545);

        HorizontalServo = hardwareMap.servo.get("horiz_servo");
        HorizontalServo.scaleRange(0, 0.73);
        HorizontalServo.setPosition(0);

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");
        DuckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LiftMotor = hardwareMap.dcMotor.get("lift_motor");
        LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        LiftHinge = hardwareMap.dcMotor.get("lift_hinge");
        LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftHinge.setTargetPosition(0);
        LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftHinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeMotor = hardwareMap.dcMotor.get("intake_motor");

        MarkerServo = hardwareMap.crservo.get("marker_servo");

        VerticalServo = hardwareMap.servo.get("vert_servo");
        VerticalServo.scaleRange(0.04, 0.375);
        VerticalServo.setPosition(0.545);

        HorizontalServo = hardwareMap.servo.get("horiz_servo");
        HorizontalServo.scaleRange(0, 0.73);
        HorizontalServo.setPosition(0);

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry, boolean RESET_LIFT_HINGE) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");
        DuckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LiftMotor = hardwareMap.dcMotor.get("lift_motor");
        LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        LiftHinge = hardwareMap.dcMotor.get("lift_hinge");
        if (RESET_LIFT_HINGE) LiftHinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftHinge.setTargetPosition(0);
        LiftHinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftHinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeMotor = hardwareMap.dcMotor.get("intake_motor");

        MarkerServo = hardwareMap.crservo.get("marker_servo");

        VerticalServo = hardwareMap.servo.get("vert_servo");
        VerticalServo.scaleRange(0.04, 0.375);
        VerticalServo.setPosition(0.545);

        HorizontalServo = hardwareMap.servo.get("horiz_servo");
        HorizontalServo.scaleRange(0, 0.73);
        HorizontalServo.setPosition(0);

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void markerServoManual(boolean button1, boolean button2) {

        if (button1) {

            MarkerServo.setPower(-1);

        } else if (button2) {

            MarkerServo.setPower(1);

        } else {

            MarkerServo.setPower(0);

        }

    }

    public void markerServoManual(double speed) {

        MarkerServo.setPower(speed);

    }

    double verticalServoPos = 0.545;

    public void verticalServoManual(boolean button1, boolean button2) {

        if (button1 && VerticalServo.getPosition() > 0) {

            verticalServoPos -= 0.007;

        } else if (button2 && VerticalServo.getPosition() < 1) {

            verticalServoPos += 0.007;

        }

        VerticalServo.setPosition(verticalServoPos);

    }

    double horizontalServoPos = 0;

    public void horizontalServoManual(boolean button1, boolean button2) {

        if (button1 && HorizontalServo.getPosition() > 0) {

            horizontalServoPos -= 0.004;

        } else if (button2 && HorizontalServo.getPosition() < 1) {

            horizontalServoPos += 0.004;

        }

        HorizontalServo.setPosition(horizontalServoPos);

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

    LiftHingePosition position = LiftHingePosition.Back;

    public void setHingePos( LiftHingePosition liftHingePosition) {

        position = liftHingePosition;

        switch (liftHingePosition) {

            case Back:

                LiftHinge.setTargetPosition(0);

            break;

            case Forward:

                LiftHinge.setTargetPosition(1880);


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

            if (!intakeOn) {

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

        if (duckFunction && duckTime.seconds() <= 1.95 && !blueDuckFunction) {

            duckPower = -0.5 - duckTime.seconds();

//            duckPower = 1;

            DuckMotor.setPower(duckPower);

        } else {

            DuckMotor.setPower(0);
            duckFunction = false;

        }

    }

    public void duckMotorTeleop(boolean mainButton, boolean fixButton) {

        if (mainButton && !isDuckButtonPushed) {

            isDuckButtonPushed = true;

            duckFunction = true;

            duckTime.reset();

        } else if (!mainButton) {

            isDuckButtonPushed = false;
        }

        if (duckFunction && duckTime.seconds() <= 1.95) {

            duckPower = -0.5 - duckTime.seconds();

//            duckPower = 1;

            DuckMotor.setPower(duckPower);

        } else {

            if (fixButton) {

                DuckMotor.setPower(0.5);

            } else {

                DuckMotor.setPower(0);

            }

            duckFunction = false;

        }

    }

    public void duckMotorReverse(boolean button) {

        if (button) {

            DuckMotor.setPower(0.5);

        } else {

            DuckMotor.setPower(0);

        }

    }

    public enum DuckMotorAlliance {
        Blue,
        Red
    }

    public static DuckMotorAlliance duckMotorAlliance = DuckMotorAlliance.Red;

    public void duckMotorSwitch(boolean button) {

        if (button && !isDuckSwitchButtonPushed) {

            if (DuckMotor.getDirection() == DcMotorSimple.Direction.FORWARD) {

                DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            } else if (DuckMotor.getDirection() == DcMotorSimple.Direction.REVERSE) {

                DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            }

            isDuckSwitchButtonPushed = true;

        } else if (!button) {

            isDuckSwitchButtonPushed = false;

        }

        if (DuckMotor.getDirection() == DcMotorSimple.Direction.FORWARD) {

            duckMotorAlliance = DuckMotorAlliance.Red;

        } else if (DuckMotor.getDirection() == DcMotorSimple.Direction.REVERSE) {

            duckMotorAlliance = DuckMotorAlliance.Blue;

        }

    }

    public void duckMotorAutoRed() {

        ElapsedTime timer = new ElapsedTime();

        timer.reset();

        DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        duckPower = -0.5;

        while (timer.seconds() < 0.125) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -0.625;

        while (timer.seconds() < 0.25) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -0.75;

        while (timer.seconds() < 0.375) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -1;

        while (timer.seconds() < 0.5) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -0.625;

        while (timer.seconds() < 2.5) {

            DuckMotor.setPower(duckPower);

        }

        DuckMotor.setPower(0);

    }

    public void duckMotorAutoBlue() {

        ElapsedTime timer = new ElapsedTime();

        timer.reset();

        DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        duckPower = -0.5;

        while (timer.seconds() < 0.125) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -0.625;

        while (timer.seconds() < 0.25) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -0.75;

        while (timer.seconds() < 0.375) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -1;

        while (timer.seconds() < 0.5) {

            DuckMotor.setPower(duckPower);

        }

        duckPower = -0.625;

        while (timer.seconds() < 2.65                 ) {

            DuckMotor.setPower(duckPower);

        }

        DuckMotor.setPower(0);

    }

    public void liftMotorMove(boolean upButton, boolean downButton) {

        if (upButton) {

            LiftMotor.setPower(0.7);

        } else if (downButton) {

            LiftMotor.setPower(-0.7);

        } else {

            LiftMotor.setPower(0);

        }

    }

    public static boolean setLiftMotor = false;

    public void SetLiftMotorPosTeleOp(boolean FDbutton, boolean FUbutton, boolean upButton, boolean downButton) {

        if (LiftHinge.getCurrentPosition() > 900) {

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

                    LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                } else if (downButton && LiftMotor.getCurrentPosition() > 0) {

                    LiftMotor.setPower(-0.7);

                    LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                } else if (LiftMotor.getCurrentPosition() < 0) {

                    LiftMotor.setTargetPosition(0);

                    LiftMotor.setPower(0.1);

                    LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                } else {

                    LiftMotor.setPower(0);

                    LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                }

            }

        } else if (position == LiftHingePosition.Back) {

            LiftMotor.setPower(0);

        }

    }

}
