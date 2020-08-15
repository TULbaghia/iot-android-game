package pl.lodz.p.embeddedsystems.game.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;

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

        this.gameSurfaceViewModel = new ViewModelProvider((MainActivity) this.getContext()).get(GameSurfaceViewModel.class);

        gameSurfaceViewModel.getMagnetometerValues().observe((MainActivity) this.getContext(), x -> System.out.println("MAGNETOMETER: " + Arrays.toString(x)));
        gameSurfaceViewModel.getAccelerometerValues().observe((MainActivity) this.getContext(), x -> System.out.println("ACCELEROMETER: " + Arrays.toString(x)));
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
