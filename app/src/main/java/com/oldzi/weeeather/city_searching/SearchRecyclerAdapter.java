package com.oldzi.weeeather.city_searching;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oldzi.weeeather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 26.07.2016.
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchRecyclerViewHolder> {

    private List<SearchItem> cities = new ArrayList<>();

    public SearchRecyclerAdapter(List<SearchItem> cities) {
        this.cities = cities;
    }

    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_search, parent, false);
        SearchRecyclerViewHolder holder = new SearchRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchRecyclerViewHolder holder, int position) {
        SearchItem records = cities.get(position);
        holder.cityName.setText(records.getCityName());
        holder.countryName.setText(records.getCountry());
    }

    @Override
    public int getItemCount() {
        if (cities == null) return 0;
        return cities.size();
    }

    public static class SearchRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        TextView countryName;

        public SearchRecyclerViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.rowCity);
            countryName = (TextView) itemView.findViewById(R.id.rowCountry);
        }
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        private OnItemClickListener clickListener;
        private GestureDetector gestureDetector;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
            void onItemLongClick(View view, int position);
        }

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, final OnItemClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onItemLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onItemLongClick(childView, rv.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}
