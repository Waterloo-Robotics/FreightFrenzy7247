package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AttachmentControl {

    public DcMotor DuckMotor;

    public DcMotor LiftMotor;

    public DcMotor LiftHinge;

    public DcMotor IntakeMotor;

    boolean isDuckButtonPushed = false;
    boolean duckFunction = false;

    boolean isDuckSwitchButtonPushed = false;
    boolean blueDuckFunction = false;

    public static ElapsedTime duckTime = new ElapsedTime();

    public enum DuckMotorDirection {
        FORWARD,
        REVERSE
    }

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry, DuckMotorDirection direction) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");
        if (direction == DuckMotorDirection.FORWARD || direction == null) {

            DuckMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        } else if (direction == DuckMotorDirection.REVERSE) {

            DuckMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        }

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

    LiftHingePosition position = LiftHingePosition.Back;

    public void setHingePos( LiftHingePosition liftHingePosition) {

        position = liftHingePosition;

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

        if (duckFunction && duckTime.seconds() <= 1.55 && !blueDuckFunction) {

            duckPower = -0.5 - duckTime.seconds();

//            duckPower = 1;

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

        while (timer.seconds() < 1.55) {

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

        while (timer.seconds() < 1.75) {

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

    public void SetLiftMotorPosTeleOp(boolean FDbutton, boolean FUbutton , boolean upButton, boolean downButton) {

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

                } else if (downButton && LiftMotor.getCurrentPosition() > 0) {

                    LiftMotor.setPower(-0.7);

                } else {

                    LiftMotor.setPower(0);

                }
            }

        } else if (position == LiftHingePosition.Back) {

            LiftMotor.setPower(0);

        }

    }

}
