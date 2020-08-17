package pl.lodz.p.embeddedsystems;

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
     * Przy instancjonowaniu klasy dodajemy obiekt jako bean.
     */
    public MainActivity() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    // -=-=-=-=- >>>AppCompatActivity -=-=-=-=-

    /**
     * Wykonywane przy ładowaniu aplikacji z xml.
     *
     * @param savedInstanceState referencja do obiektu tworzonego w onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.main_activity);
        forceFullScreen();

        propertyChangeSupport.firePropertyChange(
                new PropertyChangeEvent(this, this.getClass().getName(), null, "onCreate")
        );
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, this.getClass().getName(), null, intent));
    }

    // -=-=-=-=- <<<AppCompatActivity -=-=-=-=-
    // -=-=-=-=- >>>PropertyChangeSupport -=-=-=-=-

    /**
     * Dodanie obserwatora dla tej klasy.
     *
     * @param propertyChangeListener objekt implementujący PropertyChangeListener
     */
    public void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Usunięcie obserwatora dla tej klasy.
     *
     * @param propertyChangeListener objekt implementujący PropertyChangeListener
     */
    public void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
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