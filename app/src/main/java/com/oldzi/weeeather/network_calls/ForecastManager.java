package com.oldzi.weeeather.network_calls;

import android.util.Log;

import com.oldzi.weeeather.city_weather.WeatherCommunicator;
import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;
import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastManager extends NetworkCallsManager {

    private static final String TAG = "ForecastManager";

    private static final String FORECAST_IO_URL = "https://api.forecast.io";
    private static final String WUNDERGROUND_URL = "http://api.wunderground.com";

    private static final String FORECAST_IO_API_KEY = "386d816ec8ca8d5b498fdecec22f13e4";
    private static final String WUNDERGROUND_API_KEY = "37ada175c0113d52";

    private WeatherCommunicator communicator;

    public ForecastManager(WeatherCommunicator communicator) {
        this.communicator = communicator;
    }

    public void downloadForecastIOData(String latitude, String longitude) {
        Call<ForecastIOWeatherResult> call = initializeRetrofit(FORECAST_IO_URL)
                .hourlyForecast(FORECAST_IO_API_KEY, latitude, longitude);
        call.enqueue(new Callback<ForecastIOWeatherResult>() {
            @Override
            public void onResponse(Call<ForecastIOWeatherResult> call, Response<ForecastIOWeatherResult> response) {
                if (response.body().getHourly()!=null) {
                    communicator.onParseResult(response.body());
                } else {
                    Log.d(TAG, "ForecastIO no result");
                    communicator.onFailure();
                }
            }
            @Override
            public void onFailure(Call<ForecastIOWeatherResult> call, Throwable t) {
                Log.d(TAG, "ForecastIO No result - onFailure - "+t.getMessage());
                communicator.onFailure();
            }
        });
    }

    public void downloadAstroData(String latitude, String longitude) {

        Call<WunderAstrologyResult> call = initializeRetrofit(WUNDERGROUND_URL)
                .dailyAstrology(WUNDERGROUND_API_KEY, latitude, longitude);
        call.enqueue(new Callback<WunderAstrologyResult>() {
            @Override
            public void onResponse(Call<WunderAstrologyResult> call, Response<WunderAstrologyResult> response) {
                if (response.body().getMoonPhase() != null) {
                    communicator.onParseResult(response.body());
                } else {
                    Log.d(TAG, "ASTRO No result - onResult");
                    communicator.onFailure();
                }
            }
            @Override
            public void onFailure(Call<WunderAstrologyResult> call, Throwable t) {
                communicator.onFailure();
                Log.d(TAG, "ASTRO No result - onFailure");
            }
        });
    }

    public void downloadData(String latitude, String longitude) {

        Call<Wunder10DayResult> call = initializeRetrofit(WUNDERGROUND_URL)
                .wunderHourlyForecast(WUNDERGROUND_API_KEY, latitude, longitude);
        call.enqueue(new Callback<Wunder10DayResult>() {
            @Override
            public void onResponse(Call<Wunder10DayResult> call, Response<Wunder10DayResult> response) {
                if(response.body().getHourlyForecast()!=null) {
                    communicator.onParseResult(response.body());
                }
                else {
                    Log.d(TAG, "WUNDERWEATHER No result - onResult");
                    communicator.onFailure();
                }
            }
            @Override
            public void onFailure(Call<Wunder10DayResult> call, Throwable t) {
                Log.d(TAG, "WUNDERWEATHER No result - onFailure - "+t.getMessage());
                communicator.onFailure();
            }
        });
    }
}
