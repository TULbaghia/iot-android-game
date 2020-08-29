package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Objects;

import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class StepCountSensorFragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager = null;

    GameSurfaceViewModel gameSurfaceViewModel = null;

    private void registerSensorListener() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this.getContext()), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) Objects.requireNonNull(this.getContext()),
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    1);
        };

        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_GAME
        );
    }

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
        registerSensorListener();
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
    // -=-=-=-=- >>>SensorEventListener -=-=-=-=-

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        gameSurfaceViewModel.getStepCounter().setValue((int) sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    // -=-=-=-=- <<<SensorEventListener -=-=-=-=-
}
