package pl.lodz.p.embeddedsystems.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import pl.lodz.p.embeddedsystems.surface.GameSurface;

/**
 * Instancje klasy GameThread reprezentują aktualnie przetwarzany wątek gry.
 */
public class GameThread extends Thread {
    /**
     * Czas na klatkę.
     */
    private static final int MAX_TIME_PER_FRAME = 1000 / 60;

    /**
     * Aplikowany do SurfaceView - pozwala na dopasowanie parametrów powierzchni.
     */
    private SurfaceHolder surfaceHolder;

    /**
     * Surface z właściwym wątkiem.
     */
    private GameSurface gameSurface;

    /**
     * Warstwa na której następuje rysowanie.
     */
    private Canvas canvas;

    /**
     * Czy wątek jest aktywny.
     */
    private boolean running = false;

    public GameThread(SurfaceHolder surfaceHolder, GameSurface gameSurface) {
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
                gameSurface.update();
                gameSurface.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
            frameTime = (System.nanoTime() - frameStartTime) / 1000000;
            if (frameTime < MAX_TIME_PER_FRAME) {
                try {
                    Thread.sleep(MAX_TIME_PER_FRAME - frameTime);
                } catch (InterruptedException e) {
                    System.out.println("Błąd");
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
