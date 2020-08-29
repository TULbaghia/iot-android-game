package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.content.Context;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/**
 * Fragment wyznaczający obrót ekranu.
 */
public class RotationFragment extends Fragment {

    private GameSurfaceViewModel gameSurfaceViewModel = null;

    private OrientationEventListener orientationEventListener = null;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    /**
     * Wstępnie inicjuje fragment danymi oraz obserwatora zmiany obrotu ekranu.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        assert getContext() != null;

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);
        this.orientationEventListener = new OrientationEventListener(this.getContext()) {
            @Override
            public void onOrientationChanged(int i) {
                assert getContext() != null;
                gameSurfaceViewModel.getRotation().setValue(
                        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                                .getDefaultDisplay().getRotation()
                );
            }
        };
    }

    /**
     * Zatrzymanie obserwatora gdy fragment jest wstrzymany.
     */
    @Override
    public void onPause() {
        super.onPause();
        orientationEventListener.disable();
    }

    /**
     * Przywracanie/tworzenie obserwatora, gdy fragment jest wykorzystywany.
     */
    @Override
    public void onResume() {
        super.onResume();
        orientationEventListener.enable();
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
}
