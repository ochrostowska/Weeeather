package com.oldzi.weeeather.json.forecastIOWeather;

import android.util.Log;

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
    private int[] sunrise;
    private int[] sunset;

    public Integer getSunriseTime() {
        return sunriseTime;
    }

    public Integer getSunsetTime() {
        return sunsetTime;
    }

    public String getFormattedSunriseTime(int offset) {
        return getDate(sunriseTime, offset);
    }

    public String getFormattedSunsetTime(int offset) {
        return getDate(sunsetTime, offset);
    }

    private String getDate(int timestamp, int offset) {
        Date date = new Date(timestamp * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat();
        Log.d("TIMEZONE", ""+TimeZone.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        sdf.format(date);
        return String.format("%02d:%02d", (sdf.getCalendar().get(Calendar.HOUR_OF_DAY)+offset), sdf.getCalendar().get(Calendar.MINUTE));
    }

    public int[] getSunrise() {
        int[] sunrise = new int[2];
        Date date = new Date(sunriseTime * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        sdf.format(date);
        sunrise[0] = sdf.getCalendar().get(Calendar.HOUR_OF_DAY);
        sunrise[1] = sdf.getCalendar().get(Calendar.MINUTE);
        return sunrise;
    }

    public int[] getSunset() {
        int[] sunrise = new int[2];
        Date date = new Date(sunsetTime * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        sdf.format(date);
        sunrise[0] = sdf.getCalendar().get(Calendar.HOUR_OF_DAY);
        sunrise[1] = sdf.getCalendar().get(Calendar.MINUTE);
        return sunrise;
    }
}
