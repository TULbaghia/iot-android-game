package pl.lodz.p.embeddedsystems.game.model.shape;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Klasa reprezentująca kształt na planszy gry.
 */
public abstract class Shape {
    /**
     * Zawierają środek kształtu.
     */
    private PointF center;

    /**
     * Zawierająca styl obiektu.
     */
    private Paint style;

    /**
     * Zawiera dozwolone wartości dla punktu środka.
     */
    private RectF allowedValues;

    /**
     * Aktualne wartości pędu.
     */
    private float[] momentum = {0f, 0f};

    /**
     * Stan aktywacji funkcji zachowania pędu.
     */
    private boolean momentumEnabled = false;

    /**
     * Mnożnik momentu pędu.
     */
    private final float momentumFactor = 1f;

    /**
     * Konstruktor przyjmujący środek figury oraz styl.
     *
     * @param x     zmienna opisująca współrzędną na osi x.
     * @param y     zmienna opisująca współrzędną na osi y.
     * @param style zmienna zawierająca pełny styl obiektu.
     */
    public Shape(float x, float y, Paint style) {
        this.center = new PointF(x, y);
        this.style = style;

        this.allowedValues = new RectF();
        setMinValues(0, 0);
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
     * Ustawienie stanu aktywacji funkcji zachowania pędu.
     *
     * @param momentumEnabled czy zachowanie pędu ma być aktywne
     */
    public void setMomentumEnabled(boolean momentumEnabled) {
        this.momentumEnabled = momentumEnabled;
    }

    /**
     * Zwraca styl obiektu
     */
    public Paint getStyle() {
        return this.style;
    }

    /**
     * Zwraca środek kształtu w danej orientacji.
     */
    public PointF getCenter() {
        return center;
    }

    /**
     * Zwraca współrzędną x środka obiektu w danej orientacji.
     */
    public float getCenterX() {
        return this.center.x;
    }

    /**
     * Zwraca współrzędną y środka obiektu w danej orientacji.
     */
    public float getCenterY() {
        return this.center.y;
    }

    /**
     * Przesuwa centrum kształtu o podaną wartość.
     *
     * @param dx zmienna zawierająca wartość przesunięcia na osi x.
     * @param dy zmienna zawierająca wartość przesunięcia na osi y.
     */
    public void moveBy(float dx, float dy) {
        if (momentumEnabled) {
            momentum[0] += momentumFactor * dx;
            momentum[1] += -momentumFactor * dy;

            if (isInRange(this.center.x + momentum[0], this.center.y)) {
                this.center.offset(momentum[0], 0);
            } else {
                momentum[0] = 0f;
            }
            if (isInRange(this.center.x, this.center.y + momentum[1])) {
                this.center.offset(0, momentum[1]);
            } else {
                momentum[1] = 0f;
            }
        } else {
            if (isInRange(this.center.x + dx*20, this.center.y)) {
                this.center.offset(dx*20, 0);
            }
            if (isInRange(this.center.x, this.center.y + -dy*20)) {
                this.center.offset(0, -dy*20);
            }
        }
    }

    /**
     * Ustawia maksymalne dozwolone wartości wartości.
     *
     * @param maxWidth maksymalna wartość na osi x.
     * @param maxHeigh maksymalna wartość na osi y.
     */
    public void setMaxValues(float maxWidth, float maxHeigh) {
        allowedValues.right = maxWidth;
        allowedValues.bottom = maxHeigh;
    }


    /**
     * Ustawia minimalne dozwolone wartości.
     *
     * @param minWidth minimalna wartość na osi x.
     * @param minHeigh minimalna wartość na osi y.
     */
    public void setMinValues(float minWidth, float minHeigh) {
        allowedValues.left = minWidth;
        allowedValues.top = minHeigh;
    }

    /**
     * Zwraca wartości ograniczające obszar gry.
     *
     * @return ograniczające wartości obszaru gry.
     */
    public RectF getAllowedValues() {
        return allowedValues;
    }

    /**
     * Sprawdza czy punkt znajduje się w dozwolonych wartościach.
     *
     * @param x wartość na osi x
     * @param y wartość na osi y
     * @return prawdę gdy zawiera się w wyznaczonej powierzchni.
     */
    public boolean isInRange(float x, float y) {
        return this.allowedValues == null || this.allowedValues.contains(x, y);
    }

}
