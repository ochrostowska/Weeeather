
package com.oldzi.weeeather.json.wunderAstronomy;

import com.google.gson.annotations.SerializedName;

public class WunderAstrologyResult {

    @SerializedName("moon_phase")
    private MoonPhase moonPhase;
    private Sunrise sunrise;

    public MoonPhase getMoonPhase() {
        return moonPhase;
    }

    public Sunrise getSunrise() {
        return sunrise;
    }
}
