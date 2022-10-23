package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MecanumDrive {
    DcMotor[] motors;
    public void setUp(DcMotor[] motors) {
        this.motors = motors;
    }
    public void Drive(double x, double y, double rx) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motors[0].setPower(frontLeftPower);
        motors[1].setPower(backLeftPower);
        motors[2].setPower(frontRightPower);
        motors[3].setPower(backRightPower);
    }
}
