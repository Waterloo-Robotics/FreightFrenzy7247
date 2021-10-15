package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AttachmentControl {

    DcMotor DuckMotor;

    Servo LiftServo;

    boolean isDuckButtonPushed = false;
    boolean duckFunction = false;

    ElapsedTime duckTime = new ElapsedTime();

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry, double LiftStartPos) {

        DuckMotor = hardwareMap.dcMotor.get("duck_motor");

        LiftServo = hardwareMap.servo.get("lift_servo");
        LiftServo.scaleRange(0.52389, 0.964);
        LiftServo.setPosition(LiftStartPos);

        telemetry.addLine("Attachments Initialized");
        telemetry.update();

    }

    public void duckMotorTeleop(Telemetry telemetry, boolean button) {

        if (button && !isDuckButtonPushed) {

            isDuckButtonPushed = true;

            duckFunction = true;

            duckTime.reset();

        } else if (!button) {

            isDuckButtonPushed = false;
        }

        if (duckFunction && duckTime.seconds() <= 1.75) {

            DuckMotor.setPower(-0.25 - duckTime.seconds());

        } else {

            DuckMotor.setPower(0);
            duckFunction = false;

        }

        telemetry.addData("Duck Motor Power", DuckMotor.getPower());
        telemetry.update();

    }

    public void adjustLiftPosition(Telemetry telemetry, boolean upButton, boolean downButton) {

        if (upButton) {

            LiftServo.setPosition(LiftServo.getPosition() - 0.000175);

        } else if (downButton) {

            LiftServo.setPosition(LiftServo.getPosition() + 0.000175);

        } else {

            LiftServo.setPosition(LiftServo.getPosition());

        }

        telemetry.addData("Lift Servo Position", LiftServo.getPosition());
        telemetry.update();

    }

    public void setLiftPosition(double position) {

        LiftServo.setPosition(position);

    }

}
