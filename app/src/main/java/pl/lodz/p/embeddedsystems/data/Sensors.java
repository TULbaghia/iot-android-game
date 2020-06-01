package pl.lodz.p.embeddedsystems.data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Sensors implements SensorEventListener {

    private float[] accelerometerValues;

    public Sensors(Context context) {
        accelerometerValues = new float[] {0,0,0};

        SensorManager sensorManager =
                (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
        );
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = sensorEvent.values;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float[] getAccelerometerValues() {
        return accelerometerValues;
    }
}
