package com.oldzi.weeeather.city_weather.data;

import java.io.Serializable;

/**
 * Created by Oldzi on 04.08.2016.
 */
public class Hour implements Serializable {

    private int hour;
    private Double temperature;
    private String icon;
    private String condition;
    private String windspeed;
    private String winddir;
    private String humidity;
    private String snow;

    public Hour(int hour, Double temperature, String icon, String condition, String windspeed, String winddir, String humidity, String snow) {
        this.condition = condition;
        this.hour = hour;
        this.humidity = humidity;
        this.icon = icon;
        this.snow = snow;
        this.temperature = temperature;
        this.winddir = winddir;
        this.windspeed = windspeed;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String getCondition() {
        return condition;
    }

    public int getHour() {
        return hour;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getIcon() {
        return icon;
    }

    public String getSnow() {
        return snow;
    }

    public int getTemperature() {
        return (int)Math.round(temperature);
    }

    public String getWinddir() {
        return winddir;
    }
}
