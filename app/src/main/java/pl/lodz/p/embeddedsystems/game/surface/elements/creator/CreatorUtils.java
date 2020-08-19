package pl.lodz.p.embeddedsystems.game.surface.elements.creator;

import android.graphics.Paint;

public class CreatorUtils {

    public static Paint getPaint(Paint.Style style, Integer color) {
        Paint paint = new Paint();
        paint.setStyle(style);
        paint.setColor(color);
        return paint;
    }

}
