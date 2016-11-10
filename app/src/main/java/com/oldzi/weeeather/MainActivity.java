package com.oldzi.weeeather;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.oldzi.weeeather.city_saved.UserCitiesActivity;
import com.oldzi.weeeather.city_searching.SearchActivity;
import com.oldzi.weeeather.city_weather.LocationManager;
import com.oldzi.weeeather.city_weather.data.City;
import com.oldzi.weeeather.view.MyProgressDialog;
import com.oldzi.weeeather.view.recycler.MyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainPresenter mainPresenter;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    FloatingActionsMenu menuButton;
    TextView cityName, updatedTV;
    View header;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_test_activity);
        header = findViewById(R.id.main_header);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        menuButton = (FloatingActionsMenu) findViewById(R.id.multiple_actions_down);
        updatedTV = (TextView) findViewById(R.id.updated_field);
        cityName = (TextView) findViewById(R.id.cityNameToolbar);
        cityName.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/inconsolata.ttf"));
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new MyProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        showDialog(true);
        mainPresenter = new MainPresenter(this);
        mainPresenter.downloadWeatherForCurrentLocation();
    }

    public void onReceivedData(City city) {
        Log.d("LOL", "Data received");
        minute = 0;
        cityName.setText(city.getName());
        updatedTV.setText(getString(R.string.updated_just_now));
        header.setVisibility(View.VISIBLE);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, city);
        recyclerView.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showDialog(false);
            }
        }, 2000);


    }

    public void onUpdateTimer() {
        if (minute == 5) {
            showDialog(true);
            mainPresenter.refreshCityData();
        }
        if (minute >= 2) updatedTV.setText("Updated " + minute + " minutes ago");
        minute++;
    }

    public void onAddCity(View view) {
        menuButton.collapse();
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onRefresh(View view) {
        menuButton.collapse();
        showDialog(true);
        mainPresenter.refreshCityData();
    }

    public void onMyCities(View view) {
        menuButton.collapse();
        Intent intent = new Intent(MainActivity.this, UserCitiesActivity.class);
        startActivityForResult(intent, 2);
    }

    public void onAddToMyCities(View view) {
        menuButton.collapse();
        mainPresenter.addToUserCities();
    }

    public void onCityAdded(String cityName) {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(this, R.style.CustomDialog));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_city_added);
        TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
        text.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/inconsolata.ttf"));
        text.setText("" + cityName);
        dialog.show();
    }

    public void showDialog(boolean show) {
        if (show) progressDialog.show();
        else progressDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LocationManager.ACCESS_LOCATION_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainPresenter.downloadWeatherForCurrentLocation();
                } else {
                    Toast.makeText(this, "App doesn't have permission to access current location :(", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String cityName = data.getStringExtra("name");
                String latitude = data.getStringExtra("lat");
                String longitude = data.getStringExtra("lon");
                progressDialog.show();
                mainPresenter.downloadAllData(cityName, latitude, longitude);
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                int id = data.getIntExtra("id", 0);
                progressDialog.show();
                mainPresenter.readDataFromDb(id);
            }
        }
    }
}
