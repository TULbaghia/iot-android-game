package pl.lodz.p.embeddedsystems.sensormanager;

import android.content.Context;
import android.hardware.SensorEventListener;

import androidx.annotation.NonNull;

import java.util.EnumMap;
import java.util.Map;

import pl.lodz.p.embeddedsystems.sensormanager.sensors.AccelerometerSensor;
import pl.lodz.p.embeddedsystems.sensormanager.sensors.MagnometerSensor;
import pl.lodz.p.embeddedsystems.sensormanager.orientation.OrientationCalibrator;

// TODO: ?Add callback to model?
public class SensorController {
    private Context context;
    Map<SensorType, SensorEventListener> sensors = new EnumMap<>(SensorType.class);


    public SensorController(@NonNull Context context) {
        this.context = context;
        sensors.put(SensorType.ACCELEROMETER, new AccelerometerSensor(context));
        sensors.put(SensorType.MAGNOMETER, new MagnometerSensor(context));
    }

    public AccelerometerSensor getAccelerometer() {
        return (AccelerometerSensor) sensors.get(SensorType.ACCELEROMETER);
    }

    public MagnometerSensor getMagnometer(){
        return (MagnometerSensor) sensors.get(SensorType.MAGNOMETER);
    }

}
