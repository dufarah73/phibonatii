package br.com.phibonatii.phibonatii.model;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import br.com.phibonatii.phibonatii.LocalizationFragment;

public class Localizator implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final LocationRequest request;
    private final GoogleApiClient client;
    private final GoogleMap googleMap;
    private final Activity activity;
    private final LocalizationFragment.LocationCallback callback;

    public Localizator(GoogleMap googleMap, Activity activity, LocalizationFragment.LocationCallback callback) {
        request = LocationRequest.create();
        request.setSmallestDisplacement(50);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        client = new GoogleApiClient.Builder(activity.getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        client.connect();

        this.googleMap = googleMap;
        this.activity = activity;
        this.callback = callback;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
        }
    }

    @Override public void onConnectionSuspended(int i) {  }

    @Override
    public void onLocationChanged(Location location) {
        callback.onNewLocationAvailable(activity, location);

        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            googleMap.setMyLocationEnabled(true);
        }

        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenada, 18);
        googleMap.moveCamera(cameraUpdate);
    }
}
