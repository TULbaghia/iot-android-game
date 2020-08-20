package pl.lodz.p.embeddedsystems.game.surface.elements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.game.surface.elements.creator.CreatorUtils;
import pl.lodz.p.embeddedsystems.game.surface.elements.creator.ElementsCreator;
import pl.lodz.p.embeddedsystems.game.surface.shapes.PlayerBall;
import pl.lodz.p.embeddedsystems.game.surface.shapes.ScoringZone;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class GameSurfaceElements {
    /**
     * Obiekt-kształt gracza.
     */
    private PlayerBall playerBall;

    /**
     * Obiekt-kształt punktowanej strefy.
     */
    private ScoringZone scoringZone;

    /**
     * Referencja do planszy gry.
     */
    GameSurfaceViewModel gameSurfaceViewModel;

    /**
     * Poprzednia wartość rotacji ekranu.
     */
    int lastKnownRotation;

    /**
     * Parametry wyświetlania, funkcje: konstruowanie obiektów, ustalanie pozycji.
     */
    DisplayMetrics displayMetrics;

    public GameSurfaceElements(@NonNull Context context) {
        gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(GameSurfaceViewModel.class);
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        prepareGameObjects();
        setObservers(context);
    }


    private void setObservers(@NonNull Context context) {
        setRotationObserver(context);
        setPlayerObserver(context);

    }

    /**
     * Obserwator położenia pozycji gracza.
     */
    private void setPlayerObserver(Context context) {
        this.gameSurfaceViewModel.getOrientationValues()
                .observe((LifecycleOwner) context, orientatedData ->
                        getPlayer().moveBy(15 * orientatedData[2],  -15 * orientatedData[1]));
    }

    /**
     * Obserwator aktualnej rotacji ekranu urządzenia.
     */
    private void setRotationObserver(Object context) {
        this.gameSurfaceViewModel.getRotation()
                .observe((LifecycleOwner) context, rotation -> {

                    ((Activity) context).getWindowManager()
                            .getDefaultDisplay()
                            .getMetrics(displayMetrics);

                            if (rotation == Surface.ROTATION_0 && lastKnownRotation != Surface.ROTATION_0) {
                                System.out.println("ROT0");
                            } else if (rotation == Surface.ROTATION_180 && lastKnownRotation != Surface.ROTATION_180) {
                                //
                            } else if (rotation == Surface.ROTATION_90 && lastKnownRotation != Surface.ROTATION_90) {
                                System.out.println("ROT90");
                            } else if (rotation == Surface.ROTATION_270 && lastKnownRotation != Surface.ROTATION_270) {
                                System.out.println("ROT270");
                            }
                            lastKnownRotation = rotation;

                        }
                );
    }

    private void prepareGameObjects() {
        createPlayer(displayMetrics.widthPixels / 2f, displayMetrics.heightPixels, 70);
        createScoringZone(displayMetrics.widthPixels / 2f, displayMetrics.heightPixels / 2f, 160);
        setMaxAllowedValues();
    }

    public void createPlayer(float x, float y, float radius) {
        playerBall = ElementsCreator.createBall(x, y,
                CreatorUtils.getPaint(Paint.Style.FILL, Color.RED),
                radius);
    }

    public void createScoringZone(float x, float y, float radius) {
        scoringZone = ElementsCreator.createScoringZone(x, y,
                CreatorUtils.getPaint(Paint.Style.FILL, Color.YELLOW),
                radius);
    }

    public PlayerBall getPlayer() {
        return playerBall;
    }

    public ScoringZone getScoringZone() {
        return scoringZone;
    }

//    private void setReversedMaxAllowedValues() {
//        scoringZone.setMaxValues(displayMetrics.heightPixels, displayMetrics.widthPixels);
//        playerBall.setMaxValues(displayMetrics.heightPixels, displayMetrics.widthPixels);
//    }

    private void setMaxAllowedValues() {
        scoringZone.setMaxValues(displayMetrics.widthPixels, displayMetrics.heightPixels);
        playerBall.setMaxValues(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

}
