package pl.lodz.p.embeddedsystems.game.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GameSurfaceViewModel extends AndroidViewModel {

    private MutableLiveData<float[]> magnetometerValues;

    private MutableLiveData<float[]> accelerometerValues;


    public GameSurfaceViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<float[]> getMagnetometerValues() {
        if (this.magnetometerValues == null) {
            this.magnetometerValues = new MutableLiveData<>();
        }
        return this.magnetometerValues;
    }

    public MutableLiveData<float[]> getAccelerometerValues() {
        if (this.accelerometerValues == null) {
            this.accelerometerValues = new MutableLiveData<>();
        }
        return this.accelerometerValues;
    }
}
