package pl.lodz.p.embeddedsystems.game.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Objects;

import pl.lodz.p.embeddedsystems.MainActivity;
import pl.lodz.p.embeddedsystems.R;
import pl.lodz.p.embeddedsystems.game.viewmodel.GameSurfaceViewModel;

/**
 * Fragment odpowiadający za działanie NFC
 */
public class NFCFragment extends Fragment implements PropertyChangeListener {

    /**
     * Referencja do planszy gry.
     */
    GameSurfaceViewModel gameSurfaceViewModel = null;

    /**
     * Adapter NFC.
     */
    NfcAdapter nfcAdapter = null;

    /**
     * Referencja do tokenu utrzymywanego przez system.
     */
    PendingIntent pendingIntent = null;

    // -=-=-=-=- >>>Fragment -=-=-=-=-

    /**
     * Wykonywane przy tworzeniu fragmentu.
     *
     * @param savedInstanceState referencja do obiektu podawanego przez system w onCreate
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this.getContext());

        if (null == this.nfcAdapter) {
            Toast.makeText(this.getContext(), "To urządzenie nie wspiera NFC.", Toast.LENGTH_LONG).show();
        } else {
            ((MainActivity) Objects.requireNonNull(this.getContext())).addPropertyChangeListener(this);

            Toast.makeText(this.getContext(), "To urządzenie wspiera NFC.", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Podczepienie layoutu pod widok.
     *
     * @param inflater           pozwala załadować xml aby otrzymać widok
     * @param container          grupa widoków
     * @param savedInstanceState referencja do obiektu podawanego przez system w onCreate
     * @return widok nfc
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nfc_fragment, container, false);
    }

    /**
     * W momencie zatrzymania działania aplikacji zatrzymujemy szukanie tagów NFC.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (null != nfcAdapter) {
            nfcAdapter.disableForegroundDispatch(this.getActivity());
        }
    }

    /**
     * W momencie wznowienia działania aplikacji przywracamy szukanie tagów NFC.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (null != nfcAdapter) {
            nfcAdapter.enableForegroundDispatch(this.getActivity(), pendingIntent, null, null);
        }
    }

    // -=-=-=-=- <<<Fragment -=-=-=-=-
    // -=-=-=-=- >>>PropertyChangeListener -=-=-=-=-

    /**
     * Obserwator dla zdarzeń.
     *
     * @param propertyChangeEvent zdarzenie w aplikacji.
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getSource() == this.getContext()
                && propertyChangeEvent.getPropertyName().equals(Objects.requireNonNull(this.getContext()).getClass().getName())) {

            if (propertyChangeEvent.getNewValue().equals("onCreate")) {
                this.gameSurfaceViewModel = new ViewModelProvider((ViewModelStoreOwner) this.getContext()).get(GameSurfaceViewModel.class);

                pendingIntent = PendingIntent.getActivity(this.getContext(), 0,
                        new Intent(this.getContext(), this.getContext().getClass())
                                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            }

            if (propertyChangeEvent.getNewValue() instanceof Intent) {
                Intent intent = ((Intent) propertyChangeEvent.getNewValue());

                if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())
                        || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
                        || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
                    handleTag(Objects.requireNonNull(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)));
                }
            }
        }
    }
    // -=-=-=-=- <<<PropertyChangeListener -=-=-=-=-

    /**
     * Obsługa zdarzeń NFC
     *
     * @param tag Tag nfc
     */
    private void handleTag(Tag tag) {
        byte[] cheatTag = new byte[]{-87, -46, 22, -76};

        if (Arrays.equals(tag.getId(), cheatTag)) {
            boolean isCheatMode = Objects.equals(this.gameSurfaceViewModel.getCheatModeEnabled().getValue(), false);
            this.gameSurfaceViewModel.getCheatModeEnabled().setValue(isCheatMode);

            Toast.makeText(this.getContext(), (isCheatMode ? "CheatMode został włączony" : "CheatMode został wyłączony"), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getContext(), "Wykryto zły CHEAT_TAG", Toast.LENGTH_LONG).show();
        }
    }
}
