package com.oldzi.weeeather.city_weather.data;

import com.oldzi.weeeather.json.forecastIOWeather.ForecastIOWeatherResult;
import com.oldzi.weeeather.json.wunder10DayForecast.HourlyForecast;
import com.oldzi.weeeather.json.wunder10DayForecast.Wunder10DayResult;
import com.oldzi.weeeather.json.wunderAstronomy.WunderAstrologyResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City implements Serializable {

    private String name;
    private String latitude;
    private String longitude;

    private List<Day> daysList = new ArrayList<>();

    public City(String name, String latitude, String longitude, Wunder10DayResult wunder10DayResult, WunderAstrologyResult wunderAstrologyResult, ForecastIOWeatherResult forecastIOResult) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;

        double[] forecastTemps = new double[48];
        for (int i = 0; i < 48; i++)
            forecastTemps[i] = forecastIOResult.getHourly().getData().get(i).getTemperature();
        daysList = createWeatherData(wunder10DayResult, forecastIOResult);
    }

    public void updateData() {}

    private List<Day> createWeatherData(Wunder10DayResult wunderResult, ForecastIOWeatherResult forecastResult) {
        List<HourlyForecast> hf = wunderResult.getHourlyForecast();
        List<Day> days = new ArrayList<>();
        List<Hour> hours;

        int currentHour = hf.get(0).getFCTTIME().getHour();
        double[] aveTemps = calcAveTemps(wunderResult, forecastResult);

        int i = 0;
        int m = 0;

        while (i < hf.size()) {
            hours = new ArrayList<>();

            for (int j = currentHour; j <= 24; j++) {
                if (i == hf.size()) break;

                hours.add(new Hour(hf.get(i).getFCTTIME().getHour(),
                        aveTemps[i],
                        hf.get(i).getIcon(),
                        hf.get(i).getCondition(),
                        hf.get(i).getWspd().getMetric(),
                        hf.get(i).getWdir().getDir(),
                        hf.get(i).getHumidity(),
                        hf.get(i).getSnow().getMetric()));
                i++;
            }
            // we gotta get(i-2), cause for example for hour 23:15 of Friday 6th, the weather is taken as hour 0 of Saturday 7th, so thats -1,
            // and another -1, cause we're incrementing var i inside for loop, so we'll have index out of bounds exception if we take only i-1
            Day day = new Day(hours,
                    hf.get(i - 2).getFCTTIME().getMday(),
                    hf.get(i - 2).getFCTTIME().getMonth_name(),
                    hf.get(i - 2).getFCTTIME().getYear());
            if (m < 8) {
                day.setSunriseTime(forecastResult.getDaily().getData().get(m).getFormattedSunriseTime());
                day.setSunsetTime(forecastResult.getDaily().getData().get(m).getFormattedSunsetTime());
                m++;
            }
            days.add(day);
            currentHour = 1;
        }
        return days;
    }

    private double[] calcAveTemps(Wunder10DayResult wunderResult, ForecastIOWeatherResult forecastResult) {
        double[] aveTemps = new double[240];
        for (int i = 0; i < wunderResult.getHourlyForecast().size(); i++) {
            if (i < forecastResult.getHourly().getData().size()) {
                aveTemps[i] = Math.round(((wunderResult.getHourlyForecast().get(i).getTemp().getMetric() + forecastResult.getHourly().getData().get(i).getTemperature()) / 2) - 0.5) + 0.5;
            } else {
                aveTemps[i] = wunderResult.getHourlyForecast().get(i).getTemp().getMetric();
            }
        }
        return aveTemps;
    }

    public List<Day> getDays() {
        return daysList;
    }

    public String getName() {
        return name;
    }

    public void setDays(List<Day> days) {
        this.daysList = days;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
}



