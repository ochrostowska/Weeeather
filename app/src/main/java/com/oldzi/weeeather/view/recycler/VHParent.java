package com.oldzi.weeeather.view.recycler;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oldzi.weeeather.R;
import com.oldzi.weeeather.view.ExpandablePanel;

/**
 * Created by Oldzi on 02.09.2016.
 */
public class VHParent extends RecyclerView.ViewHolder {
    private TextView cityNameTV, updatedTV, temperatureTV, iconTV, conditionTV, sunriseTV, sunsetTV, windspeedTV, windDirTV, humidityTV, snowTV, minTempTV, maxTempTV;
    private TextView iconHumidity, iconWindSpeed, iconWindDir, iconSnow, iconSunset, iconSunrise, iconCelsius;
    private ExpandablePanel panel;
    private ImageView arrow;
    private TextView button;
    private Context context;
    private Typeface weatherFont, typeface;

    public VHParent(View itemView, final Context context) {
        super(itemView);
        this.context = context;
        panel = (ExpandablePanel) itemView.findViewById(R.id.expandablePanel);
        weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/inconsolata.ttf");
        button = (TextView) itemView.findViewById(R.id.expand);
        arrow = (ImageView) itemView.findViewById(R.id.expand_arrow);
        button.setTypeface(typeface);
        setupViews();

        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                button.setText(context.getString(R.string.show_me_more));
                arrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_down));

            }

            public void onExpand(View handle, View content) {
                button.setText(context.getString(R.string.show_me_less));
                arrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_up));
            }
        });
    }

    private void setupViews() {
        cityNameTV = (TextView) itemView.findViewById(R.id.cityNameToolbar);
        updatedTV = (TextView) itemView.findViewById(R.id.updated_field);
        temperatureTV = (TextView) itemView.findViewById(R.id.current_temperature_tv);
        conditionTV = (TextView) itemView.findViewById(R.id.condition);
        sunriseTV = (TextView) itemView.findViewById(R.id.sunrise_time);
        sunsetTV = (TextView) itemView.findViewById(R.id.sunset_time);
        windspeedTV = (TextView) itemView.findViewById(R.id.windspeed_value);
        windDirTV = (TextView) itemView.findViewById(R.id.winddir_value);
        humidityTV = (TextView) itemView.findViewById(R.id.humidity_value);
        snowTV = (TextView) itemView.findViewById(R.id.snow_value);
        setupIconTypeface();
    }

    public void setupIconTypeface() {
        iconHumidity = (TextView) itemView.findViewById(R.id.humidity_icon);
        iconWindSpeed = (TextView) itemView.findViewById(R.id.windspeed_icon);
        iconWindDir = (TextView) itemView.findViewById(R.id.winddir_icon);
        iconSnow = (TextView) itemView.findViewById(R.id.snow_icon);
        iconSunrise = (TextView) itemView.findViewById(R.id.sunrise_icon);
        iconSunset = (TextView) itemView.findViewById(R.id.sunset_icon);
        iconTV = (TextView) itemView.findViewById(R.id.icon_tv);
        iconCelsius = (TextView) itemView.findViewById(R.id.celsius_tv);
        iconSunrise.setTypeface(weatherFont);
        iconSunset.setTypeface(weatherFont);
        iconHumidity.setTypeface(weatherFont);
        iconWindSpeed.setTypeface(weatherFont);
        iconWindDir.setTypeface(weatherFont);
        iconSnow.setTypeface(weatherFont);
        iconTV.setTypeface(weatherFont);
        iconCelsius.setTypeface(weatherFont);
    }

    public void setupTypeface(TextView view) {
        view.setTypeface(typeface);
    }

    public String defineMaxim(String iconName) {
        String icon;
        switch(iconName) {
            case "chanceflurries":
                icon = context.getString(R.string.maxim_rainy1);
                break;
            case "chancerain":
                icon = context.getString(R.string.maxim_rainy2);
                break;
            case "chancesleet":
                icon = context.getString(R.string.maxim_rainy3);
                break;
            case "chancesnow":
                icon = context.getString(R.string.maxim_snow1);
                break;
            case "chancestorms":
                icon = context.getString(R.string.maxim_rainy2);
                break;
            case "clear":
                icon = context.getString(R.string.maxim_sunny1);
                break;
            case "flurries":
                icon = context.getString(R.string.maxim_rainy2);
                break;
            case "fog":
                icon = context.getString(R.string.maxim_foggy);
                break;
            case "hazy":
                icon = context.getString(R.string.maxim_foggy);
                break;
            case "mostlycloudy":
                icon = context.getString(R.string.maxim_cloudy1);
                break;
            case "mostlysunny":
                icon = context.getString(R.string.maxim_sunny1);
                break;
            case "partlycloudy":
                icon = context.getString(R.string.maxim_cloudy1);
                break;
            case "partlysunny":
                icon = context.getString(R.string.maxim_cloudy1);
                break;
            case "sleet":
                icon = context.getString(R.string.maxim_rainy3);
                break;
            case "rain":
                icon = context.getString(R.string.maxim_rainy1);
                break;
            case "snow":
                icon = context.getString(R.string.maxim_snow1);
                break;
            case "sunny":
                icon = context.getString(R.string.maxim_sunny1);
                break;
            case "tstorms":
                icon = context.getString(R.string.maxim_rainy3);
                break;
            default:
                icon = context.getString(R.string.maxim_default);
                break;
        }
        return icon;
    }


    public String defineIcon(String iconName) {
        String icon;
        switch (iconName) {
            case "chanceflurries":
                icon = context.getString(R.string.wi_rain_mix);
                break;
            case "chancerain":
                icon = context.getString(R.string.wi_rain);
                break;
            case "chancesleet":
                icon = context.getString(R.string.wi_sleet);
                break;
            case "chancesnow":
                icon = context.getString(R.string.wi_snow);
                break;
            case "chancestorms":
                icon = context.getString(R.string.wi_storm);
                break;
            case "clear":
                icon = context.getString(R.string.wi_sun);
                break;
            case "flurries":
                icon = context.getString(R.string.wi_rain_mix);
                break;
            case "fog":
                icon = context.getString(R.string.wi_fog);
                break;
            case "hazy":
                icon = context.getString(R.string.wi_fog);
                break;
            case "mostlycloudy":
                icon = context.getString(R.string.wi_mostly_cloudy);
                break;
            case "mostlysunny":
                icon = context.getString(R.string.wi_mosly_sunny);
                break;
            case "partlycloudy":
                icon = context.getString(R.string.wi_mostly_cloudy);
                break;
            case "partlysunny":
                icon = context.getString(R.string.wi_mostly_cloudy);
                break;
            case "sleet":
                icon = context.getString(R.string.wi_sleet);
                break;
            case "rain":
                icon = context.getString(R.string.wi_rain);
                break;
            case "snow":
                icon = context.getString(R.string.wi_snow);
                break;
            case "sunny":
                icon = context.getString(R.string.wi_sun);
                break;
            case "tstorms":
                icon = context.getString(R.string.wi_storm);
                break;
            default:
                icon = context.getString(R.string.wi_cloudy);
                break;
        }
        return icon;
    }
}

