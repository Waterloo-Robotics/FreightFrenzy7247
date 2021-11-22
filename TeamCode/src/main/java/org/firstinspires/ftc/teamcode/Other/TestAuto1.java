package org.firstinspires.ftc.teamcode.Other;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@Autonomous
public class TestAuto1 extends LinearOpMode {

    public DcMotor e;

    @Override
    public void runOpMode() {

        e = hardwareMap.dcMotor.get("front_left");

        waitForStart();



    }

}
