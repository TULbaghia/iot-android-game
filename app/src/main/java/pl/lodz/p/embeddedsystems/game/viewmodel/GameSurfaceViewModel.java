package pl.lodz.p.embeddedsystems.game.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class GameSurfaceViewModel extends AndroidViewModel {

    private MutableLiveData<float[]> magnetometerValues;

    private MutableLiveData<float[]> accelerometerValues;

    private MutableLiveData<float[]> orientationValues;

    private MutableLiveData<Boolean> cheatModeEnabled;

    private MutableLiveData<Integer> gainedScore;

    private MutableLiveData<Boolean> isStarted;

    private MutableLiveData<Integer> rotation;

    private MutableLiveData<Integer> significantMotion;

    private MutableLiveData<Integer> stepCounter;


    public GameSurfaceViewModel(@NonNull Application application) {
        super(application);
    }

    public <T> T getNonNullValueOf(@NonNull MutableLiveData<T> item) {
        return Objects.requireNonNull(item.getValue());
    }


    public MutableLiveData<float[]> getMagnetometerValues() {
        if (null == this.magnetometerValues) {
            this.magnetometerValues = new MutableLiveData<>(new float[3]);
        }
        return this.magnetometerValues;
    }

    public MutableLiveData<float[]> getAccelerometerValues() {
        if (null == this.accelerometerValues) {
            this.accelerometerValues = new MutableLiveData<>(new float[3]);
        }
        return this.accelerometerValues;
    }

    public MutableLiveData<float[]> getOrientationValues() {
        if (null == this.orientationValues) {
            this.orientationValues = new MutableLiveData<>(new float[3]);
        }
        return this.orientationValues;
    }

    public MutableLiveData<Boolean> getCheatModeEnabled() {
        if (null == this.cheatModeEnabled) {
            this.cheatModeEnabled = new MutableLiveData<>(false);
        }
        return this.cheatModeEnabled;
    }

    public MutableLiveData<Integer> getGainedScore() {
        if (null == this.gainedScore) {
            this.gainedScore = new MutableLiveData<>(0);
        }
        return this.gainedScore;
    }

    public MutableLiveData<Boolean> getIsStarted(){
        if (null == this.isStarted) {
            this.isStarted = new MutableLiveData<>(false);
        }
        return this.isStarted;
    }

    public MutableLiveData<Integer> getRotation(){
        if (null == this.rotation) {
            this.rotation = new MutableLiveData<>(0);
        }
        return this.rotation;
    }

    public MutableLiveData<Integer> getSignificantMotion(){
        if (null == this.significantMotion) {
            this.significantMotion = new MutableLiveData<>(0);
        }
        return this.significantMotion;
    }

    public MutableLiveData<Integer> getStepCounter(){
        if (null == this.stepCounter) {
            this.stepCounter = new MutableLiveData<>(0);
        }
        return this.stepCounter;
    }
}
