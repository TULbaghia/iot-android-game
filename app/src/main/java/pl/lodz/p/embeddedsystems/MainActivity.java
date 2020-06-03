package pl.lodz.p.embeddedsystems;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import pl.lodz.p.embeddedsystems.surface.GameSurface;

public class MainActivity extends Activity {

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
        setContentView(new GameSurface(this));
    }
}

