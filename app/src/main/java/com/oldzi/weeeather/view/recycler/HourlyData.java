package com.oldzi.weeeather.view.recycler;

public class HourlyData {
    private String hour;
    private String icon;
    private String temperature;

    public HourlyData(String hour, String icon, String temperature) {
        this.hour = hour;
        this.icon = icon;
        this.temperature = temperature;
    }

    public String getHour() {
        return hour;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemperature() {
        return temperature;
    }
}