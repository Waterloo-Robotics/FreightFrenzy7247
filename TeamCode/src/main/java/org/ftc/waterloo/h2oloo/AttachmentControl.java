package org.ftc.waterloo.h2oloo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AttachmentControl {

    Servo LiftServo;

    public void attachmentInit(HardwareMap hardwareMap, Telemetry telemetry, double LiftStartPos) {

        LiftServo = hardwareMap.servo.get("lift_servo");
        LiftServo.scaleRange(0.52389, 0.964);
        LiftServo.setPosition(LiftStartPos);

        telemetry.addLine("Attachments Initialized");
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
