package pl.lodz.p.embeddedsystems.model.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import pl.lodz.p.embeddedsystems.model.gameobject.GameObject;

/**
 * Klasa zawierająca opis kulki, którą poruszamy się po ekranie.
 * Jest klasą będącą częścią GameSurface, zatem implementuje GameObject.
 */
public class Ball extends Shape implements GameObject {
    /**
     * Promień kuli.
     */
    private float radius;

    /**
     * Wypełnienie kuli.
     */
    private Paint style;

    /**
     * Konstruktor przyjmujący środek figury oraz jej promień.
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     * @param radius zmienna opisująca promień kuli.
     */
    public Ball(float x, float y, float radius, Paint style) {
        super(x, y);
        this.radius = radius;
        this.style = style;
    }

    /**
     * Zwraca promień kuli.
     * @return promień kuli.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Rysowanie kształtu.
     * @param canvas - warstwa rysowania.
     */
    @Override
    public void drawShape(Canvas canvas) {
        canvas.drawCircle(super.getCenterX(), (float) super.getCenterY(), this.radius, this.style);
    }

    /**
     * Aktualizacja pozycji obiektu.
     * @param point - punkt, względem którego odbywa się aktualizacja położenia.
     */
    @Override
    public void update(PointF point) {
     super.setCenter(point);
    }
}
