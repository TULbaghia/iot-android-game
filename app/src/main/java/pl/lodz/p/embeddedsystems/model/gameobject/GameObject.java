package pl.lodz.p.embeddedsystems.model.gameobject;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Interfejs, który implementują wszystkie obiekty podlegające aktualizacji w czasie gry.
 */
public interface GameObject {
    /**
     * Rysowanie wyznaczonego kształtu.
     * @param canvas przyjmuje warstwę dla obiektu.
     */
    void drawShape(Canvas canvas);

    /**
     * Aktualizacja stanu wczytanego obiektu.
     */
    void update(PointF point);
}
