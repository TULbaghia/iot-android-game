package pl.lodz.p.embeddedsystems.game.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GameSurfaceViewModel extends AndroidViewModel {

    private MutableLiveData<float[]> magnetometerValues;

    private MutableLiveData<float[]> accelerometerValues;

    private MutableLiveData<Boolean> cheatModeEnabled;


    public GameSurfaceViewModel(@NonNull Application application) {
        super(application);
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

    public MutableLiveData<Boolean> getCheatModeEnabled() {
        if (null == this.cheatModeEnabled) {
            this.cheatModeEnabled = new MutableLiveData<>(false);
        }
        return cheatModeEnabled;
    }
}
