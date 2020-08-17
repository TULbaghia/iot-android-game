package pl.lodz.p.embeddedsystems.game.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Objects;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

public class GameLockFragment extends Fragment {

    GameSurfaceViewModel gameSurfaceViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) Objects.requireNonNull(this.getContext())).get(GameSurfaceViewModel.class);

        this.gameSurfaceViewModel.getOrientationValues()
                .observe((LifecycleOwner) this.getContext(), new Observer<float[]>() {
                    @Override
                    public void onChanged(float[] orientatedData) {
                        if (checkAzimuthState((int) (Math.toDegrees(orientatedData[0]) + 360) % 360)) {
                            gameSurfaceViewModel.getOrientationValues().removeObserver(this);
                        }
                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gamelock_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) Objects.requireNonNull(this.getView()).findViewById(R.id.lockStateText)).setText("Skieruj urządzenie na północ aby rozpocząć.");
    }

    public Boolean checkAzimuthState(Integer azimuth) {
        if (azimuth > 350) {
            this.gameSurfaceViewModel.getIsStarted().setValue(true);
            Toast.makeText(this.getContext(), "Gra rozpoczęta!", Toast.LENGTH_LONG).show();
            ((TextView) Objects.requireNonNull(this.getView()).findViewById(R.id.lockStateText)).setVisibility(View.GONE);
            return true;
        }
        return false;
    }
}
