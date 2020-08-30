package pl.lodz.p.embeddedsystems.game.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import pl.lodz.p.embeddedsystems.game.surface.GameSurfaceView;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;

/** Reprezentuje przetwarzany wątek gry. */
public class GameThread extends Thread {
  /** Aplikowany do SurfaceView - pozwala na dopasowanie parametrów powierzchni. */
  private final SurfaceHolder surfaceHolder;

  /** Plansza do gry z właściwym wątkiem. */
  private final GameSurfaceView gameSurface;

  /** Warstwa na której następuje rysowanie. */
  private Canvas canvas;

  /** Czy wątek jest aktywny. */
  private boolean running = false;

  public GameThread(@NonNull GameSurfaceView gameSurface) {
    this.gameSurface = gameSurface;
    this.surfaceHolder = gameSurface.getHolder();
  }

  /**
   * Włącza lub wyłącza przetwarzanie zadań w wątku.
   *
   * @param running fałsz w celu zatrzymania przetwarzania
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  // -=-=-=-=- >>>Thread -=-=-=-=-

  /** Przetwarzanie w wątku. */
  @Override
  public void run() {
    long frameStartTime;
    long frameTime;
    while (running) {
      frameStartTime = System.nanoTime();
      try {
        canvas = surfaceHolder.lockCanvas();
        synchronized (surfaceHolder) {
          gameSurface.draw(canvas);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (canvas != null) {
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

  // -=-=-=-=- <<<Thread -=-=-=-=-
}
