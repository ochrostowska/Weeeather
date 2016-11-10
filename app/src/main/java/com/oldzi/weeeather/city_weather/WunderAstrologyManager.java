package com.oldzi.weeeather.city_weather;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;
import com.oldzi.weeeather.network_calls.WunderGroundAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oldzi on 05.08.2016.
 */
public class WunderAstrologyManager {

    WeatherCommunicator weatherCommunicator;
    public static final String TAG = "astrologyManager";
    public boolean gotResult = false;

    public WunderAstrologyManager(WeatherCommunicator weatherCommunicator) {
        this.weatherCommunicator = weatherCommunicator;
    }

    public void downloadData() {
        WunderGroundAPI service = initializeRetrofit();
        Call<WunderAstrologyResult> call = service.dailyAstrology("53.450001", "14.516666");
        call.enqueue(new Callback<WunderAstrologyResult>() {
            @Override
            public void onResponse(Call<WunderAstrologyResult> call, Response<WunderAstrologyResult> response) {
                gotResult = response.body().getMoonPhase() != null;
                if (gotResult) {
                    weatherCommunicator.onParseResult(response.body());
                } else Log.d(TAG, "No result - onResponse");
            }

            @Override
            public void onFailure(Call<WunderAstrologyResult> call, Throwable t) {
                gotResult = false;
                Log.d(TAG, "No result - onFailure");
            }
        });
    }

    public void downloadData(String latitude, String longitude) {
        WunderGroundAPI service = initializeRetrofit();
        Call<WunderAstrologyResult> call = service.dailyAstrology(latitude, longitude);
        call.enqueue(new Callback<WunderAstrologyResult>() {
            @Override
            public void onResponse(Call<WunderAstrologyResult> call, Response<WunderAstrologyResult> response) {
                gotResult = response.body().getMoonPhase() != null;
                 if (gotResult) {
                    weatherCommunicator.onParseResult(response.body());
                } else Log.d(TAG, "No result - onResult");
            }

            @Override
            public void onFailure(Call<WunderAstrologyResult> call, Throwable t) {
                gotResult = false;
                Log.d(TAG, "No result - onFailure");
            }
        });
    }

    private WunderGroundAPI initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WunderGroundAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WunderGroundAPI.class);
    }


    public void downloadDataasJson() {
        WunderGroundAPI service = initializeRetrofit();
        Call<JsonObject> call = service.dailyAstrologyinJson("53.450001", "14.552812");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                gotResult = response.body().size() != 0;
                if (gotResult) {
                    Gson gson = new Gson();
                    WunderAstrologyResult result = gson.fromJson(response.body().toString(), WunderAstrologyResult.class);
                } else Log.d(TAG, "No result JSON");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                gotResult = false;
                Log.d(TAG, "No result - onFailure JSON");
            }
        });
    }

}
