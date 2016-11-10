
package com.oldzi.weeeather.json.wunderAstronomy;

import com.google.gson.annotations.SerializedName;

public class WunderAstrologyResult {

    @SerializedName("moon_phase")
    private MoonPhase moonPhase;

    public MoonPhase getMoonPhase() {
        return moonPhase;
    }
}
