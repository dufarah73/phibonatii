package br.com.phibonatii.phibonatii.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class SingleLocalizator {

    public static interface LocationCallback {
        public void onNewLocationAvailable(Context context, Location location);
    }

    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        boolean isNetworkEnabled = false;
        boolean isGPSEnabled = false;
        Criteria criteria = new Criteria();
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        } else {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
            }
        }
        if (isNetworkEnabled || isGPSEnabled) {
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    callback.onNewLocationAvailable(context, location);
                }
                @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
                @Override public void onProviderEnabled(String provider) { }
                @Override public void onProviderDisabled(String provider) { }
            }, null);
        }
    }

}
