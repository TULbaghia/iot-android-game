package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/** Fragment przeliczający dane z akcelerometru i magnetometru na orientację. */
public class OrientationCalibratorFragment extends Fragment {

  private GameSurfaceViewModel gameSurfaceViewModel = null;

  private Observer<float[]> observer;

  // -=-=-=-=- >>>Fragment -=-=-=-=-

  /** Wstępnie inicjuje fragment danymi. */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setRetainInstance(true);

    assert getContext() != null;

    this.gameSurfaceViewModel =
        new ViewModelProvider((ViewModelStoreOwner) this.getContext())
            .get(GameSurfaceViewModel.class);
    this.observer =
        accelValues ->
            gameSurfaceViewModel
                .getOrientationValues()
                .setValue(
                    calibrateData(
                        gameSurfaceViewModel.getMagnetometerValues().getValue(), accelValues));
  }

  /** Stworzenie widoku dla fragmentu. */
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.sensor_fragment, container, false);
  }

  /** Zatrzymanie obserwatora gdy fragment jest wstrzymany. */
  @Override
  public void onPause() {
    super.onPause();
    this.gameSurfaceViewModel.getAccelerometerValues().removeObserver(this.observer);
  }

  /** Przywracanie/tworzenie obserwatora, gdy fragment jest wykorzystywany. */
  @Override
  public void onResume() {
    super.onResume();
    this.gameSurfaceViewModel
        .getAccelerometerValues()
        .observe(this.getViewLifecycleOwner(), this.observer);
  }

  // -=-=-=-=- <<<Fragment -=-=-=-=-

  /**
   * Przeliczanie danych z magnetometru i akcelerometru na orientację.
   *
   * @param magnetometerValues tablica danych z magnetometru
   * @param accelerometerValues tablica danych z akcelerometru
   * @return orientację na podstawie w/w danych
   */
  private float[] calibrateData(float[] magnetometerValues, float[] accelerometerValues) {
    float[] orientatedData = new float[3];
    if (accelerometerValues != null && magnetometerValues != null) {
      float[] R = new float[9];
      float[] I = new float[9];
      boolean success =
          SensorManager.getRotationMatrix(R, I, accelerometerValues, magnetometerValues);
      if (success) {
        SensorManager.getOrientation(R, orientatedData);
      }
    }
    return orientatedData;
  }
}
