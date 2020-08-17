package pl.lodz.p.embeddedsystems.game.model.shape;

import android.graphics.Paint;
import android.graphics.PointF;

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
     * Konstruktor przyjmujący środek figury oraz styl.
     *
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     * @param style zmienna zawierająca pełny styl obiektu.
     */
    public Shape(float x, float y, Paint style) {
        this.center = new PointF(x, y);
        this.style = style;
    }

    /**
     * Ustawienie współrzędnych środka obiektu
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
     * @return środek kształtu.
     */
    public PointF getCenter() {
        return center;
    }

    /**
     * @return współrzędna x środka obiektu.
     */
    public float getCenterX(){
        return this.center.x;
    }

    /**
     * @return współrzędna y środka obiektu.
     */
    public float getCenterY(){
        return this.center.y;
    }

    // Test - brak limitu położenia na planszy
    /**
     * Przesuwa centrum kształtu o podaną wartość.
     *
     * @param dx zmienna zawierająca wartość przesunięcia na osi x.
     * @param dy zmienna zawierająca wartość przesunięcia na osi y.
     */
    public void moveBy(float dx, float dy) {
        getCenter().offset(dx, 0);
        getCenter().offset(0, dy);
    }

}
