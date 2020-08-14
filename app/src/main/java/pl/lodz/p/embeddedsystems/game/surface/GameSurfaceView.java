package pl.lodz.p.embeddedsystems.game.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import pl.lodz.p.embeddedsystems.game.thread.GameThread;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Wątek gry, odświeżajacy planszę.
     */
    private GameThread gameThread;

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
    }

    private Paint getPaint(Paint.Style style, Integer color) {
        Paint paint = new Paint();
        paint.setStyle(style);
        paint.setColor(color);
        return paint;
    }

    public void update() {

    }

    // -=-=-=-=- >>>SurfaceView -=-=-=-=-

    @Override
    public void draw(Canvas canvas) {
        if(null != canvas) {
            super.draw(canvas);
            canvas.drawCircle(100, 100, 100, getPaint(Paint.Style.FILL, Color.RED));
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
