package com.oldzi.weeeather.network_calls;

import com.oldzi.weeeather.json.wunderAutocomplete.WunderAutocompleteResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Oldzi on 25.07.2016.
 */
public interface WunderAutoComplete {

    String URL = "http://autocomplete.wunderground.com";

    @GET("aq")
    Call<WunderAutocompleteResult> findCity (@Query("query") String cityName);

}
