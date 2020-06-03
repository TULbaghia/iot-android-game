package pl.lodz.p.embeddedsystems.model.shape;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Klasa zawierająca opis kształtu.
 */
public abstract class Shape {
    /**
     * Zmienne zawierające środek kształtu.
     */
    private PointF center;

    /**
     * Zmienna dozwolone wartości dla punktu środka.
     */
    private RectF allowedValues;

    /**
     * Konstruktor przyjmujący środek figury.
     *
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     */
    public Shape(float x, float y) {
        this.center = new PointF(x, y);
        this.allowedValues = new RectF();
    }

    /**
     * Przesuwa centrum kształtu o podaną wartość.
     *
     * @param dx zmienna zawierająca wartość przesunięcia na osi x.
     * @param dy zmienna zawierająca wartość przesunięcia na osi y.
     */
    public void moveBy(float dx, float dy) {
        if (isInRange(this.center.x + dx, this.center.y)) {
            this.center.offset(dx, 0);
        }
        if (isInRange(this.center.x, this.center.y + dy)) {
            this.center.offset(0, dy);
        }
    }

    /**
     * Przesuwa centrum kształtu na podaną wartość.
     *
     * @param x zmienna zawierająca wartość na osi x.
     * @param y zmienna zawierająca wartość na osi y.
     */
    public void moveTo(float x, float y) {
        if (isInRange(x, this.center.y)) {
            this.center.set(x, this.center.y);
        }
        if (isInRange(this.center.x, y)) {
            this.center.set(this.center.x, y);
        }
    }

    /**
     * Zwraca wartość współrzędnej x.
     *
     * @return współrzędna x centrum.
     */
    public float getCenterX() {
        return this.center.x;
    }

    /**
     * Zwraca wartośś współrzędnej y.
     *
     * @return współrzędna y centrum.
     */
    public float getCenterY() {
        return this.center.y;
    }

    /**
     * Ustawienie nowego centrum kształtu.
     */
    public void setCenter(PointF center) {
        this.center = center;
    }

    /**
     * Funkcja ustawiająca maksymalne, dozwolone wartości wartości.
     *
     * @param maxWith  maksymalna wartość na osi x.
     * @param maxHeigh maksymalna wartość na osi y.
     */
    public void setMaxValues(float maxWith, float maxHeigh) {
        allowedValues.right = maxWith;
        allowedValues.bottom = maxHeigh;
    }

    /**
     * Funkcja ustawiająca minimalne, dozwolone wartości wartości.
     *
     * @param minWidth minimalna wartość na osi x.
     * @param minHeigh minimalna wartość na osi y.
     */
    public void setMinValues(float minWidth, float minHeigh) {
        allowedValues.left = minWidth;
        allowedValues.top = minHeigh;
    }

    /**
     * Funkcja sprawdzająca czy punkt znajduje się w dozwolonych wartościach.
     *
     * @param x wartość na osi x
     * @param y wartość na osi y
     * @return prawdę gdy zawiera się w wyznaczonej powierzchni.
     */
    public boolean isInRange(float x, float y) {
        return this.allowedValues == null || this.allowedValues.contains(x, y);
    }
}
