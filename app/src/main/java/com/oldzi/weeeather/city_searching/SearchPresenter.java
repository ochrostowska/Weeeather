package com.oldzi.weeeather.city_searching;

import android.app.Activity;
import android.content.Intent;

import com.oldzi.weeeather.json.wunderAutocomplete.RESULT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 05.09.2016.
 */
public class SearchPresenter implements SearchCommunicator {

    private SearchActivity view;
    private SearchCityManager manager;
    private List<SearchItem> citiesFound = new ArrayList<>();

    public SearchPresenter(SearchActivity view) {
        this.view = view;
        manager = new SearchCityManager(this);
    }

    public void findCities(String query) {
        manager.findCities(query);
    }

    @Override
    public void onCityFound(List<RESULT> cities) {
        if (view != null) {
            if (cities != null) {
                citiesFound.clear();
                for (int i = 0; i < cities.size(); i++)
                    citiesFound.add(new SearchItem(
                            cities.get(i).getName(),
                            cities.get(i).getC(),
                            cities.get(i).getLat(),
                            cities.get(i).getLon()));
                view.onCityFound(citiesFound);
            }
        }
    }

    @Override
    public void onFailure() {

    }

    public void cityClicked(int position) {
        String name = citiesFound.get(position).getCityName();
        String latitude = citiesFound.get(position).getLatitude();
        String longitude = citiesFound.get(position).getLongitude();
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("lat", latitude);
        intent.putExtra("lon", longitude);
        view.setResult(Activity.RESULT_OK, intent);
        view.onActivityFinish();
    }

    public void cityLongClicked(int position) {
        //TODO:
        // display window with adding to city list
    }

    public void finishWithoutWeather() {
        view.setResult(Activity.RESULT_CANCELED);
        view.onActivityFinish();
    }
}
