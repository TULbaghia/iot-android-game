package pl.lodz.p.embeddedsystems;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

/**
 * Główna aktywność aplikacji.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Propowanie xml odbywa się przed onCreate głównej aktywności,
     * używamy tego aby powiadomić obserwatorów.
     */
    PropertyChangeSupport propertyChangeSupport;

    /**
     * Przy instancjonowaniu klasy tworzymy bean'a.
     */
    public MainActivity() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    // -=-=-=-=- >>>AppCompatActivity -=-=-=-=-

    /**
     * Wstępnie inicjuje aktywność danymi.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.main_activity);
        forceFullScreen();

        propertyChangeSupport.firePropertyChange(
                new PropertyChangeEvent(this, this.getClass().getName(), null, "onCreate")
        );
    }

    /**
     * Obsługuje komunikację między aktywnościami i/lub systemem.
     * Propaguje intent do propertyChangeSupport mającego swoich obserwatorów.
     *
     * @param intent obiekt reprezentujacy akcję, do podjęcia przez aplikację
     */
    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, this.getClass().getName(), null, intent));
    }

    // -=-=-=-=- <<<AppCompatActivity -=-=-=-=-
    // -=-=-=-=- >>>PropertyChangeSupport -=-=-=-=-

    /**
     * Dodanie obserwatora.
     *
     * @param propertyChangeListener obserwujący do powiadomienia o zmianie wartości
     */
    public void addPropertyChangeListener(@NonNull final PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Usunięcie obserwatora.
     *
     * @param propertyChangeListener obserwujący do usunięcia
     */
    public void removePropertyChangeListener(@NonNull final PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    // -=-=-=-=- <<<PropertyChangeSupport -=-=-=-=-

    /**
     * Wymuś użycie trybu pełnego ekranu.
     * W zależności od wersji systemu używo starego lub nowego API.
     */
    private void forceFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Objects.requireNonNull(this.getWindow().getInsetsController()).hide(WindowInsets.Type.statusBars());
            Objects.requireNonNull(this.getWindow().getInsetsController()).hide(WindowInsets.Type.navigationBars());
        } else {
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }
}