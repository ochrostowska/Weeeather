
package com.oldzi.weeeather.json.forecastIOWeather;

public class Datum {

    private Integer time;
    private Double temperature;

    public Integer getTime() {
        return time;
    }

    public Double getTemperature() {
        return 5*(temperature-32)/9;
    }

}
