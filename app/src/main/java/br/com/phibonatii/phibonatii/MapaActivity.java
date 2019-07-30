package br.com.phibonatii.phibonatii;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MapaActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_CODE = 101;

    private String lat, lng;

    private LocalizationFragment.LocationCallback callbackLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        callbackLocal = new LocalizationFragment.LocationCallback() {
            @Override public void onNewLocationAvailable(Activity activity, Location location) {
                lat = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
//            Toast.makeText(this, "LOCATION - permissão já concedida", Toast.LENGTH_LONG).show();
            FragmentManager fragMan = getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMan.beginTransaction();
            LocalizationFragment fragLocal = new LocalizationFragment();
            fragLocal.SetLocalizationFragment(this, callbackLocal);
            fragTrans.replace(R.id.frame_localization, fragLocal);
            fragTrans.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "LOCATION - permissão concedida agora", Toast.LENGTH_LONG).show();
                FragmentManager fragMan = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragMan.beginTransaction();
                LocalizationFragment fragLocal = new LocalizationFragment();
                fragLocal.SetLocalizationFragment(this, callbackLocal);
                fragTrans.replace(R.id.frame_localization, fragLocal);
                fragTrans.commit();
            } else {
                Toast.makeText(this, "LOCATION - permissão negada", Toast.LENGTH_LONG).show();
            }
        }
    }
}
