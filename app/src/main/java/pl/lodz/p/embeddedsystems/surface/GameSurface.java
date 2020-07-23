package pl.lodz.p.embeddedsystems.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

import pl.lodz.p.embeddedsystems.model.lock.GameLock;
import pl.lodz.p.embeddedsystems.model.lock.GameLockState;
import pl.lodz.p.embeddedsystems.sensormanager.SensorController;
import pl.lodz.p.embeddedsystems.model.shape.Ball;
import pl.lodz.p.embeddedsystems.model.shape.Score;
import pl.lodz.p.embeddedsystems.sensormanager.orientation.OrientationCalibrator;
import pl.lodz.p.embeddedsystems.thread.GameThread;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * "Powierzchnia", na której uruchomiona zostaje właściwa aplikacja z grą.
 * Tworzy własny wątek przez implementowanie interfejsu Runnable.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Właściwy wątek gry.
     */
    private GameThread gameThread;

    /**
     * Kształt gracza.
     */
    private Ball ball;

    /**
     * Kształt punktowanej strefy.
     */
    private Ball correctCustomShape;

    /**
     * Wynik gracza.
     */
    private Score score;

    private GameLock gameLock;

    private SensorController sensorController;

    Rect screenBounds;

    public GameSurface(@NotNull Context context) {
        super(context);
        init(context);
    }

    public GameSurface(@NotNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(@NotNull Context context) {
        sensorController = new SensorController(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        screenBounds = new Rect(
                0, 0,
                displayMetrics.widthPixels, displayMetrics.heightPixels
        );
        setStartingObjects();
        setFocusable(true);
        setZOrderMediaOverlay(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    private void setStartingObjects() {
        ball = new Ball(screenBounds.width() / 2, screenBounds.height() - 45, 45, getPaint(Paint.Style.FILL, Color.RED));
        correctCustomShape = new Ball(screenBounds.width() / 2, screenBounds.height() / 2, screenBounds.height() / 6, getPaint(Paint.Style.FILL, Color.BLUE));
        score = new Score(screenBounds.left + screenBounds.width() / 10, screenBounds.left + screenBounds.width() / 10, getPaint(Paint.Style.FILL, Color.WHITE));
        gameLock = new GameLock(screenBounds.left + screenBounds.width() / 15, screenBounds.centerY() - screenBounds.height() / 4, getPaint(Paint.Style.FILL, Color.WHITE));
        ball.setMinValues(0, 0);
    }

    private Paint getPaint(Paint.Style style, Integer color) {
        Paint paint = new Paint();
        paint.setStyle(style);
        paint.setColor(color);
        return paint;
    }

    // nadpisane metody interfejsu SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        ball.setMaxValues(width, height);
        final int rotation = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        sensorController.getAccelerometer().setRotation(rotation);
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
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                ball.moveTo(event.getX(), event.getY());
//        }
//        return true;
//    }

    /**
     * Aktualizacja położenia środku kształtu (w tym przypadku kuli).
     */
    public void update() {

        float[] accelerometerValues = sensorController.getAccelerometer().getAccelerometerValues();
        float[] magnometerValues = sensorController.getMagnometer().getMagnometerValues();
        OrientationCalibrator.calibrateData(magnometerValues, accelerometerValues);

        gameLock.updateState((int)(Math.toDegrees( OrientationCalibrator.getOrientatedData()[0])+360)%360);
        if (gameLock.getLockState() == GameLockState.UNLOCKED) {
            float pitch = OrientationCalibrator.getOrientatedData()[1] - OrientationCalibrator.getOrientationInitial()[1];
            float roll = OrientationCalibrator.getOrientatedData()[2] - OrientationCalibrator.getOrientationInitial()[2];
            ball.moveBy(10 * roll * screenBounds.width() / 1000f, -15 * pitch * screenBounds.height() / 1000f);
            if (isInRange()) {
                score.update();
            }
        }
    }

    /**
     * Rysowanie każdego wybranego przez użytkownika położenia.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        correctCustomShape.drawShape(canvas);
        ball.drawShape(canvas);
        score.drawShape(canvas);
        gameLock.drawShape(canvas);
    }

    /**
     * Czy okrąg zawiera się w innym okręgu.
     */
    public boolean isInRange() {
        return correctCustomShape.getRadius() > (
                sqrt(pow(ball.getCenterX() - correctCustomShape.getCenterX(), 2) +
                pow(ball.getCenterY() - correctCustomShape.getCenterY(), 2)) + ball.getRadius()
        );
    }
}
