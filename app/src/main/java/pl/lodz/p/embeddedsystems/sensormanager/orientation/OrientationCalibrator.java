package pl.lodz.p.embeddedsystems.sensormanager.orientation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class OrientationCalibrator {
    private static float [] orientatedData = new float[3];
    private static float [] orientationInitial = new float[3];

    public static void calibrateData(float [] magnometerValues, float [] accelerometerValues) {
        if (accelerometerValues != null && magnometerValues != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelerometerValues, magnometerValues);
            if (success) {
                SensorManager.getOrientation(R, orientatedData);
                if (orientationInitial == null) {
                    orientationInitial = new float[orientatedData.length];
                    System.arraycopy(orientatedData, 0, orientationInitial, 0, orientatedData.length);
                    orientationInitial = orientatedData;
                }
            }
        }
    }

    public static float[] getOrientatedData() {
        return orientatedData;
    }

    public static float[] getOrientationInitial() {
        return orientationInitial;
    }
}

