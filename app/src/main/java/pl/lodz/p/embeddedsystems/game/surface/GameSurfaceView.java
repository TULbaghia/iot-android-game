package pl.lodz.p.embeddedsystems.game.surface;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Arrays;

import pl.lodz.p.embeddedsystems.game.surface.elements.GameSurfaceElements;
import pl.lodz.p.embeddedsystems.game.thread.GameThread;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Wątek gry, odświeżajacy planszę.
     */
    private GameThread gameThread;

    /**
     * View model - powierzchnia gry.
     */
    GameSurfaceViewModel gameSurfaceViewModel = null;

    /**
     * Elementy na planszy.
     */
    GameSurfaceElements gameSurfaceViewElements = null;

    public GameSurfaceView(Context context) {
        super(context);
        init();
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.getHolder().addCallback(this);
        this.getHolder().setFormat(PixelFormat.TRANSPARENT);

        // TODO: Wsparcie dla rysowania, dla API przed 28-- usunąć w finalnej wersji aplikacji
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            this.setZOrderOnTop(true);
        }

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);
        this.gameSurfaceViewElements = new GameSurfaceElements(this.getContext());

        // >>>EXAMPLE- TO REMOVE
        gameSurfaceViewModel.getMagnetometerValues().observe((LifecycleOwner) this.getContext(), x -> {
            if (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getCheatModeEnabled())) {
                Log.v("MAGNETOMETER", Arrays.toString(x));
            }
        });
        gameSurfaceViewModel.getAccelerometerValues().observe((LifecycleOwner) this.getContext(), x -> {
            if (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getCheatModeEnabled())) {
                Log.v("ACCELEROMETER", Arrays.toString(x));
            }
        });
        gameSurfaceViewModel.getOrientationValues().observe((LifecycleOwner) this.getContext(), x -> {
            if (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getCheatModeEnabled())) {
                Log.v("Orientation", Arrays.toString(x));
            }
        });
        //Temporary point increase.
        gameSurfaceViewModel.getOrientationValues().observe((LifecycleOwner) this.getContext(), x ->
                this.gameSurfaceViewModel.getGainedScore().setValue(
                        this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getGainedScore()) + 1
                )
        );
        // <<<EXAMPLE- TO REMOVE
    }
    // -=-=-=-=- >>>SurfaceView -=-=-=-=-

    @Override
    public void draw(Canvas canvas) {
        if (null != canvas) {
            super.draw(canvas);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.gameSurfaceViewElements.getScoringZone().drawShape(canvas);
            this.gameSurfaceViewElements.getPlayer().drawShape(canvas);
        }
    }

    // -=-=-=-=- <<<SurfaceView -=-=-=-=-
    // -=-=-=-=- >>>SurfaceHolder.Callback -=-=-=-=-

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        while (true) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // -=-=-=-=- <<<SurfaceHolder.Callback -=-=-=-=-
}
