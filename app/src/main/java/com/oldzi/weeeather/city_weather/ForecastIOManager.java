package com.oldzi.weeeather.city_weather;

import android.util.Log;

import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;
import com.oldzi.weeeather.network_calls.ForecastIOWeatherAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oldzi on 14.08.2016.
 */
public class ForecastIOManager {

    WeatherCommunicator weatherCommunicator;
    public static final String TAG = "FORECASTIO";
    public boolean gotResult = false;

    public ForecastIOManager(WeatherCommunicator weatherCommunicator) {
        this.weatherCommunicator = weatherCommunicator;
    }

    public void downloadData() {
        ForecastIOWeatherAPI service = initializeRetrofit();
        Call<ForecastIOWeatherResult> call = service.hourlyForecast("53.450001", "14.516666");
        call.enqueue(new Callback<ForecastIOWeatherResult>() {
            @Override
            public void onResponse(Call<ForecastIOWeatherResult> call, Response<ForecastIOWeatherResult> response) {
                gotResult = response.body().getHourly() != null;
                if (gotResult) {
                    weatherCommunicator.onParseResult(response.body());
                } else Log.d(TAG, "No result");
            }
            @Override
            public void onFailure(Call<ForecastIOWeatherResult> call, Throwable t) {
                gotResult = false;
                Log.d(TAG, "No result - onFailure - " + t.getMessage());
            }
        });
    }

    public void downloadData(String latitude, String longitude) {
        ForecastIOWeatherAPI service = initializeRetrofit();
        Call<ForecastIOWeatherResult> call = service.hourlyForecast(latitude, longitude);
        call.enqueue(new Callback<ForecastIOWeatherResult>() {
            @Override
            public void onResponse(Call<ForecastIOWeatherResult> call, Response<ForecastIOWeatherResult> response) {
                gotResult = response.body().getHourly() != null;
                if (gotResult) {
                    weatherCommunicator.onParseResult(response.body());
                } else Log.d(TAG, "No result");
            }
            @Override
            public void onFailure(Call<ForecastIOWeatherResult> call, Throwable t) {
                gotResult = false;
                Log.d(TAG, "No result - onFailure - "+t.getMessage());
            }
        });
    }

    private ForecastIOWeatherAPI initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ForecastIOWeatherAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ForecastIOWeatherAPI.class);
    }
}
