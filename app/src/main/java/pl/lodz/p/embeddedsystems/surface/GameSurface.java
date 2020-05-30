package pl.lodz.p.embeddedsystems.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import pl.lodz.p.embeddedsystems.model.shape.Ball;
import pl.lodz.p.embeddedsystems.thread.GameThread;

/**
 * "Powierzchnia", na której uruchomiona zostaje właściwa aplikacja z grą.
 *  Tworzy własny wątek przez implementowanie interfejsu Runnable.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Właściwy wątek gry.
     */
    private GameThread gameThread;

    /**
     * Kształt gracza.
     */
    private PointF point;

    /**
     * Kształt gracza.
     */
    private Ball ball;

    public GameSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        adjustShape();
        point = new PointF(100,100);
        setFocusable(true);
    }

    private void adjustShape() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        ball = new Ball(100,100,45, paint);
    }

    // nadpisane metody interfejsu SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
        System.out.println("TUTAJ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while (true) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                break;
            } catch (Exception e) {
                System.out.println("Wyjątek.");
            }
        }
    }

    // poruszanie kulą poprzez ruch palcem po ekranie
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                point.set(event.getX(), event.getY());
        }
        return true;
    }

    /**
     * Aktualizacja położenia środku kształtu (w tym przypadku kuli).
     */
    public void update() {
        ball.update(point);
    }

    /**
     * Rysowanie każdego wybranego przez użytkownika położenia.
     */
    @Override
    public void draw (Canvas canvas) {
        super.draw(canvas);
        ball.drawShape(canvas);
    }

}
