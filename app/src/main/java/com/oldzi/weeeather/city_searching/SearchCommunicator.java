package com.oldzi.weeeather.city_searching;

import com.oldzi.weeeather.json.wunderAutocomplete.RESULT;

import java.util.List;

/**
 * Created by Oldzi on 05.09.2016.
 */
public interface SearchCommunicator {
    void onCityFound(List<RESULT> cities);
    void onFailure();
}
