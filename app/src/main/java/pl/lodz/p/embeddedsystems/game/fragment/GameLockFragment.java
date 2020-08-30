package pl.lodz.p.embeddedsystems.game.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/** Fragment odpowiadający za odblokowanie rozgrywki przy uruchomieniu aplikacji. */
public class GameLockFragment extends Fragment {

  private GameSurfaceViewModel gameSurfaceViewModel = null;

  // -=-=-=-=- >>>Fragment -=-=-=-=-

  /** Wstępnie inicjuje fragment danymi. */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setRetainInstance(true);

    assert this.getContext() != null;

    this.gameSurfaceViewModel =
        new ViewModelProvider((ViewModelStoreOwner) this.getContext())
            .get(GameSurfaceViewModel.class);
  }

  /** Stworzenie widoku dla fragmentu. */
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.gamelock_fragment, container, false);
  }

  /** Po stworzeniu widoku. Jeżeli gra nie została rozpoczęta tworzymy zadanie dla użytkownika. */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (!this.gameSurfaceViewModel.getNonNullValueOf(this.gameSurfaceViewModel.getIsStarted())) {

      this.gameSurfaceViewModel
          .getOrientationValues()
          .observe(
              this.getViewLifecycleOwner(),
              new Observer<float[]>() {
                @Override
                public void onChanged(float[] orientatedData) {
                  if (checkAzimuthState((int) (Math.toDegrees(orientatedData[0]) + 360) % 360)) {
                    gameSurfaceViewModel.getOrientationValues().removeObserver(this);
                  }
                }
              });

      assert this.getView() != null;

      this.getView().findViewById(R.id.gameLockFragment).setVisibility(View.VISIBLE);
    }
  }

  // -=-=-=-=- <<<Fragment -=-=-=-=-

  /**
   * Sprawdzanie czy urządzenie zostało skierowane na północ.
   *
   * @param azimuth kąt zawarty pomiędzy północną częścią południka, a urządzeniem
   * @return prawdę jeżeli wskazano północ z odchyleniem 10 stopni.
   */
  public Boolean checkAzimuthState(@NonNull Integer azimuth) {
    if (azimuth > 350) {
      this.gameSurfaceViewModel.getIsStarted().setValue(true);

      Toast.makeText(this.getContext(), "Gra rozpoczęta!", Toast.LENGTH_LONG).show();

      assert this.getView() != null;
      this.getView().findViewById(R.id.gameLockFragment).setVisibility(View.GONE);

      return true;
    }
    return false;
  }
}
