package com.oldzi.weeeather.city_weather;

import android.util.Log;

import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.network_calls.WunderGroundAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oldzi on 22.07.2016.
 */
public class WunderWeatherManager {

    private static final String TAG = "WunderWeatherManager";
    private boolean gotResult = false;
    WeatherCommunicator weatherCommunicator;

    public WunderWeatherManager(WeatherCommunicator weatherCommunicator) {
        this.weatherCommunicator = weatherCommunicator;
    }

    public void downloadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WunderGroundAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WunderGroundAPI service = retrofit.create(WunderGroundAPI.class);
        Call<Wunder10DayResult> call = service.hourlyForecast("53.450001", "14.516666");
        call.enqueue(new Callback<Wunder10DayResult>() {
            @Override
            public void onResponse(Call<Wunder10DayResult> call, Response<Wunder10DayResult> response) {
                gotResult = response.body().getHourlyForecast().size() != 0;
                if(gotResult) {
                    weatherCommunicator.onParseResult(response.body());
                }
                else Log.d(TAG, "No result onResponse");
            }

            @Override
            public void onFailure(Call<Wunder10DayResult> call, Throwable t) {
                gotResult = false;
                Log.d("KAKA", "No result - onFailure - "+t.getMessage());
            }
        });
    }

    public void downloadData(String latitude, String longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WunderGroundAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WunderGroundAPI service = retrofit.create(WunderGroundAPI.class);
        Call<Wunder10DayResult> call = service.hourlyForecast(latitude, longitude);
        call.enqueue(new Callback<Wunder10DayResult>() {
            @Override
            public void onResponse(Call<Wunder10DayResult> call, Response<Wunder10DayResult> response) {
                gotResult = response.body().getHourlyForecast().size() != 0;
                if(gotResult) {
                    weatherCommunicator.onParseResult(response.body());
                }
                else Log.d(TAG, "No result - onResult");
            }

            @Override
            public void onFailure(Call<Wunder10DayResult> call, Throwable t) {
                gotResult = false;
                Log.d(TAG, "No result - onFailure - "+t.getMessage());
            }
        });
    }
}


