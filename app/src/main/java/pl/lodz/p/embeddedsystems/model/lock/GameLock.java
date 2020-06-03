package pl.lodz.p.embeddedsystems.model.lock;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import pl.lodz.p.embeddedsystems.model.shape.Shape;

public class GameLock extends Shape {

    private GameLockState lockState;

    private boolean isGameStarting = true;

    private Paint style;

    /**
     * Konstruktor przyjmujący środek figury.
     *
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     */
    public GameLock(float x, float y, Paint style) {
        super(x, y);
        this.lockState = GameLockState.LOCKED;
        style.setTextSize(65);
        style.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        this.style = style;
    }

    public void drawShape(Canvas canvas) {
        if (lockState.getInfo() != null) {
            canvas.drawText(lockState.getInfo(), super.getCenterX(), super.getCenterY(), this.style);
        }
    }

    public void updateState(Integer azimuth) {
        if (isGameStarting && (azimuth > 350 || azimuth < 15)){
            this.lockState = GameLockState.UNLOCKED;
            isGameStarting = false;
        }
    }

    public GameLockState getLockState() {
        return lockState;
    }
}
