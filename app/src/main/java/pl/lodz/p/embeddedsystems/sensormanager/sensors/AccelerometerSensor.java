package pl.lodz.p.embeddedsystems.sensormanager.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;

import androidx.annotation.NonNull;

public class AccelerometerSensor implements SensorEventListener {

    private float[] accelerometerValues;

    /**
     * Reprezentacja obortu ekranu przez Surface.ROTATION_*
     */
    private int rotation;

    public AccelerometerSensor(@NonNull Context context) {
        accelerometerValues = new float[]{0, 0, 0};

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
            applyRotation();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float[] getAccelerometerValues() {
        return accelerometerValues;
    }

    /**
     * Ustawia obrót ekranu.
     *
     * @param rotation wartość z enum Surface.ROTATION_*.
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Uwzględnia obrót ekranu przy zwracaniu wyników.
     */
    private void applyRotation() {
        float tmp;
        switch (this.rotation) {
            case Surface.ROTATION_180:
                break;
            case Surface.ROTATION_0:
                this.accelerometerValues[0] = -this.accelerometerValues[0];
                break;
            case Surface.ROTATION_90:
                tmp = this.accelerometerValues[0];
                this.accelerometerValues[0] = this.accelerometerValues[1];
                this.accelerometerValues[1] = tmp;
                break;
            case Surface.ROTATION_270:
                tmp = this.accelerometerValues[0];
                this.accelerometerValues[0] = -this.accelerometerValues[1];
                this.accelerometerValues[1] = -tmp;
                break;
            default:
                break;
        }
    }
}
