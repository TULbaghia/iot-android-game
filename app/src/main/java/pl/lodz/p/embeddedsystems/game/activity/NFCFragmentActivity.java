package pl.lodz.p.embeddedsystems.game.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import pl.lodz.p.embeddedsystems.MainActivity;
import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.surface.GameSurfaceView;

public class NFCFragmentActivity extends Fragment implements PropertyChangeListener {

    GameSurfaceView gameSurfaceView = null;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) Objects.requireNonNull(this.getContext())).addPropertyChangeListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nfc_fragment, container, false);
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
    // -=-=-=-=- >>>PropertyChangeListener -=-=-=-=-

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getSource() == this.getContext()
                && propertyChangeEvent.getPropertyName().equals(Objects.requireNonNull(this.getContext()).getClass().getName())
                && propertyChangeEvent.getNewValue().equals("onCreate")) {
            MainActivity mainActivity = (MainActivity) this.getContext();

            this.gameSurfaceView = (GameSurfaceView) mainActivity.findViewById(R.id.GameSurfaceView);
        }
    }
    // -=-=-=-=- <<<PropertyChangeListener -=-=-=-=-
}
