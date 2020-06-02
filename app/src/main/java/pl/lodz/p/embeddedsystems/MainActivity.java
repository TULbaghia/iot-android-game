package pl.lodz.p.embeddedsystems;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import pl.lodz.p.embeddedsystems.surface.GameSurface;

public class MainActivity extends Activity {

    /**
     * Przechowuje instancję aplikacji.
     */
    private static Application application;

    /**
     * Ustawia kontener aplikacji oraz jego zależności.
     *
     * @param savedInstanceState zapewnia najnowszą wersję.
     */
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        application = this.getApplication();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameSurface());
    }

    /**
     * Zwraca instancję aplikacji utworzona przy tworzeniu.
     *
     * @return instancję aplikacji zawartej w aktywności.
     */
    public static Application getApp() {
        return application;
    }

    /**
     * Zwraca kontekst z instacji aplikacji utworzonej przy tworzeniu.
     *
     * @return kontekst istniejący w ramach aplikacji.
     */
    public static Context getContext() {
        return application.getApplicationContext();
    }
}

