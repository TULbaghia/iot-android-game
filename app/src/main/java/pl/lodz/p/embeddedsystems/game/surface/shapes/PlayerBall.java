package pl.lodz.p.embeddedsystems.game.surface.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import pl.lodz.p.embeddedsystems.game.model.gameobject.GameObject;
import pl.lodz.p.embeddedsystems.game.model.shape.Shape;

/** Kula poruszana przez gracza. */
public class PlayerBall extends Shape implements GameObject {

  /** Pole zawierające promień kuli. */
  private final float radius;

  /**
   * Konstruktor przyjmujący środek figury oraz jej promień.
   *
   * @param x zmienna opisująca współrzędną na osi x.
   * @param y zmienna opisująca współrzędną na osi y.
   * @param style zmienna opisująca styl obiektu.
   * @param radius zmienna opisująca promień kuli.
   */
  public PlayerBall(float x, float y, @NonNull Paint style, float radius) {
    super(x, y, style);
    setMomentumEnabled(true);
    this.radius = radius;
  }

  /** Zwraca promień kuli. */
  public float getRadius() {
    return radius;
  }

  // -=-=-=-=- >>>GameObject -=-=-=-=-

  /**
   * Rysuje kształt na planszy.
   *
   * @param canvas warstwa do rysowania.
   */
  @Override
  public void drawShape(Canvas canvas) {
    canvas.drawCircle(super.getCenterX(), super.getCenterY(), this.radius, super.getStyle());
  }

  // -=-=-=-=- <<<GameObject -=-=-=-=-
}
