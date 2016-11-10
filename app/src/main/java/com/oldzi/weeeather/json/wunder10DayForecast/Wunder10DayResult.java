
package com.oldzi.weeeather.json.wunder10DayForecast;

import java.util.ArrayList;
import java.util.List;

public class Wunder10DayResult {

    private List<HourlyForecast> hourly_forecast = new ArrayList<>();

    public List<HourlyForecast> getHourlyForecast() {
        return hourly_forecast;

    }
}