package pl.lodz.p.embeddedsystems.game.surface.elements.creator;

import android.graphics.Paint;

import androidx.annotation.NonNull;

import pl.lodz.p.embeddedsystems.game.surface.shapes.PlayerBall;
import pl.lodz.p.embeddedsystems.game.surface.shapes.ScoringZone;

/**
 * Kretor elementów wyświetlanych na planszy.
 */
public class ElementsCreator {

    /**
     * Zwraca obiekt piłki.
     *
     * @param x współrzędna osi X
     * @param y współrzędna osi Y
     * @param style styl obiektu
     * @param radius promień obiektu
     * @return nowy obiekt stworzony z w/w parametrów
     */
    public static PlayerBall createBall(float x, float y, @NonNull Paint style, float radius){
        return new PlayerBall(x, y, style, radius);
    }

    /**
     * Zwraca obiekt strefy.
     *
     * @param x współrzędna osi X
     * @param y współrzędna osi Y
     * @param style styl obiektu
     * @param radius promień obiektu
     * @return nowy obiekt stworzony z w/w parametrów
     */
    public static ScoringZone createScoringZone(float x, float y, @NonNull Paint style, float radius){
        return new ScoringZone(x, y, style, radius);
    }

}
