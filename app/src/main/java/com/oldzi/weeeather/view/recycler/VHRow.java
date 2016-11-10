package com.oldzi.weeeather.view.recycler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.oldzi.weeeather.R;
import com.oldzi.weeeather.city_weather.data.City;

public class VHRow extends VHParent {
    TextView dateTV, temperatureTV, iconTV, conditionTV, sunriseTV, sunsetTV, windspeedTV, windDirTV, humidityTV, snowTV;

    public VHRow(View itemView, Context context) {
        super(itemView, context);
        setupViews();
    }

    private void setupViews() {
        dateTV = (TextView) itemView.findViewById(R.id.date_tv);
        iconTV = (TextView) itemView.findViewById(R.id.icon_tv);
        dateTV = (TextView) itemView.findViewById(R.id.date_tv);
        temperatureTV = (TextView) itemView.findViewById(R.id.min_max_temp);
        conditionTV = (TextView) itemView.findViewById(R.id.condition);
        sunriseTV = (TextView) itemView.findViewById(R.id.sunrise_time);
        sunsetTV = (TextView) itemView.findViewById(R.id.sunset_time);
        windspeedTV = (TextView) itemView.findViewById(R.id.windspeed_value);
        windDirTV = (TextView) itemView.findViewById(R.id.winddir_value);
        humidityTV = (TextView) itemView.findViewById(R.id.humidity_value);
        snowTV = (TextView) itemView.findViewById(R.id.snow_value);
        setupIconTypeface();
    }

    public void updateValues(City city, int position) {

        //boolean isDay = ((city.getDays().get(position).getSunriseHour() < city.getDays().get(0).getHours().get(0).getHour()) && (city.getDays().get(0).getHours().get(0).getHour() < city.getDays().get(0).getSunsetHour()));
        if(position==1)
         dateTV.setText("Tomorrow, " + city.getDays().get(position).getMonth() + " " + city.getDays().get(position).getDay() + city.getDays().get(position).getDaySuffix() + ", " + city.getDays().get(position).getYear());
        else dateTV.setText(city.getDays().get(position).getMonth() + " " + city.getDays().get(position).getDay() + city.getDays().get(position).getDaySuffix() + ", " + city.getDays().get(position).getYear());

        temperatureTV.setText(city.getDays().get(position).getMinTemp() + " - " + city.getDays().get(position).getMaxTemp());
        conditionTV.setText(city.getDays().get(position).getHours().get(0).getCondition());
        Log.d("ICON", "" +city.getDays().get(position).getHours().get(0).getIcon());
        String icon = city.getDays().get(position).getHours().get(0).getIcon();
        iconTV.setText(defineIcon(icon));
        sunriseTV.setText(city.getDays().get(position).getSunriseTime());
        sunsetTV.setText(city.getDays().get(position).getSunsetTime());
        windspeedTV.setText(city.getDays().get(position).getHours().get(0).getWindspeed());
        windDirTV.setText(city.getDays().get(position).getHours().get(0).getWinddir());
        humidityTV.setText(city.getDays().get(position).getHours().get(0).getHumidity());
        snowTV.setText(city.getDays().get(position).getHours().get(0).getSnow());
    }
}

