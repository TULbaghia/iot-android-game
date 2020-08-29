package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.os.PowerManager;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Objects;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/**
 * @deprecated Do usunięcia
 */
public class TemperatureSensorFragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager = null;

    GameSurfaceViewModel gameSurfaceViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        this.sensorManager = (SensorManager) Objects.requireNonNull(this.getContext()).getSystemService(Context.SENSOR_SERVICE);

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerSensorListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerSensorListener();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

//        HardwarePropertiesManager hardwarePropertiesManager= (HardwarePropertiesManager) this.getContext().getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);
//        float[] temp = hardwarePropertiesManager.getDeviceTemperatures(HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU, HardwarePropertiesManager.TEMPERATURE_CURRENT);

//        Log.d("TEMPERATURE", Arrays.toString(temp));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void registerSensorListener() {
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
        );

        float x = getCurrentCPUTemperature();


        System.out.println(x);
    }

    private float getCurrentCPUTemperature() {
        String file = readFile("/sys/devices/virtual/thermal/thermal_zone2/temp", '\n');
        if (file != null) {
            return Long.parseLong(file);
        } else {
            return -1f;
//            return Long.parseLong(batteryTemp + " " + (char) 0x00B0 + "C");
        }
    }

    private byte[] mBuffer = new byte[4096];

    @SuppressLint("NewApi")
    private String readFile(String file, char endChar) {
        // Permit disk reads here, as /proc/meminfo isn't really "on
        // disk" and should be fast.  TODO: make BlockGuard ignore
        // /proc/ and /sys/ files perhaps?
        StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            int len = is.read(mBuffer);
            is.close();

            if (len > 0) {
                int i;
                for (i = 0; i < len; i++) {
                    if (mBuffer[i] == endChar) {
                        break;
                    }
                }
                return new String(mBuffer, 0, i);
            }
        } catch (java.io.FileNotFoundException e) {
        } catch (java.io.IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (java.io.IOException e) {
                }
            }
            StrictMode.setThreadPolicy(savedPolicy);
        }
        return null;
    }
}
