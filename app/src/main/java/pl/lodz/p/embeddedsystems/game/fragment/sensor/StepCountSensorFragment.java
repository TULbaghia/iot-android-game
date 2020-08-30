package pl.lodz.p.embeddedsystems.game.fragment.sensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/** Fragment zwracający liczbę kroków od ostatniego uruchomienia urządzenia. */
public class StepCountSensorFragment extends Fragment implements SensorEventListener {

  private SensorManager sensorManager = null;

  private GameSurfaceViewModel gameSurfaceViewModel = null;

  // -=-=-=-=- >>>Fragment -=-=-=-=-

  /** Wstępnie inicjuje fragment danymi. */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setRetainInstance(true);

    assert getContext() != null;

    this.sensorManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);
    this.gameSurfaceViewModel =
        new ViewModelProvider((ViewModelStoreOwner) this.getContext())
            .get(GameSurfaceViewModel.class);

    if (ContextCompat.checkSelfPermission(
            this.getContext(), Manifest.permission.ACTIVITY_RECOGNITION)
        != PackageManager.PERMISSION_GRANTED) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ActivityCompat.requestPermissions(
            (Activity) this.getContext(),
            new String[] {Manifest.permission.ACTIVITY_RECOGNITION},
            1);
      }
    }
  }

  /** Zatrzymanie obserwatora gdy fragment jest wstrzymany. */
  @Override
  public void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this);
  }

  /**
   * Przywracanie/tworzenie obserwatora, gdy fragment jest wykorzystywany. Dodatkowo sprawdzanie
   * uprawnień.
   */
  @Override
  public void onResume() {
    super.onResume();

    assert getContext() != null;

    sensorManager.registerListener(
        this,
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
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
  public void onSensorChanged(SensorEvent sensorEvent) {
    if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
      gameSurfaceViewModel.getStepCounter().setValue((int) sensorEvent.values[0]);
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {}

  // -=-=-=-=- <<<SensorEventListener -=-=-=-=-
}
