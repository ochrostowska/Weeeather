package com.oldzi.weeeather.view.recycler;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oldzi.weeeather.R;
import com.oldzi.weeeather.city_weather.data.City;
import com.oldzi.weeeather.view.DrawableModifier;

/**
 * Created by Oldzi on 16.08.2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;


    Context context;
    City city = null;


    public MyRecyclerAdapter(Context context, City city) {
        this.context = context;
        this.city = city;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_header, parent, false);
            return new VHHeader(v, context);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_main, parent, false);
            return new VHRow(v, context);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHHeader) {
            VHHeader VHheader = (VHHeader) holder;
            if (city != null) {
                VHheader.updateValues(city);
            }
        } else if (holder instanceof VHRow) {
            VHRow VHitem = (VHRow) holder;
            ImageView iv = (ImageView) holder.itemView.findViewById(R.id.row_clouds_image_view);
            VHitem.updateValues(city, position);
            if (position == 1) {
                DrawableModifier
                        .withContext(context)
                        .withItemColor(R.color.coffeePink3)
                        .withDrawable(ContextCompat.getDrawable(context, R.drawable.cloud_row))
                        .modify()
                        .applyTo(iv);
                iv.setBackgroundColor(ContextCompat.getColor(context, R.color.coffeePink4));
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.coffeePink3)); //and so on..
            } else if (position == 2) {
                DrawableModifier
                        .withContext(context)
                        .withItemColor(R.color.coffeePink4)
                        .withDrawable(ContextCompat.getDrawable(context, R.drawable.cloud_row_purple))
                        .modify()
                        .applyTo(iv);
                iv.setBackgroundColor(ContextCompat.getColor(context, R.color.coffeePink5));
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.coffeePink4)); //and so on..
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
