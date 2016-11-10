package com.oldzi.weeeather.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oldzi.weeeather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 09.11.2016.
 */

public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.VHHourly> {

    private List<HourlyData> hourlyData = new ArrayList<>();

    public HourlyRecyclerAdapter(List<HourlyData> hourlyData) {
        this.hourlyData = hourlyData;
    }

    @Override
    public VHHourly onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hourly, parent, false);
        return new VHHourly(view);
    }

    @Override
    public void onBindViewHolder(VHHourly holder, int position) {
        HourlyData data = hourlyData.get(position);
        holder.hour.setText(data.getHour());
        holder.icon.setText(data.getIcon());
        holder.temperature.setText(data.getTemperature());
    }

    @Override
    public int getItemCount() {
        if (hourlyData == null) return 0;
        return hourlyData.size();
    }

    public class VHHourly extends RecyclerView.ViewHolder {
        private TextView hour;
        private TextView icon;
        private TextView temperature;

        public VHHourly(View v) {
            super(v);
            hour = (TextView) v.findViewById(R.id.current_hour_tv);
            icon = (TextView) v.findViewById(R.id.current_temperature_icon);
            temperature = (TextView) v.findViewById(R.id.current_temperature_tv);
        }
    }


}