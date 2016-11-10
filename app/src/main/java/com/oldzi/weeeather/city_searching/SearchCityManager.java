package com.oldzi.weeeather.city_searching;

import android.util.Log;

import com.oldzi.weeeather.json.wunderAutocomplete.WunderAutocompleteResult;
import com.oldzi.weeeather.network_calls.WunderAutoComplete;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oldzi on 25.07.2016.
 */
public class SearchCityManager {

    SearchCommunicator communicator;

    public SearchCityManager(SearchCommunicator communicator) {
        this.communicator = communicator;
    }

    public void findCities(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WunderAutoComplete.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WunderAutoComplete service = retrofit.create(WunderAutoComplete.class);
        Call<WunderAutocompleteResult> call = service.findCity(query);
        call.enqueue(new Callback<WunderAutocompleteResult>() {
            @Override
            public void onResponse(Call<WunderAutocompleteResult> call, Response<WunderAutocompleteResult> response) {
                if (response.body().RESULTS.size() != 0) {
                    communicator.onCityFound(response.body().RESULTS);
                }
            }

            @Override
            public void onFailure(Call<WunderAutocompleteResult> call, Throwable t) {
                Log.d("SEARCH", "No result - onFailure" + t.getLocalizedMessage());
            }
        });
    }
}
