package pl.lodz.p.embeddedsystems.game.surface.conditions;

import android.graphics.PointF;

import java.util.concurrent.ThreadLocalRandom;

import pl.lodz.p.embeddedsystems.game.surface.shapes.PlayerBall;
import pl.lodz.p.embeddedsystems.game.surface.shapes.ScoringZone;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GameSurfaceConditions {

    public static final int POINTS_INC = 1;

    public static final int SCORING_ZONE_LIMIT = 100;

    public static boolean checkRules(ScoringZone scoringZone, PlayerBall playerBall, Integer currentPoints) {
        if (isInRange(scoringZone, playerBall)) {
            if ((currentPoints + POINTS_INC) % SCORING_ZONE_LIMIT == 0) {
                float offset = scoringZone.getRadius();
                scoringZone.setCenter(new PointF( (float) ThreadLocalRandom.current().nextDouble(offset, scoringZone.getAllowedValues().right - offset),
                        (float) ThreadLocalRandom.current().nextDouble(offset, scoringZone.getAllowedValues().bottom - offset))
                );
            }
            return true;
        }
        return false;
    }

    public static boolean isInRange(ScoringZone scoringZone, PlayerBall playerBall) {
        return scoringZone.getRadius() > (
                sqrt(pow(playerBall.getCenterX() - scoringZone.getCenterX(), 2) +
                        pow(playerBall.getCenterY() - scoringZone.getCenterY(), 2)) + playerBall.getRadius());
    }


    public static int getPointsInc() {
        return POINTS_INC;
    }

    public static int getScoringZoneLimit() {
        return SCORING_ZONE_LIMIT;
    }
}
