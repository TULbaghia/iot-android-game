package pl.lodz.p.embeddedsystems.model.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Klasa zawierająca opis kształtu.
 */
abstract class Shape {
    /**
     * Zmienne zawierające środek kształtu.
     */
    private PointF center;

    /**
     * Konstruktor przyjmujący środek figury.
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     */
    Shape(float x, float y) {
        this.center = new PointF(x, y);
    }

    /**
     * Przesuwa centrum kształtu o podaną wartość.
     * @param dx zmienna zawierająca wartość przesunięcia na osi x.
     * @param dy zmienna zawierająca wartość przesunięcia na osi y.
     */
    public void moveBy(float dx, float dy) {
        this.center.offset(dx, dy);
    }

    /**
     * Zwraca wartość współrzędnej x.
     * @return współrzędna x centrum.
     */
    public float getCenterX() {
        return this.center.x;
    }

    /**
     * Zwraca wartośś współrzędnej y.
     * @return współrzędna y centrum.
     */
    public double getCenterY() {
        return this.center.y;
    }

    /**
     * Ustawienie nowego centrum kształtu.
     */
    public void setCenter(PointF center) {
        this.center = center;
    }
}
