package pl.lodz.p.embeddedsystems.game.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.text.MessageFormat;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/**
 * Fragment realizujący interfejs użytkownika.
 */
public class GameUIFragment extends Fragment {

    private GameSurfaceViewModel gameSurfaceViewModel = null;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    /**
     * Wstępnie inicjuje fragment danymi.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        assert this.getContext() != null;

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);
    }

    /**
     * Stworzenie widoku dla fragmentu.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gameui_fragment, container, false);
    }


    /**
     * Po stworzeniu widoku.
     * Dodanie elementów interfejsu jako obserwatorów.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.gameSurfaceViewModel.getGainedScore().observe(this.getViewLifecycleOwner(), data ->
                ((TextView) view.findViewById(R.id.gameUI__Score)).setText(
                        MessageFormat.format("{0}: {1}", getString(R.string.uiPoints), data)
                )
        );

        this.gameSurfaceViewModel.getStepCounter().observe(this.getViewLifecycleOwner(), data ->
                ((TextView) view.findViewById(R.id.gameUI__StepCounter)).setText(
                        MessageFormat.format("{0}: {1}", getString(R.string.uiStep), data)
                )
        );

        this.gameSurfaceViewModel.getSignificantMotion().observe(this.getViewLifecycleOwner(), data ->
                ((TextView) view.findViewById(R.id.gameUI__SignificantMotion)).setText(
                        MessageFormat.format("{0}: {1}", getString(R.string.uiSigMo), data)
                )
        );
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
}
