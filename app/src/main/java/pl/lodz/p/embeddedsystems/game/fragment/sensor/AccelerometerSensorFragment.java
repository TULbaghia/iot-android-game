package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/** Fragment pobierający dane z akcelerometru. */
public class AccelerometerSensorFragment extends Fragment implements SensorEventListener {

  private SensorManager sensorManager = null;

  private GameSurfaceViewModel gameSurfaceViewModel = null;

  // -=-=-=-=- >>>Fragment -=-=-=-=-

  /** Wstępnie inicjuje fragment danymi. */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setRetainInstance(true);

    assert this.getContext() != null;

    this.sensorManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);
    this.gameSurfaceViewModel =
        new ViewModelProvider((ViewModelStoreOwner) this.getContext())
            .get(GameSurfaceViewModel.class);
  }

  /** Zatrzymanie obserwatora gdy fragment jest wstrzymany. */
  @Override
  public void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this);
  }

  /** Przywracanie/tworzenie obserwatora, gdy fragment jest wykorzystywany. */
  @Override
  public void onResume() {
    super.onResume();
    sensorManager.registerListener(
        this,
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_GAME);
  }

  // -=-=-=-=- <<<Fragment -=-=-=-=-
  // -=-=-=-=- >>>SensorEventListener -=-=-=-=-

  /**
   * Aktualizacja wartości czujnika.
   *
   * @param sensorEvent zdarzenie zawierające informacje o aktywności sensora.
   */
  @Override
  public void onSensorChanged(@NonNull SensorEvent sensorEvent) {
    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      gameSurfaceViewModel.getAccelerometerValues().setValue(applyRotation(sensorEvent.values));
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {}

  // -=-=-=-=- <<<SensorEventListener -=-=-=-=-

  /**
   * Uwzględnienie obrotu ekranu dla danych z akcelerometru.
   *
   * @param accelerometerValues tablica zawierająca dane z akcelerometru.
   * @return tablicę akcelerometru z zastosowaną rotacją.
   */
  private float[] applyRotation(float[] accelerometerValues) {
    float tmp;
    switch (gameSurfaceViewModel.getNonNullValueOf(gameSurfaceViewModel.getRotation())) {
      case Surface.ROTATION_90:
        tmp = accelerometerValues[0];
        accelerometerValues[0] = -accelerometerValues[1];
        accelerometerValues[1] = tmp;
        break;
      case Surface.ROTATION_270:
        tmp = accelerometerValues[0];
        accelerometerValues[0] = accelerometerValues[1];
        accelerometerValues[1] = -tmp;
        break;
      case Surface.ROTATION_180:
      default:
        break;
    }
    return accelerometerValues;
  }
}
