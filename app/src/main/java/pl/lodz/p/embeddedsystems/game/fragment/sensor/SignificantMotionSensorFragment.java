package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Objects;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class SignificantMotionSensorFragment extends Fragment {

    SensorManager sensorManager = null;

    GameSurfaceViewModel gameSurfaceViewModel = null;

    Sensor sensor;

    TriggerEventListener triggerEventListener;

    private void registerSensorListener() {
        this.triggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent triggerEvent) {
                gameSurfaceViewModel.getSignificantMotion().setValue(gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getSignificantMotion()) + 1);
                registerSensorListener();
            }
        };

        sensorManager.requestTriggerSensor(this.triggerEventListener, this.sensor);
    }

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        this.sensorManager = (SensorManager) Objects.requireNonNull(this.getContext()).getSystemService(Context.SENSOR_SERVICE);

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);

        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.cancelTriggerSensor(this.triggerEventListener, this.sensor);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sensorManager.cancelTriggerSensor(this.triggerEventListener, this.sensor);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerSensorListener();
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
}
