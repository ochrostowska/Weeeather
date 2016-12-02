package com.oldzi.weeeather.network_calls;

import com.google.gson.JsonObject;
import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;
import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;
import com.oldzi.weeeather.json.wunderAutocomplete.WunderAutocompleteResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPI {

    // FORECASTIO
    // http://api.wunderground.com/api/37ada175c0113d52/hourly10day/q/37.776289,-122.395234.json
    @GET("/forecast/{key}/{latitude},{longitude}")
    Call<ForecastIOWeatherResult> hourlyForecast(@Path("key") String apiKey,
                                                 @Path("latitude") String latitude,
                                                 @Path("longitude") String longitude);

    // WUNDERGROUND
    // http://api.wunderground.com/api/37ada175c0113d52/hourly10day/q/37.776289,-122.395234.json
    @GET("/api/{key}//hourly10day/q/{latitude},{longitude}.json")
    Call<Wunder10DayResult> wunderHourlyForecast(@Path("key") String apiKey,
                                                 @Path("latitude") String latitude,
                                                 @Path("longitude") String longitude);

    @GET("/api/{key}/hourly10day/q/{latitude},{longitude}.json")
    Call<JsonObject> wunderHourlyForecastinJson(@Path("key") String apiKey,
                                                @Path("latitude") String latitude,
                                                @Path("longitude") String longitude);

    @GET("/api/{key}/astronomy/q/{latitude},{longitude}.json")
    Call<WunderAstrologyResult> dailyAstrology(@Path("key") String apiKey,
                                               @Path("latitude") String latitude,
                                               @Path("longitude") String longitude);

    @GET("/api/{key}/astronomy/q/{latitude},{longitude}.json")
    Call<JsonObject> dailyAstrologyinJson(@Path("key") String apiKey,
                                          @Path("latitude") String latitude,
                                          @Path("longitude") String longitude);

    //WUNDERGROUNDAUTOCOMPLETE
    @GET("aq")
    Call<WunderAutocompleteResult> findCity (@Query("query") String cityName);
}






