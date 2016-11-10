package com.oldzi.weeeather.city_saved;

import android.app.Activity;
import android.content.Intent;

import com.oldzi.weeeather.database.CitiesDatabaseHelper;
import com.oldzi.weeeather.model.BasePresenter;

import java.util.List;

/**
 * Created by Oldzi on 07.10.2016.
 */

public class UserCitiesPresenter extends BasePresenter {


    private UserCitiesActivity view;
    private CitiesDatabaseHelper helper;
    private List<UserCity> userCities;

    public UserCitiesPresenter(UserCitiesActivity view) {
        super(view);
        this.view = view;
        helper = new CitiesDatabaseHelper(view);
    }

    private void changeItemOrder() {
    }

    public void getUserCities() {
        userCities = helper.getUserCities();
        if(view!=null)
        view.onCitiesFound(userCities);
    }


    public void onItemMoved(int startingPosition, int endingPosition) {
        helper.changeOrder(startingPosition, endingPosition);
        helper.getAllCities();
    }

    public void onItemClicked(int position) {
        int id = userCities.get(position).getId();
        Intent intent = new Intent();
        intent.putExtra("id", id);
        view.setResult(Activity.RESULT_OK, intent);
        view.onActivityFinish();
    }

    public void finishWithoutWeather() {
        view.setResult(Activity.RESULT_CANCELED);
        view.onActivityFinish();
    }
}


