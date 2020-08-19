package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import java.util.Objects;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class RotationFragment extends Fragment {

    GameSurfaceViewModel gameSurfaceViewModel = null;

    OrientationEventListener orientationEventListener = null;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) Objects.requireNonNull(this.getContext())).get(GameSurfaceViewModel.class);

        this.orientationEventListener = new OrientationEventListener(this.getContext()) {
            @Override
            public void onOrientationChanged(int i) {
                gameSurfaceViewModel.getRotation().setValue(
                        ((WindowManager) Objects.requireNonNull(getContext()).getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation());
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orientationEventListener.enable();
    }

    @Override
    public void onPause() {
        super.onPause();
        orientationEventListener.disable();
    }

    @Override
    public void onResume() {
        super.onResume();
        orientationEventListener.enable();
    }

}
