package pl.lodz.p.embeddedsystems.game.model.shape;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Shape {
    /**
     * Zmienne zawierające środek kształtu.
     */
    private PointF center;

    /**
     * Zmienna zawierająca styl obiektu.
     */
    private Paint style;

    /**
     * Zmienna dozwolone wartości dla punktu środka.
     */
    private RectF allowedValues;


    /**
     * Konstruktor przyjmujący środek figury oraz styl.
     *
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     * @param style zmienna zawierająca pełny styl obiektu.
     */
    public Shape(float x, float y, Paint style) {
        this.center = new PointF(x, y);
        this.style = style;

        this.allowedValues = new RectF();
        setMinValues(0,0 );
    }

    /**
     * Ustawienie nowego centrum kształtu.
     */
    public void setCenter(PointF center) {
        this.center = center;
    }

    /**
     * Ustawienie stylu wizialnego obiektu.
     */
    public void setStyle(Paint style) {
        this.style = style;
    }

    /**
     * @return styl obiektu
     */
    public Paint getStyle(){
        return this.style;
    }

    /**
     * @return środek kształtu w danej orientacji.
     */
    public PointF getCenter() {
        return center;
    }

    /**
     * @return współrzędna x środka obiektu w danej orientacji.
     */
    public float getCenterX(){
        return this.center.x;
    }

    /**
     * @return współrzędna y środka obiektu w danej orientacji.
     */
    public float getCenterY(){
        return this.center.y;
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
     * Funkcja ustawiająca maksymalne, dozwolone wartości wartości.
     *
     * @param maxWidth  maksymalna wartość na osi x.
     * @param maxHeigh maksymalna wartość na osi y.
     */
    public void setMaxValues(float maxWidth, float maxHeigh) {
        allowedValues.right = maxWidth;
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

    public RectF getAllowedValues() {
        return allowedValues;
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
