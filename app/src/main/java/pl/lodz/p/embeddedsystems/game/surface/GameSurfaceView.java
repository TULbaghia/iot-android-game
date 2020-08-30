package pl.lodz.p.embeddedsystems.game.surface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import pl.lodz.p.embeddedsystems.game.surface.elements.GameSurfaceElements;
import pl.lodz.p.embeddedsystems.game.thread.GameThread;

/**
 * Plansza przechowująca informacje o rozgrywce.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Wątek odświeżajacyg planszę gry.
     */
    private GameThread gameThread;

    /**
     * Elementy na planszy.
     */
    GameSurfaceElements gameSurfaceViewElements = null;

    /**
     * Konstruktor dla FragmentManager'a.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     */
    public GameSurfaceView(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * Konstruktor dla propatora xml.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     * @param attrs atrybuty podane w xml
     */
    public GameSurfaceView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Inicjalizator pól obiektu
     */
    private void init() {
        this.getHolder().addCallback(this);
        this.getHolder().setFormat(PixelFormat.TRANSPARENT);

        this.gameSurfaceViewElements = new GameSurfaceElements(this.getContext());
    }

    // -=-=-=-=- >>>SurfaceView -=-=-=-=-

    /**
     * Rysowanie elementów na planszy.
     *
     * @param canvas plansza do rysowania
     */
    @Override
    public void draw(Canvas canvas) {
        if (null != canvas) {
            super.draw(canvas);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.gameSurfaceViewElements.getScoringZone().drawShape(canvas);
            this.gameSurfaceViewElements.getPlayer().drawShape(canvas);
        }
    }

    /**
     * Łapie i propaguje zdarzenie dotknięcia ekranu.
     *
     * @param event zdarzenie dotknięcia ekranu.
     * @return prawdę jeżeli zdarzenie zostało obsłużone
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        gameSurfaceViewElements.onTouchEvent();
        return super.onTouchEvent(event);
    }

    // -=-=-=-=- <<<SurfaceView -=-=-=-=-
    // -=-=-=-=- >>>SurfaceHolder.Callback -=-=-=-=-

    /**
     * Tworzenie powierzchni planszy
     *
     * @param surfaceHolder obiekt zawierający powierzchnię
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    /**
     * Wykonywane podczas niszczenia planszy.
     *
     * @param surfaceHolder obiekt zawierający powierzchnię
     */
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
