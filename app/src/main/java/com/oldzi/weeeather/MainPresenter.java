package com.oldzi.weeeather;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.oldzi.weeeather.city_weather.LocationManager;
import com.oldzi.weeeather.city_weather.WeatherCommunicator;
import com.oldzi.weeeather.city_weather.data.City;
import com.oldzi.weeeather.database.CitiesDatabaseHelper;
import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;
import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;
import com.oldzi.weeeather.network_calls.ForecastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainPresenter implements WeatherCommunicator {

    private ForecastManager manager;
    private Wunder10DayResult wunder10DayResult;
    private WunderAstrologyResult wunderAstrologyResult;
    private ForecastIOWeatherResult forecastIOWeatherResult;
    private boolean gotWeatherData, gotAstroData, gotForecastIOData;
    private City city;
    private String cityName, cityLatitude, cityLongitude;
    private CitiesDatabaseHelper dbHelper;
    Handler handler;

    private MainActivity view;

    public MainPresenter(MainActivity view) {
        this.view = view;
        manager = new ForecastManager(this);
        dbHelper = new CitiesDatabaseHelper(view);
        handler = new Handler();
    }

    private void showNoConnectionDialog() {

        final Dialog dialog = new Dialog(view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_no_connection);
        TextView info = (TextView) dialog.findViewById(R.id.dialog_text);
        Button settings = (Button) dialog.findViewById(R.id.button_settings);
        Button exit = (Button) dialog.findViewById(R.id.button_exit);
        info.setTypeface(Typeface.createFromAsset(view.getAssets(), "fonts/inconsolata.ttf"));
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                dialog.dismiss();
                view.showDialog(false);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                view.showDialog(false);
            }
        });
        dialog.show();
    }


    public void downloadAllData(String name, String latitude, String longitude) {
        cityName = name;
        cityLatitude = latitude;
        cityLongitude = longitude;

        gotAstroData = false;
        gotWeatherData = false;
        gotForecastIOData = false;

        if (checkConnection()) {
            Geocoder gcd = new Geocoder(view, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses!=null)
                Log.d("MAMA", addresses.get(0).getLocality());
            manager.downloadData(latitude, longitude);
            manager.downloadForecastIOData(latitude, longitude);
            manager.downloadAstroData(latitude, longitude);
        } else {
            showNoConnectionDialog();
        }
    }

    private boolean checkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) view.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    // TODO:
    // I'm working here!!
    public void downloadWeatherForCurrentLocation() {
        if(checkConnection()) {
            LocationManager locationManager = LocationManager.getInstance(this, view);
            locationManager.getCoordinates();
        } else {
            showNoConnectionDialog();
        }
    }

    public void refreshCityData() {
        downloadAllData(cityName, cityLatitude, cityLongitude);
    }

    public void addToUserCities() {
        dbHelper.addCity(city);
        view.onCityAdded(city.getName());
    }

    public void readDataFromDb(int id) {

        City city = dbHelper.getCityById(id);
        dbHelper.updateTimesClicked(id);
        publish(city);
    }

    private void checkIfReady() {

        if (gotWeatherData && gotAstroData && gotForecastIOData) {
            city = new City(cityName, cityLatitude, cityLongitude,
                    wunder10DayResult, wunderAstrologyResult, forecastIOWeatherResult);
            handler.removeCallbacksAndMessages(null);
            publish(city);
        }
    }

    public void stopUpgradeTimer() {
        handler.removeCallbacksAndMessages(this);
    }

    @Override
    public void onParseResult(Wunder10DayResult result) {
        wunder10DayResult = result;
        gotWeatherData = true;
        checkIfReady();
    }

    @Override
    public void onParseResult(WunderAstrologyResult result) {
        wunderAstrologyResult = result;
        gotAstroData = true;
        checkIfReady();
    }

    @Override
    public void onParseResult(ForecastIOWeatherResult result) {
        forecastIOWeatherResult = result;
        gotForecastIOData = true;
        checkIfReady();
    }

    @Override
    public void onParseLocationResult(String latitude, String longitude) {
        Log.d("LOCATIONRES", "In presenter got coords! " + latitude + " " + longitude);
        Geocoder gcd = new Geocoder(view, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1);
        } catch (IOException e) {
            view.showDialog(false);
            Log.d("LOCATIONRES", "No adresses found");
        }
        if (addresses.size() > 0) {
            String cityName = addresses.get(0).getLocality();
            Log.d("LOCATIONRES", "Address is " + cityName);
            downloadAllData(cityName, latitude, longitude);
        }
    }

    @Override
    public void onFailure() {
        if(!checkConnection()) showNoConnectionDialog();
        else view.showDialog(false);
    }

    private void publish(City city) {
        Log.d("LOL", "in publish");
            if(view!=null) {
                view.onReceivedData(city);
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        view.onUpdateTimer();
                        handler.postDelayed( this, 6 * 10000 );
                    }
                }, 6 * 10000 );
            }
        }
    }

