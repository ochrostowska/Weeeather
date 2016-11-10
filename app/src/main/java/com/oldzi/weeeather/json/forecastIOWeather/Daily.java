package com.oldzi.weeeather.json.forecastIOWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 05.09.2016.
 */
public class Daily {

    private String summary;
    private List<Datum_> data = new ArrayList<>();

    public String getSummary() {
        return summary;
    }

    public List<Datum_> getData() {
        return data;
    }
}
