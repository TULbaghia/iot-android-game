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

    private GameThread gameThread;

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void draw(Canvas canvas) {
        if(null != canvas) {
            super.draw(canvas);
            canvas.drawCircle(100, 100, 100, getPaint(Paint.Style.FILL, Color.RED));
        }
    }

    private Paint getPaint(Paint.Style style, Integer color) {
        Paint paint = new Paint();
        paint.setStyle(style);
        paint.setColor(color);
        return paint;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(getHolder(), this);
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
            } catch (Exception e) {
                System.out.println("Wyjątek.");
            }
        }
    }
}
