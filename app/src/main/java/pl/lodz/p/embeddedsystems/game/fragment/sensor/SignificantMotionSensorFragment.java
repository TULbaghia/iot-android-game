package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/**
 * Fragment zliczający ruch urządzenia.
 */
public class SignificantMotionSensorFragment extends Fragment {

    private SensorManager sensorManager = null;

    private GameSurfaceViewModel gameSurfaceViewModel = null;

    private Sensor sensor;

    private TriggerEventListener triggerEventListener;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    /**
     * Wstępnie inicjuje fragment danymi oraz czujnik ruchu.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        assert getContext() != null;

        this.sensorManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);
        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
    }

    /**
     * Zatrzymanie obserwatora gdy fragment jest wstrzymany.
     */
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.cancelTriggerSensor(this.triggerEventListener, this.sensor);
    }

    /**
     * Zatrzymanie obserwatora gdy fragment jest niszczony.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.cancelTriggerSensor(this.triggerEventListener, this.sensor);
    }

    /**
     * Przywracanie/tworzenie obserwatora, gdy fragment jest wykorzystywany.
     */
    @Override
    public void onResume() {
        super.onResume();
        registerSensorListener();
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-

    /**
     * Rejestrowanie obserwatora dla zdarzenia.
     */
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
}
