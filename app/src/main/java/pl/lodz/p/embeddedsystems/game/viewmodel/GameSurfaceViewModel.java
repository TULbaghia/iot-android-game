package pl.lodz.p.embeddedsystems.game.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

/** Widok-model zawierający aktalizowane dane z fragmentów czujników, nfc, etc. */
public class GameSurfaceViewModel extends AndroidViewModel {

  public GameSurfaceViewModel(@NonNull Application application) {
    super(application);
  }

  private final MutableLiveData<float[]> magnetometerValues = new MutableLiveData<>(new float[3]);

  private final MutableLiveData<float[]> accelerometerValues = new MutableLiveData<>(new float[3]);

  private final MutableLiveData<float[]> orientationValues = new MutableLiveData<>(new float[3]);

  private final MutableLiveData<Boolean> cheatModeEnabled = new MutableLiveData<>(false);

  private final MutableLiveData<Integer> gainedScore = new MutableLiveData<>(0);

  private final MutableLiveData<Boolean> isStarted = new MutableLiveData<>(false);

  private final MutableLiveData<Integer> rotation = new MutableLiveData<>(0);

  private final MutableLiveData<Integer> significantMotion = new MutableLiveData<>(0);

  private final MutableLiveData<Integer> stepCounter = new MutableLiveData<>(0);

  /**
   * Zapewnia, że wartość zwrócona z gettera nie jest nullem, w przeciwnym wypadku wyrzuca wyjątek.
   *
   * @param item getter zwracający MutableLiveData<T>
   * @param <T> dowolny generyczny typ
   * @return wyjątek gdy wartość jest równa null lub wartość z przekazanego parametru
   */
  public <T> T getNonNullValueOf(@NonNull MutableLiveData<T> item) {
    return Objects.requireNonNull(item.getValue());
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<float[]> getMagnetometerValues() {
    return this.magnetometerValues;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<float[]> getAccelerometerValues() {
    return this.accelerometerValues;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<float[]> getOrientationValues() {
    return this.orientationValues;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<Boolean> getCheatModeEnabled() {
    return this.cheatModeEnabled;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<Integer> getGainedScore() {
    return this.gainedScore;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<Boolean> getIsStarted() {
    return this.isStarted;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<Integer> getRotation() {
    return this.rotation;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<Integer> getSignificantMotion() {
    return this.significantMotion;
  }

  /** Zwraca typ umożliwiający aktualizację/pobieranie danych oraz obserwowanie zmian. */
  public MutableLiveData<Integer> getStepCounter() {
    return this.stepCounter;
  }
}
