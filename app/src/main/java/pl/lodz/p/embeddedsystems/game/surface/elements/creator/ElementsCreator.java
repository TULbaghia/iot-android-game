package pl.lodz.p.embeddedsystems.game.surface.elements.creator;

import android.graphics.Paint;

import pl.lodz.p.embeddedsystems.game.surface.shapes.PlayerBall;
import pl.lodz.p.embeddedsystems.game.surface.shapes.ScoringZone;

public class ElementsCreator {

    public static PlayerBall createBall(float x, float y, Paint style, float radius){
        return new PlayerBall(x, y, style, radius);
    }

    public static ScoringZone createScoringZone(float x, float y, Paint style, float radius){
        return new ScoringZone(x, y, style, radius);
    }

}
