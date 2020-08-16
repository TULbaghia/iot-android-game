package pl.lodz.p.embeddedsystems.game.fragment;

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

/**
 * Aktywność (Fragment) odpowiadająca za wyświetlanie panoramy jako tło aplikacji
 */
public class PanoramaFragment extends Fragment {

    /**
     * Tworzy widok panoramy z szablonu panorama_fragment.
     *
     * @param inflater           pozwala załadować xml aby otrzymać widok
     * @param container          grupa widoków
     * @param savedInstanceState referencja do obiektu podawanego przez system w onCreate
     * @return widok zawierający panoramę
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.panorama_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPanorama(view);
    }

    /**
     * Ustawia panoramę.
     *
     * @param view widok dla którego ustawić panoramę
     */
    private void setupPanorama(View view) {
        PLManager plManager = new PLManager(this.getContext());
        plManager.setContentView((ViewGroup) view);
        plManager.onCreate();

        plManager.setAccelerometerEnabled(false);
        plManager.setInertiaEnabled(false);
        plManager.setZoomEnabled(false);

        plManager.startSensorialRotation();

        PLSphericalPanorama panorama = new PLSphericalPanorama();
        panorama.setImage(new PLImage(PLUtils.getBitmap(this.getContext(), R.raw.galaxy), false));
        float pitch = 5f;
        float yaw = 0f;
        float zoomFactor = 0.8f;

        panorama.getCamera().lookAtAndZoomFactor(pitch, yaw, zoomFactor, false);
        plManager.setPanorama(panorama);
    }
}
