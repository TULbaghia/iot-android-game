package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Objects;

import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class AccelerometerSensorFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager = null;

    private GameSurfaceViewModel gameSurfaceViewModel = null;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

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
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
        );
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
    // -=-=-=-=- >>>SensorEventListener -=-=-=-=-

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gameSurfaceViewModel.getAccelerometerValues().setValue(applyRotation(sensorEvent.values));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    // -=-=-=-=- <<<SensorEventListener -=-=-=-=-


    private float[] applyRotation(float[] accelerometerValues) {
        float tmp;
        switch (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getRotation())) {
            case Surface.ROTATION_180:
                break;
            case Surface.ROTATION_90:
                tmp = accelerometerValues[0];
                accelerometerValues[0] = -accelerometerValues[1];
                accelerometerValues[1] = tmp;
                break;
            case Surface.ROTATION_270:
                tmp = accelerometerValues[0];
                accelerometerValues[0] = accelerometerValues[1];
                accelerometerValues[1] = -tmp;
                break;
            default:
                break;
        }
        return accelerometerValues;
    }
}
