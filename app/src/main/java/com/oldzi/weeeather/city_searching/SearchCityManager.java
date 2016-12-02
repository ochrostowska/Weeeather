package com.oldzi.weeeather.city_searching;

import android.util.Log;

import com.oldzi.weeeather.json.wunderAutocomplete.WunderAutocompleteResult;
import com.oldzi.weeeather.network_calls.NetworkCallsManager;
import com.oldzi.weeeather.network_calls.WeatherAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oldzi on 25.07.2016.
 */
public class SearchCityManager extends NetworkCallsManager {

    SearchCommunicator communicator;
    String AUTOCOMPLETE_URL = "http://autocomplete.wunderground.com";

    public SearchCityManager(SearchCommunicator communicator) {
        this.communicator = communicator;
    }

    public void findCities(String query) {
        WeatherAPI service = initializeRetrofit(AUTOCOMPLETE_URL);
        Call<WunderAutocompleteResult> call = service.findCity(query);
        call.enqueue(new Callback<WunderAutocompleteResult>() {
            @Override
            public void onResponse(Call<WunderAutocompleteResult> call, Response<WunderAutocompleteResult> response) {
                if (response.body().RESULTS.size() != 0) {
                    communicator.onCityFound(response.body().RESULTS);
                } else communicator.onFailure();
            }
            @Override
            public void onFailure(Call<WunderAutocompleteResult> call, Throwable t) {
                Log.d("SEARCH", "No result - onFailure" + t.getLocalizedMessage());
                communicator.onFailure();
            }
        });
    }
}
