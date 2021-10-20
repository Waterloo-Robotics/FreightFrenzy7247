package org.ftc.waterloo.h2oloobots;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryControl {

    AttachmentControl attachmentControl = new AttachmentControl();
    DriveTrain driveTrain = new DriveTrain();

    double fldir = 0;
    double frdir = 0;
    double bldir = 0;
    double brdir = 0;

    public void telemetryUpdateFourMotor(Telemetry telemetry) {

        fldir = driveTrain.fldir;
        frdir = driveTrain.frdir;
        bldir = driveTrain.bldir;
        brdir = driveTrain.brdir;

        telemetry.addData("Front Left Motor Power", driveTrain.fl.getPower());
        telemetry.addData("Front Right Motor Power", driveTrain.fr.getPower());
        telemetry.addData("Back Left Motor Power", driveTrain.bl.getPower());
        telemetry.addData("Back Right Motor Power", driveTrain.br.getPower());
        telemetry.addData("Lift Motor Position", attachmentControl.LiftMotor.getCurrentPosition());
        telemetry.addData("Duck Motor Power", attachmentControl.DuckMotor.getPower());
        telemetry.update();

    }

    public void getDirection(DcMotor motor, double var) {

        var = (motor.getPower() / Math.abs(motor.getPower()));

    }

}
