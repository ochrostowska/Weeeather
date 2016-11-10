
package com.oldzi.weeeather.json.wunderAstronomy;

import com.google.gson.annotations.SerializedName;

public class MoonPhase {

    private String percentIlluminated;
    private String ageOfMoon;
    private String phaseofMoon;
    private String hemisphere;
    @SerializedName("current_time")
    private CurrentTime currentTime;
    private Sunrise sunrise;
    private Sunset sunset;

    public String getPercentIlluminated() {
        return percentIlluminated;
    }

    public String getAgeOfMoon() {
        return ageOfMoon;
    }

    public String getPhaseofMoon() {
        return phaseofMoon;
    }


    public CurrentTime getCurrentTime() {
        return currentTime;
    }


    public Sunrise getSunrise() {
        return sunrise;
    }

    public Sunset getSunset() {
        return sunset;
    }

}
