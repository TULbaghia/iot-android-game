package pl.lodz.p.embeddedsystems.game.surface.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import pl.lodz.p.embeddedsystems.game.model.gameobject.GameObject;
import pl.lodz.p.embeddedsystems.game.model.shape.Shape;

public class PlayerBall extends Shape implements GameObject  {

    /**
     * Pole zawierające promień kuli.
     */
    private float radius;

    /**
     * Konstruktor przyjmujący środek figury oraz jej promień.
     *
     * @param x      zmienna opisująca współrzędną na osi x.
     * @param y      zmienna opisująca współrzędną na osi y.
     * @param style  zmienna opisująca styl obiektu.
     * @param radius zmienna opisująca promień kuli.
     */
    public PlayerBall(float x, float y, Paint style, float radius) {
        super(x, y, style);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void drawShape(Canvas canvas) {
        canvas.drawCircle(super.getCenterX(), super.getCenterY(), this.radius, super.getStyle());
    }

    @Override
    public void update(PointF point) {
        super.setCenter(point);
    }
}
