package pl.lodz.p.embeddedsystems.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.panoramagl.PLImage;
import com.panoramagl.PLManager;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.utils.PLUtils;

import pl.lodz.p.embeddedsystems.R;

public class PanoramaFragment extends Fragment {

    public static PanoramaFragment newInstance() {
        return new PanoramaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panorama_fragment, container, false);

        setupPanorama(view);

        return view;
    }

    private void setupPanorama(View view) {
        PLManager plManager = new PLManager(this.getContext());
        plManager.setContentView((ViewGroup) view);
        plManager.onCreate();

        plManager.setAccelerometerEnabled(false);
        plManager.setInertiaEnabled(false);
        plManager.setZoomEnabled(false);

        plManager.startSensorialRotation();

        PLSphericalPanorama panorama = new PLSphericalPanorama();
        panorama.setImage(new PLImage(PLUtils.getBitmap(this.getContext(), R.raw.stars_black_night_sky), false));
        float pitch = 5f;
        float yaw = 0f;
        float zoomFactor = 0.8f;

        panorama.getCamera().lookAtAndZoomFactor(pitch, yaw, zoomFactor, false);
        plManager.setPanorama(panorama);
    }
}
