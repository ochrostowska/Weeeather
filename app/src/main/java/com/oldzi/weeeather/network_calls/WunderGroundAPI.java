package com.oldzi.weeeather.network_calls;

import com.google.gson.JsonObject;
import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Oldzi on 22.07.2016.
 */
public interface WunderGroundAPI {

    String URL = "http://api.wunderground.com";
    String API_KEY = "37ada175c0113d52";

    // http://api.wunderground.com/api/37ada175c0113d52/hourly10day/q/37.776289,-122.395234.json
    @GET("/api/" + API_KEY + "/hourly10day/q/{latitude},{longitude}.json")
    Call<Wunder10DayResult> hourlyForecast(@Path("latitude") String latitude, @Path("longitude") String longitude);

    @GET("/api/" + API_KEY + "/hourly10day/q/{latitude},{longitude}.json")
    Call<JsonObject> hourlyForecastinJson(@Path("latitude") String latitude, @Path("longitude") String longitude);

    @GET("/api/" + API_KEY + "/astronomy/q/{latitude},{longitude}.json")
    Call<WunderAstrologyResult> dailyAstrology(@Path("latitude") String latitude, @Path("longitude") String longitude);

    @GET("/api/" + API_KEY + "/astronomy/q/{latitude},{longitude}.json")
    Call<JsonObject> dailyAstrologyinJson(@Path("latitude") String latitude, @Path("longitude") String longitude);
}
