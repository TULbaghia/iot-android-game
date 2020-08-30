package pl.lodz.p.embeddedsystems.game.model.gameobject;

import android.graphics.Canvas;

/** Interfejs implementowany przez wszystkie obiekty podlegające aktualizacji w czasie gry. */
public interface GameObject {
  /**
   * Rysowanie wyznaczonego kształtu.
   *
   * @param canvas warstwa do rysowania.
   */
  void drawShape(Canvas canvas);
}
