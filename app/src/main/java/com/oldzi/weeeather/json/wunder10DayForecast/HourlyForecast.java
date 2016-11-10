
package com.oldzi.weeeather.json.wunder10DayForecast;


public class HourlyForecast {

    private FCTTIME FCTTIME;
    private Temp temp;              // temperature
    private String condition;       // general weather conditions ex. clear
    private String icon;            // string icon name
    private Wspd wspd;              // windspeed
    private Wdir wdir;              // wind direction and degrees
    private String humidity;        // wilgotnosc
    private Feelslike feelslike;    // odczuwalna
    private Snow snow;              // snieg


    public FCTTIME getFCTTIME() {
        return FCTTIME;
    }

    public Temp getTemp() {
        return temp;
    }

    public String getCondition() {
        return condition;
    }

    public String getIcon() {
        return icon;
    }

    public Wspd getWspd() {
        return wspd;
    }

    public Wdir getWdir() {
        return wdir;
    }

    public String getHumidity() {
        return humidity;
    }

    public Feelslike getFeelslike() {
        return feelslike;
    }

    public Snow getSnow() {
        return snow;
    }
}
