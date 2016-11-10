package com.oldzi.weeeather.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oldzi.weeeather.R;
import com.oldzi.weeeather.city_weather.data.City;

import java.util.ArrayList;
import java.util.List;

class VHHeader extends VHParent {
    TextView  dateTV, temperatureTV, iconTV, conditionTV, maximTV, sunriseTV, sunsetTV, windspeedTV, windDirTV, humidityTV, snowTV, minTempTV, maxTempTV;
    ImageButton searchButton;
    RecyclerView hourlyRecycler;

    public VHHeader(View itemView, Context context) {
        super(itemView, context);
        setupViews();
    }

    private void setupViews() {
        /*hourlyRecycler = (RecyclerView) itemView.findViewById(R.id.hourly_recycler);
        hourlyRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        HourlyRecyclerAdapter adapter = new HourlyRecyclerAdapter(createListForTest());
        hourlyRecycler.setLayoutManager(llm);
        hourlyRecycler.setAdapter(adapter);*/
        dateTV = (TextView) itemView.findViewById(R.id.date_tv);
        iconTV = (TextView) itemView.findViewById(R.id.icon_tv);
        searchButton = (ImageButton) itemView.findViewById(R.id.addCity);
        temperatureTV = (TextView) itemView.findViewById(R.id.current_temperature_tv);
        conditionTV = (TextView) itemView.findViewById(R.id.condition);
        maximTV = (TextView) itemView.findViewById(R.id.maxim);
        sunriseTV = (TextView) itemView.findViewById(R.id.sunrise_time);
        sunsetTV = (TextView) itemView.findViewById(R.id.sunset_time);
        windspeedTV = (TextView) itemView.findViewById(R.id.windspeed_value);
        windDirTV = (TextView) itemView.findViewById(R.id.winddir_value);
        humidityTV = (TextView) itemView.findViewById(R.id.humidity_value);
        snowTV = (TextView) itemView.findViewById(R.id.snow_value);
        setupIconTypeface();
    }

    public void updateValues(City city) {
        dateTV.setText("Today, " + city.getDays().get(0).getMonth() + " " + city.getDays().get(0).getDay() + city.getDays().get(0).getDaySuffix() + ", " + city.getDays().get(0).getYear());
        temperatureTV.setText(String.valueOf(city.getDays().get(0).getHours().get(0).getTemperature()));
        conditionTV.setText(city.getDays().get(0).getHours().get(0).getCondition());
        String icon =  city.getDays().get(0).getHours().get(0).getIcon();
        iconTV.setText(defineIcon(icon));
        maximTV.setText(defineMaxim(icon));
        sunriseTV.setText(city.getDays().get(0).getSunriseTime());
        sunsetTV.setText(city.getDays().get(0).getSunsetTime());
        windspeedTV.setText(city.getDays().get(0).getHours().get(0).getWindspeed());
        windDirTV.setText(city.getDays().get(0).getHours().get(0).getWinddir());
        humidityTV.setText(city.getDays().get(0).getHours().get(0).getHumidity());
        snowTV.setText(city.getDays().get(0).getHours().get(0).getSnow());
    }

    public List<HourlyData> createListForTest() {
        List<HourlyData> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            HourlyData h = new HourlyData("a", "a", "aa");
            list.add(h);
        }
        return list;
    }
}