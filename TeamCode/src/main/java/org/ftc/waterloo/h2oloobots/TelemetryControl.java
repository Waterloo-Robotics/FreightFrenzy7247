package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryControl {

    AttachmentControl attachmentControl = new AttachmentControl();
    DriveTrain driveTrain = new DriveTrain();

    public void telemetryUpdateFourMotor(Telemetry telemetry) {

        String direction = "";
        double leftMax = Math.max(driveTrain.fl.getPower(), driveTrain.bl.getPower());
        double rightMax = Math.max(driveTrain.fr.getPower(), driveTrain.br.getPower());
        if (getDirection(driveTrain.fl) == -1 && getDirection(driveTrain.bl) == -1 && getDirection(driveTrain.fr) == 1 && getDirection(driveTrain.br) == 1) direction = "Moving Forward";
        if (getDirection(driveTrain.fl) == 1 && getDirection(driveTrain.bl) == 1 && getDirection(driveTrain.fr) == -1 && getDirection(driveTrain.br) == -1) direction = "Moving Backward";
        if (getDirection(driveTrain.fl) == -1 && getDirection(driveTrain.bl) == 1 && getDirection(driveTrain.fr) == -1 && getDirection(driveTrain.br) == 1) direction = "Strafing Right";
        if (getDirection(driveTrain.fl) == 1 && getDirection(driveTrain.bl) == -1 && getDirection(driveTrain.fr) == 1 && getDirection(driveTrain.br) == -1) direction = "Strafing Left";
        if (getDirection(driveTrain.fl) == -1 && getDirection(driveTrain.bl) == -1 && getDirection(driveTrain.fr) == -1 && getDirection(driveTrain.br) == -1) direction = "Turning Right";
        if (getDirection(driveTrain.fl) == 1 && getDirection(driveTrain.bl) == 1 && getDirection(driveTrain.fr) == 1 && getDirection(driveTrain.br) == 1) direction = "Turning Left";

        telemetry.addLine(direction + " at " + Math.max(leftMax, rightMax) + "% Speed");
        telemetry.addData("Lift Servo Position", attachmentControl.LiftServo.getPosition());
        telemetry.addData("Duck Motor Power", attachmentControl.DuckMotor.getPower());
        telemetry.update();

    }

    public int getDirection(DcMotor motor) {

        return (int) (motor.getPower() / Math.abs(motor.getPower()));

    }

}
