package pl.lodz.p.embeddedsystems.game.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import pl.lodz.p.embeddedsystems.game.surface.GameSurfaceView;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;

/**
 * Instancje klasy GameThread reprezentują aktualnie przetwarzany wątek gry.
 */
public class GameThread extends Thread {
    /**
     * Aplikowany do SurfaceView - pozwala na dopasowanie parametrów powierzchni.
     */
    private final SurfaceHolder surfaceHolder;

    /**
     * Surface z właściwym wątkiem.
     */
    private GameSurfaceView gameSurface;

    /**
     * Warstwa na której następuje rysowanie.
     */
    private Canvas canvas;

    /**
     * Czy wątek jest aktywny.
     */
    private boolean running = false;

    public GameThread(SurfaceHolder surfaceHolder, GameSurfaceView gameSurface) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameSurface = gameSurface;
    }

    /**
     * Przetwarzanie w wątku.
     */
    @Override
    public void run() {
        long frameStartTime;
        long frameTime;
        while (running) {
            frameStartTime = System.nanoTime();
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
//                    gameSurface.update();
                    gameSurface.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            frameTime = (System.nanoTime() - frameStartTime);
            if (frameTime < SENSOR_DELAY_GAME) {
                try {
                    Thread.sleep(SENSOR_DELAY_GAME - frameTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
