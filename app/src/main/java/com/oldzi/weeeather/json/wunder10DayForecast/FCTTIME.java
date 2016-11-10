
package com.oldzi.weeeather.json.wunder10DayForecast;

public class FCTTIME {

    private int hour;
    private String hour_padded;
    private String year;
    private String mday;
    private String mon;
    private String mon_padded;
    private String month_name;
    private String weekday_name;

    public int getHour() {
        return hour;
    }

    public String getMday() {
        return mday;
    }

    public String getHour_padded() {
        return hour_padded;
    }

    public String getYear() {
        return year;
    }

    public String getMon() {
        return mon;
    }

    public String getMon_padded() {
        return mon_padded;
    }
    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getWeekday_name() {
        return weekday_name;
    }
}
