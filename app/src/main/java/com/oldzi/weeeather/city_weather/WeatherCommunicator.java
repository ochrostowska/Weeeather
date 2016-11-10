package com.oldzi.weeeather.city_weather;

import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;
import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;

public interface WeatherCommunicator {
    void onParseResult(Wunder10DayResult result);
    void onParseResult(WunderAstrologyResult result);
    void onParseResult(ForecastIOWeatherResult result);
    void onParseLocationResult(String latitude, String longitude);
}
