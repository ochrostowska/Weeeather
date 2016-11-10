package com.oldzi.weeeather.city_searching;

import java.util.Locale;

public class StringResponseModifications {

    /**
     *  RESULT from WunderAutocompleteResult contains var c, which is just a country code
     *  we need to get full name of the country
     */

    public static String getNamefromCode(String countryCode) {
        Locale l = new Locale("", countryCode);
        return l.getDisplayCountry();
    }

    /**
     *  RESULT from WunderAutocompleteResult contains var name, which is
     *  a name of the city with region after a comma
     *  we need to get only city name
     */

    public static String cutCityName(String response) {
        return response.split(",", 2)[0];
    }
}
