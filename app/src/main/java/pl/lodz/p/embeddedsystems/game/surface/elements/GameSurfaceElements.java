package pl.lodz.p.embeddedsystems.game.surface.elements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.concurrent.ThreadLocalRandom;

import pl.lodz.p.embeddedsystems.game.surface.elements.creator.CreatorUtils;
import pl.lodz.p.embeddedsystems.game.surface.elements.creator.ElementsCreator;
import pl.lodz.p.embeddedsystems.game.surface.conditions.GameSurfaceConditions;
import pl.lodz.p.embeddedsystems.game.surface.shapes.PlayerBall;
import pl.lodz.p.embeddedsystems.game.surface.shapes.ScoringZone;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/**
 * Zawiera oraz zarządza elementami na planszy gry.
 */
public class GameSurfaceElements {
    /**
     * Kształt gracza.
     */
    private PlayerBall playerBall;

    /**
     * Kształt punktowanej strefy.
     */
    private ScoringZone scoringZone;

    /**
     * Referencja do planszy gry.
     */
    private final GameSurfaceViewModel gameSurfaceViewModel;

    /**
     * Parametry wyświetlania, funkcje: konstruowanie obiektów, ustalanie pozycji.
     */
    private final DisplayMetrics displayMetrics;

    /**
     * Poprzednia wartość rotacji ekranu.
     */
    private int lastKnownRotation;

    /**
     * Inicjuje dane w obiekcie.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     */
    public GameSurfaceElements(@NonNull Context context) {
        gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(GameSurfaceViewModel.class);
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        prepareGameObjects();
        setObservers(context);

        // możliwe scenariusze startowe:
        // a) sytuacja gdy podczas ładowania gry na starcie (biały ekran przed narysowaniem pierwszej klatki) zmieniono orientację (widoczna zmiana np. na górnym toolbarze w androidzie),
        // - jest to sytuacja typowa, gdy aplikacja startuje na telefonie
        // b) sytuacja gdy już podczas ładowania ustawiony jest tryb landscape (np. podczas debuga)

        // sprawdzenie, czy tryb "landscape" byl wlaczony juz przed wlaczeniem aplikacji
        lastKnownRotation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();

        // jesli tak, to chcemy aby zaraz po utworzeniu obiektów gry względem trybu "portrait"
        // została triggerowana akcja zmiany rotacji ekranu na "landscape" lub "reverse landscape", a zatem dokonane odpowiednie przekształcenie
        // wszystko to ma na celu uzyskanie sytuacji, w której dokonujemy rotacji punktu względem defaultowego trybu "portrait"
        if (lastKnownRotation != Surface.ROTATION_0) {
            gameSurfaceViewModel.getRotation().setValue(lastKnownRotation);
        }
    }

    /**
     * Ustala kolejnych obserwatorów.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     */
    private void setObservers(@NonNull Context context) {
        setRotationObserver(context);
        setPlayerObserver(context);
        setNfcActionObserver(context);
    }

    /**
     * Ustawienie obserwatora położenia pozycji gracza.
     * Przelicza punkty.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     */
    private void setPlayerObserver(@NonNull Context context) {
        this.gameSurfaceViewModel.getOrientationValues()
                .observe((LifecycleOwner) context, orientatedData -> {
                    if (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getIsStarted())) {
                        getPlayer().moveBy(orientatedData[2], orientatedData[1]);
                        if (GameSurfaceConditions.checkRules(scoringZone, playerBall, this.gameSurfaceViewModel.getGainedScore().getValue())) {
                            float steps = this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getStepCounter()) / 200f;

                            int significantMotion = this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getSignificantMotion());

                            int newPoints = (int) (GameSurfaceConditions.getPointsInc() * Math.max(1, steps - significantMotion));

                            this.gameSurfaceViewModel.getGainedScore().setValue(
                                    this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getGainedScore()) + newPoints
                            );
                        }
                    }
                });
    }

    /**
     * Ustawienie akcji dla trybu oszusta.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     */
    private void setNfcActionObserver(@NonNull Context context) {
        this.gameSurfaceViewModel.getCheatModeEnabled()
                .observe((LifecycleOwner) context, isCheatMode -> {
                    if (isCheatMode) {
                        playerBall.setMomentumEnabled(false);
                    } else {
                        playerBall.setMomentumEnabled(true);
                    }
                });
    }

    /**
     * Ustawienie obserwatora rotacji ekranu urządzenia.
     *
     * @param context kontekst aplikacji (obiekt aktywności)
     */
    private void setRotationObserver(@NonNull Context context) {
        this.gameSurfaceViewModel.getRotation()
                .observe((LifecycleOwner) context, rotation -> {

                            ((Activity) context).getWindowManager()
                                    .getDefaultDisplay()
                                    .getMetrics(displayMetrics);

                            if (rotation == Surface.ROTATION_0 && lastKnownRotation != Surface.ROTATION_0) {
                                if (lastKnownRotation == Surface.ROTATION_90) {
                                    getScoringZone().setCenter(new PointF(displayMetrics.heightPixels - getScoringZone().getCenterY(), getScoringZone().getCenterX()));
                                    getPlayer().setCenter(new PointF(displayMetrics.heightPixels - getPlayer().getCenterY(), getPlayer().getCenterX()));
                                    swapAllowedValues();
                                } else if (lastKnownRotation == Surface.ROTATION_270) {
                                    getScoringZone().setCenter(new PointF(getScoringZone().getCenterY(), displayMetrics.widthPixels - getScoringZone().getCenterX()));
                                    getPlayer().setCenter(new PointF(getPlayer().getCenterY(), displayMetrics.widthPixels - getPlayer().getCenterX()));
                                    swapAllowedValues();
                                }

//                            } else if (rotation == Surface.ROTATION_180 && lastKnownRotation != Surface.ROTATION_180) {
                                //
                            } else if (rotation == Surface.ROTATION_90 && lastKnownRotation != Surface.ROTATION_90) {
                                if (lastKnownRotation == Surface.ROTATION_0) {
                                    getScoringZone().setCenter(new PointF(getScoringZone().getCenterY(), displayMetrics.widthPixels - getScoringZone().getCenterX()));
                                    getPlayer().setCenter(new PointF(getPlayer().getCenterY(), displayMetrics.widthPixels - getPlayer().getCenterX()));
                                    swapAllowedValues();
                                } else {
                                    getScoringZone().setCenter(new PointF(displayMetrics.widthPixels - getScoringZone().getCenterX(), displayMetrics.heightPixels - getScoringZone().getCenterY()));
                                    getPlayer().setCenter(new PointF(displayMetrics.widthPixels - getPlayer().getCenterX(), displayMetrics.heightPixels - getPlayer().getCenterY()));
                                }
                            } else if (rotation == Surface.ROTATION_270 && lastKnownRotation != Surface.ROTATION_270) {
                                if (lastKnownRotation == Surface.ROTATION_0) {
                                    getScoringZone().setCenter(new PointF(displayMetrics.heightPixels - getScoringZone().getCenterY(), getScoringZone().getCenterX()));
                                    getPlayer().setCenter(new PointF(displayMetrics.heightPixels - getPlayer().getCenterY(), getPlayer().getCenterX()));
                                    swapAllowedValues();
                                } else {
                                    getScoringZone().setCenter(new PointF(displayMetrics.widthPixels - getScoringZone().getCenterX(), displayMetrics.heightPixels - getScoringZone().getCenterY()));
                                    getPlayer().setCenter(new PointF(displayMetrics.widthPixels - getPlayer().getCenterX(), displayMetrics.heightPixels - getPlayer().getCenterY()));
                                }
                            }
                            lastKnownRotation = rotation;

                        }
                );
    }

    /**
     * Zwraca obiekt gracza.
     */
    public PlayerBall getPlayer() {
        return playerBall;
    }

    /**
     * Zwraca obiekt strefy.
     */
    public ScoringZone getScoringZone() {
        return scoringZone;
    }

    private void swapAllowedValues() {
        if (playerBall.getAllowedValues().right == displayMetrics.heightPixels && playerBall.getAllowedValues().bottom == displayMetrics.widthPixels) {
            scoringZone.setMaxValues(displayMetrics.widthPixels, displayMetrics.heightPixels);
            playerBall.setMaxValues(displayMetrics.widthPixels, displayMetrics.heightPixels);
        } else {
            scoringZone.setMaxValues(displayMetrics.heightPixels, displayMetrics.widthPixels);
            playerBall.setMaxValues(displayMetrics.heightPixels, displayMetrics.widthPixels);
        }
    }

    /**
     * Przygotowuje obiekty na planszy.
     */
    private void prepareGameObjects() {
        createPlayer(displayMetrics.widthPixels / 2f, displayMetrics.heightPixels / 2f, 70);
        createScoringZone(displayMetrics.widthPixels / 3f, displayMetrics.heightPixels / 5f, 160);
        setMaxAllowedValues();
    }

    /**
     * Tworzy obiekt poruszany przez gracza.
     *
     * @param x      współrzędna osi X
     * @param y      współrzędma pso Y
     * @param radius promień obiektu
     */
    public void createPlayer(float x, float y, float radius) {
        playerBall = ElementsCreator.createBall(x, y,
                CreatorUtils.getPaint(Paint.Style.FILL, Color.RED),
                radius);
    }

    /**
     * Tworzy strefę na planszy gry.
     *
     * @param x      współrzędna osi X
     * @param y      współrzędma pso Y
     * @param radius promień obiektu
     */
    public void createScoringZone(float x, float y, float radius) {
        scoringZone = ElementsCreator.createScoringZone(x, y,
                CreatorUtils.getPaint(Paint.Style.FILL, Color.YELLOW),
                radius);
    }

    /**
     * Ustawia dozwolone wartości dla obiektów gry.
     */
    private void setMaxAllowedValues() {
        scoringZone.setMaxValues(displayMetrics.widthPixels, displayMetrics.heightPixels);
        playerBall.setMaxValues(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    /**
     * Łapie zdarzenie dotknięcia ekranu i je obsługuje.
     */
    public void onTouchEvent() {
        if (this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getCheatModeEnabled())) {
            this.gameSurfaceViewModel.getGainedScore().setValue(
                    this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getGainedScore())
                            + ThreadLocalRandom.current().nextInt(80) + 20
            );
        }
    }
}
