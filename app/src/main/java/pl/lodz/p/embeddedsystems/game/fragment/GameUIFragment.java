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

import java.util.Objects;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class GameUIFragment extends Fragment {

    GameSurfaceViewModel gameSurfaceViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) Objects.requireNonNull(this.getContext())).get(GameSurfaceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gameui_fragment, container, false);
    }

    //TODO: przeniesc setText do strings.xml
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.gameSurfaceViewModel.getGainedScore()
                .observe(this.getViewLifecycleOwner(), data ->
                        ((TextView) view.findViewById(R.id.gameUI__Score)).setText("Punkty: " + data)
                );

        this.gameSurfaceViewModel.getSignificantMotion()
                .observe(this.getViewLifecycleOwner(), data ->
                        ((TextView) view.findViewById(R.id.gameUI__SignificantMotion)).setText("SM: " + data)
                );
    }
}
