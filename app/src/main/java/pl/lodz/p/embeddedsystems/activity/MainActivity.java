package pl.lodz.p.embeddedsystems.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.panoramagl.PLManager;

import pl.lodz.p.embeddedsystems.activity.fragmentActivity.PanoramaActivity;

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
//        setContentView(R.layout.game_surface);

        Intent panorama = new Intent(this.getBaseContext(), PanoramaActivity.class);
        startActivity(panorama);

//        this.addContentView(new GameSurface(this), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
    }
}

