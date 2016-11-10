package com.oldzi.weeeather.json.forecastIOWeather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Oldzi on 05.09.2016.
 */
public class Datum_ {

    // unix formatted
    private Integer sunriseTime;
    private Integer sunsetTime;

    public Integer getSunriseTime() {
        return sunriseTime;
    }

    public Integer getSunsetTime() {
        return sunsetTime;
    }

    public String getFormattedSunriseTime() {
        return getDate(sunriseTime);
    }

    public String getFormattedSunsetTime() {
        return getDate(sunsetTime);
    }

    private String getDate(int timestamp) {
        Date date = new Date(timestamp * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        sdf.format(date);
        return sdf.getCalendar().get(Calendar.HOUR_OF_DAY) + ":" + sdf.getCalendar().get(Calendar.MINUTE);
    }
}
