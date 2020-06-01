package pl.lodz.p.embeddedsystems.model.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Score extends Shape {
    /**
     * Wypełnienie tekstu.
     */
    private Paint style;

    /**
     * Ilość punktów.
     */
    private Integer score = 0;

    /**
     * Konstruktor przyjmujący środek figury.
     *
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     */
    public Score(float x, float y, Paint style) {
        super(x, y);
        style.setTextSize(50);
        this.style = style;
    }
    
    public void drawShape(Canvas canvas) {
        canvas.drawText("TOTAL: " + score.toString(), super.getCenterX(), super.getCenterY(), style);
    }

    /**
     * Aktualizacja wyniku gracza.
     */
    public void update() {
        score++;
    }

}
