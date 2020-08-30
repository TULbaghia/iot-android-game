package pl.lodz.p.embeddedsystems.game.surface.elements.creator;

import android.graphics.Paint;

import androidx.annotation.NonNull;

/** Użyteczne funkcje dla kreatora elementów. */
public class CreatorUtils {

  /**
   * Tworzy obiekt typu Paint o określonym stylu oraz kolorze.
   *
   * @param style styl przypisany do obiektu
   * @param color kolor przypisany do obiektu
   * @return obiekt Paint zawierający podany styl oraz kolor
   */
  public static Paint getPaint(@NonNull Paint.Style style, @NonNull Integer color) {
    Paint paint = new Paint();
    paint.setStyle(style);
    paint.setColor(color);
    return paint;
  }
}
