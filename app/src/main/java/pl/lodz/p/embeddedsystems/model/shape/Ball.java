package pl.lodz.p.embeddedsystems.model.shape;

/**
 * Klasa zawierająca opis kulki, którą poruszamy się po ekranie.
 */
public class Ball extends Shape {
    /**
     * Promień kuli.
     */
    private float radius;

    /**
     * Konstruktor przyjmujący środek figury oraz jej promień.
     * @param x zmienna opisująca współrzędną na osi x.
     * @param y zmienna opisująca współrzędną na osi y.
     * @param radius zmienna opisująca promień kuli.
     */
    public Ball(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
    }

    /**
     * Zwraca promień kuli.
     * @return promień kuli.
     */
    public float getRadius() {
        return radius;
    }
}
