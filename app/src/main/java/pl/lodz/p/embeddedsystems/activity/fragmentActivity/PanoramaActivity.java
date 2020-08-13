package pl.lodz.p.embeddedsystems.activity.fragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.panoramagl.PLImage;
import com.panoramagl.PLManager;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.utils.PLUtils;

import pl.lodz.p.embeddedsystems.R;

public class PanoramaActivity extends Activity {

    private PLManager plManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_surface);

        setupPanorama();
    }

    /**
     * Załadowanie panoramy.
     */
    private void setupPanorama() {
        plManager = new PLManager(this);
        plManager.setContentView((ViewGroup) findViewById(R.id.panorama));
        plManager.onCreate();
        plManager.setAccelerometerEnabled(false);
        plManager.setInertiaEnabled(false);
        plManager.setZoomEnabled(false);
        plManager.startSensorialRotation();

        PLSphericalPanorama panorama = new PLSphericalPanorama();
        panorama.setImage(new PLImage(PLUtils.getBitmap(this, R.raw.space360), false));
        float pitch = 5f;
        float yaw = 0f;
        float zoomFactor = 0.8f;

        panorama.getCamera().lookAtAndZoomFactor(pitch, yaw, zoomFactor, false);
        plManager.setPanorama(panorama);
    }
}
