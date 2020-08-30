package pl.lodz.p.embeddedsystems.game.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.panoramagl.PLImage;
import com.panoramagl.PLManager;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.utils.PLUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pl.lodz.p.embeddedsystems.R;

/** Fragment wyświetlający panoramę jako tło aplikacji. */
public class PanoramaFragment extends Fragment {

  // -=-=-=-=- >>>Fragment -=-=-=-=-

  /** Wstępnie inicjuje fragment danymi. */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setRetainInstance(true);
  }

  /** Tworzy widok panoramy z szablonu panorama_fragment. */
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.panorama_fragment, container, false);
  }

  /** Po utworzeniu widoku, rozpoczyna inicjalizację panoramy. */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupPanorama(view);
  }

  /**
   * Inicjalizacja panoramy.
   *
   * @param view widok dla którego ustawić panoramę
   */
  private void setupPanorama(View view) {
    PLManagerWrapper plManager = new PLManagerWrapper(this.getContext());
    plManager.setContentView((ViewGroup) view);
    plManager.onCreate();

    plManager.invokeActivateOrientation();
    plManager.setAccelerometerEnabled(false);
    plManager.setInertiaEnabled(false);
    plManager.setZoomEnabled(false);

    plManager.startSensorialRotation();

    PLSphericalPanorama panorama = new PLSphericalPanorama();
    panorama.setImage(new PLImage(PLUtils.getBitmap(this.getContext(), R.raw.galaxy), false));
    float pitch = 5f;
    float yaw = 0f;
    float zoomFactor = 0.8f;

    panorama.getCamera().lookAtAndZoomFactor(pitch, yaw, zoomFactor, false);
    plManager.setPanorama(panorama);
  }

  // -=-=-=-=- <<<Fragment -=-=-=-=-

  /**
   * Opakowuje klasę biblioteczną panoramy. Umożliwia zmianę orientacji panoramy przy zmianie
   * orientacji ekranu.
   */
  private static class PLManagerWrapper extends PLManager {
    public PLManagerWrapper(Context context) {
      super(context);
    }

    public void invokeActivateOrientation() {
      try {
        Method m = PLManager.class.getDeclaredMethod("activateOrientation");
        m.invoke(this);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
