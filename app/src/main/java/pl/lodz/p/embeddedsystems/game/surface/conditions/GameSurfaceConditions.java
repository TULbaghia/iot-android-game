package pl.lodz.p.embeddedsystems.game.surface.conditions;

import android.graphics.PointF;

import java.util.concurrent.ThreadLocalRandom;

import pl.lodz.p.embeddedsystems.game.surface.shapes.PlayerBall;
import pl.lodz.p.embeddedsystems.game.surface.shapes.ScoringZone;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Sprawdza warunki gry.
 */
public class GameSurfaceConditions {

    private static final int POINTS_INC = 1;

    private static final int SCORING_ZONE_LIMIT = 100;

    /**
     * Sprawdza żywotność strefy i zmienia jej położenie.
     *
     * @param scoringZone strefa w której ma znaleźć się kulka.
     * @param playerBall kulka sterowana przez gracza
     * @param currentPoints aktualnie posiadane punkty
     * @return prawdę jeżeli kulka jest w strefie
     */
    public static boolean checkRules(ScoringZone scoringZone, PlayerBall playerBall, Integer currentPoints) {
        if (isInRange(scoringZone, playerBall)) {
            final float probability = 0.01f;
            float randomDouble =
                    (float) ThreadLocalRandom.current().nextDouble(0f, 1.0f);
            if (randomDouble < probability) {
                scoringZone.setRadius(
                        (float) ThreadLocalRandom.current().nextDouble(100f, 300f));
                float offset = scoringZone.getRadius();
                scoringZone.setCenter(new PointF( (float) ThreadLocalRandom.current().nextDouble(offset * 2, scoringZone.getAllowedValues().right - offset),
                        (float) ThreadLocalRandom.current().nextDouble(offset, scoringZone.getAllowedValues().bottom - offset))
                );
            }
            return true;
        }
        return false;
    }

    /**
     * Sprawdza czy kula znajduje się w strefie.
     *
     * @param scoringZone strefa w której ma znaleźć się kulka.
     * @param playerBall kulka sterowana przez gracza
     * @return prawdę gdy kulka jest wewnątrz strefy.
     */
    public static boolean isInRange(ScoringZone scoringZone, PlayerBall playerBall) {
        return scoringZone.getRadius() > (
                sqrt(pow(playerBall.getCenterX() - scoringZone.getCenterX(), 2) +
                        pow(playerBall.getCenterY() - scoringZone.getCenterY(), 2)) + playerBall.getRadius());
    }

    /**
     * Zwraca podstawową ilość punktów do przyznania.
     */
    public static int getPointsInc() {
        return POINTS_INC;
    }

// --Commented out by Inspection START (30.08.20 14:04):
//    /**
//     * Zwraca ograniczenie dla stefy.
//     */
//    public static int getScoringZoneLimit() {
//        return SCORING_ZONE_LIMIT;
//    }
// --Commented out by Inspection STOP (30.08.20 14:04)
}
