package pl.lodz.p.embeddedsystems.game.surface.elements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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

    public GameSurfaceElements(@NonNull Context context) {
        gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(GameSurfaceViewModel.class);
        preparePlayerElement(context);
    }

    private void preparePlayerElement(@NonNull Context context) {
        createPlayer();
        setPlayerObserver(context);
    }

    /**
     * Obserwator położenia pozycji gracza.
     */
    private void setPlayerObserver(Context context) {
        this.gameSurfaceViewModel.getOrientationValues()
                .observe((LifecycleOwner) context, orientatedData ->
                        getPlayer().moveBy(orientatedData[2],  -1 * orientatedData[1]));
    }

    public void createPlayer() {
        // Test
        playerBall = new PlayerBall(250f, 500f, new Paint(Color.BLACK), 70);
    }

    public void createScoringZone() {
        //
    }

    public PlayerBall getPlayer() {
        return playerBall;
    }

    public ScoringZone getScoringZone() {
        return scoringZone;
    }

}
