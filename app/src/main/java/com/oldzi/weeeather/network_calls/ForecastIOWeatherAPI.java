package com.oldzi.weeeather.network_calls;

import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Oldzi on 14.08.2016.
 */
public interface ForecastIOWeatherAPI {

    String URL = "https://api.forecast.io";
    String API_KEY = "386d816ec8ca8d5b498fdecec22f13e4";

    // http://api.wunderground.com/api/37ada175c0113d52/hourly10day/q/37.776289,-122.395234.json
    @GET("/forecast/" + API_KEY + "/{latitude},{longitude}")
    Call<ForecastIOWeatherResult> hourlyForecast(@Path("latitude") String latitude, @Path("longitude") String longitude);
}
