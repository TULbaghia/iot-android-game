package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.hardware.SensorManager;
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

public class OrientationCalibratorFragment extends Fragment {

    GameSurfaceViewModel gameSurfaceViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) Objects.requireNonNull(this.getContext())).get(GameSurfaceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.gameSurfaceViewModel.getAccelerometerValues()
                .observe(this.getViewLifecycleOwner(), accelerometerValues ->
                        this.gameSurfaceViewModel.getOrientationValues().setValue(calibrateData(
                                this.gameSurfaceViewModel.getMagnetometerValues().getValue(),
                                accelerometerValues
                        )));
    }

    private float[] calibrateData(float[] magnetometerValues, float[] accelerometerValues) {
        float[] orientatedData = new float[3];
        if (accelerometerValues != null && magnetometerValues != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelerometerValues, magnetometerValues);
            if (success) {
                SensorManager.getOrientation(R, orientatedData);
            }
        }
        return orientatedData;
    }
}
