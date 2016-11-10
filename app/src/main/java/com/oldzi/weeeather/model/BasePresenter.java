package com.oldzi.weeeather.model;

import android.app.Activity;

/**
 * Created by Oldzi on 25.10.2016.
 */

public class BasePresenter {
    Activity view;

    public BasePresenter(Activity view) {
        this.view = view;
    }
}
