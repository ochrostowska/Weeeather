package com.oldzi.weeeather.city_weather.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 04.08.2016.
 */
public class Day implements Serializable {

    private String day;
    private String month;
    private String year;
    private String daySuffix;
    private double maxTemp;
    private double minTemp;
    private String sunsetTime;
    private String sunriseTime;
    private List<Hour> hours = new ArrayList<>();


    public Day(List<Hour> hours, String day, String month, String year) {
        this.hours = hours;
        this.day = day;
        this.month = month;
        this.year = year;
        daySuffix = getDayOfMonthSuffix(Integer.valueOf(day));
        maxTemp = calculateMaxTemp();
        minTemp = calculateMinTemp();
    }

    public String getDaySuffix() {
        return daySuffix;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    private double calculateMaxTemp() {
        double max = hours.get(0).getTemperature();
        for (int i = 1; i < hours.size(); i++)
            if (hours.get(i).getTemperature() > max) max = hours.get(i).getTemperature();
        return max;
    }

    private double calculateMinTemp() {
        double min = hours.get(0).getTemperature();
        for (int i = 1; i < hours.size(); i++)
            if (hours.get(i).getTemperature() < min) min = hours.get(i).getTemperature();
        return min;
    }

    public String getDay() {
        return day;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public int getMaxTemp() {
        return (int)Math.round(maxTemp);
    }

    public int getMinTemp() {
        return (int)Math.round(minTemp);
    }

    String getDayOfMonthSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}
