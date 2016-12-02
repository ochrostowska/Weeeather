package com.oldzi.weeeather.city_weather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.google.android.gms.location.LocationServices.API;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

/**
 * Created by Oldzi on 08.09.2016.
 */
public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "LocationManager";
    public final static int ACCESS_LOCATION_PERMISSION = 22;
    private GoogleApiClient googleApiClient;
    private WeatherCommunicator weatherCommunicator;
    private Context context;

    private static LocationManager ourInstance;

    public LocationManager(WeatherCommunicator weatherCommunicator, Context context) {
        this.weatherCommunicator = weatherCommunicator;
        this.context = context;
        initialize();
    }

    public static LocationManager getInstance(WeatherCommunicator weatherCommunicator, Context context) {
        if(ourInstance==null) ourInstance = new LocationManager(weatherCommunicator, context);
        return ourInstance;
    }

    public LocationManager() {}

    private void initialize() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(API)
                .build();
    }

    public void getCoordinates() {
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION_PERMISSION);
            return;
        }
        Location location = FusedLocationApi.getLastLocation(googleApiClient);

        if (location == null) {
            Log.d(TAG, "Location is null");

        } else {
            // TODO:
            // Find a way to retrieve cityName from lat lon
            // extend onParseLocationResult to include name
            // String cityName = location.get ??
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            weatherCommunicator.onParseLocationResult(latitude.toString(), longitude.toString());
            googleApiClient.disconnect();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.e(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
    }
}
