
package com.oldzi.weeeather.json.forecastIOWeather;

public class ForecastIOWeatherResult {

    private Double latitude;
    private Double longitude;
    private String timezone;
    private Integer offset;
    private Hourly hourly;
    private Daily daily;

    public Daily getDaily() {
        return daily;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public Integer getOffset() {
        return offset;
    }

    public Hourly getHourly() {
        return hourly;
    }

}
