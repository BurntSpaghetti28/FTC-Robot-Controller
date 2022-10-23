package org.firstinspires.ftc.robotcontroller.internal;

import static java.lang.Math.PI;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name="Main TeleOP")
public class MainTeleOP extends LinearOpMode {
    FieldCentric fc = new FieldCentric();
    MecanumDrive md = new MecanumDrive();
    BNO055IMU.Parameters params = new BNO055IMU.Parameters();
    boolean enableMecanumDrive = true;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
        DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
        DcMotor rl = hardwareMap.get(DcMotor.class, "rl");
        DcMotor rr = hardwareMap.get(DcMotor.class, "rr");
        DcMotor el = hardwareMap.get(DcMotor.class, "el");
        TouchSensor tsmin = hardwareMap.get(TouchSensor.class, "tsmin");
        TouchSensor tsmax = hardwareMap.get(TouchSensor.class, "tsmax");
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        params.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(params);

        fc.setUp(new DcMotor[] {fl, fr, rl, rr}, imu);
        md.setUp(new DcMotor[] {fl, fr, rl, rr});

        telemetry.addLine("Initialized and Ready!");
        telemetry.update();

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        el.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Running Variables= FieldCentric: " + (!enableMecanumDrive ? "ON" : "OFF"));
            telemetry.update();
            if (activated(gamepad1.left_stick_x) || activated(gamepad1.left_stick_y) || activated(gamepad1.right_stick_x)) {
                if (enableMecanumDrive) md.Drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
                else fc.Drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            }
            else {
                fl.setPower(0);
                fr.setPower(0);
                rl.setPower(0);
                rr.setPower(0);
            }
            if (gamepad1.y && !tsmax.isPressed()) {
                el.setPower(-1);
            }
            else if (gamepad1.a && !tsmin.isPressed()) {
                el.setPower(1);
            }
            else {
                el.setPower(0);
            }
        }

        telemetry.addLine("Stopping!");
        telemetry.update();

        fl.setPower(0);
        fr.setPower(0);
        rl.setPower(0);
        rr.setPower(0);
    }
    public boolean activated(float val) {
        return Math.abs(val) > 0.075;
    }
}
