package br.com.phibonatii.phibonatii;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import br.com.phibonatii.phibonatii.model.Localizator;

public class LocalizationFragment extends SupportMapFragment implements OnMapReadyCallback {

    public static interface LocationCallback {
        public void onNewLocationAvailable(Activity activity, Location location);
    }

    private Activity activity;
    private LocationCallback callback;

    public void SetLocalizationFragment(final Activity activity, final LocationCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        new Localizator(googleMap, activity, callback);
    }
}
