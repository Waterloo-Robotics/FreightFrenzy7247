package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AttachmentControl {

    public DcMotor DuckMotor;

    public Servo LiftServo;

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

    public void adjustLiftPosition(boolean upButton, boolean downButton) {

        if (upButton) {

            LiftServo.setPosition(LiftServo.getPosition() - 0.000175);

        } else if (downButton) {

            LiftServo.setPosition(LiftServo.getPosition() + 0.000175);

        } else {

            LiftServo.setPosition(LiftServo.getPosition());

        }

    }

    public void setLiftPosition(double position) {

        LiftServo.setPosition(position);

    }

}
