package pl.lodz.p.embeddedsystems;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.panoramagl.PLICamera;
import com.panoramagl.PLImage;
import com.panoramagl.PLManager;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.utils.PLUtils;

public class MainActivity extends Activity {
    private PLManager plManager;

    /**
     * Ustawia kontener aplikacji oraz jego zależności.
     *
     * @param savedInstanceState zapewnia najnowszą wersję.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_surface);

        setupPanorama();
    }

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

