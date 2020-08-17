package pl.lodz.p.embeddedsystems.game.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import pl.lodz.p.embeddedsystems.game.surface.GameSurfaceView;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;

/**
 * Instancje klasy GameThread reprezentują aktualnie przetwarzany wątek gry.
 * TODO: Podczas działania aplikacji, może dojść do zablokowania odświeżenia zablokowanego canvas'a przez co wyrzuca błąd (non-critical)
 * TODO: @up java.lang.IllegalArgumentException: canvas object must be the same instance that was previously returned by lockCanvas
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

    public GameThread(GameSurfaceView gameSurface) {
        super();
        this.gameSurface = gameSurface;
        this.surfaceHolder = gameSurface.getHolder();
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
                    if(canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
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
