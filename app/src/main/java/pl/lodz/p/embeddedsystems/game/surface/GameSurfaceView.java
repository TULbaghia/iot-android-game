package pl.lodz.p.embeddedsystems.game.surface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import pl.lodz.p.embeddedsystems.MainActivity;
import pl.lodz.p.embeddedsystems.game.thread.GameThread;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Wątek gry, odświeżajacy planszę.
     */
    private GameThread gameThread;

    GameSurfaceViewModel gameSurfaceViewModel = null;

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

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);

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
        gameSurfaceViewModel.getIsStarted().observe((LifecycleOwner) this.getContext(), x -> {
            if (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getCheatModeEnabled())) {
                Log.v("Azimuth reached:", x.toString());
            }
        });
        // <<<EXAMPLE- TO REMOVE
    }
    // -=-=-=-=- >>>SurfaceView -=-=-=-=-

    @Override
    public void draw(Canvas canvas) {
        if (null != canvas) {
            super.draw(canvas);
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
