package com.oldzi.weeeather.city_saved;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.oldzi.weeeather.R;
import com.oldzi.weeeather.city_searching.SearchRecyclerAdapter;

import java.util.List;

public class UserCitiesActivity extends AppCompatActivity {
    private UserCitiesPresenter presenter;
    private RecyclerView recyclerView;
    private RecyclerViewDragDropManager dragMgr;

    public void onActivityFinish() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.finishWithoutWeather();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_cities);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        presenter = new UserCitiesPresenter(this);

        // Setup D&D feature and RecyclerView
        dragMgr = new RecyclerViewDragDropManager();
        dragMgr.setInitiateOnMove(false);
        dragMgr.setInitiateOnLongPress(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SearchRecyclerAdapter.RecyclerItemClickListener(this, recyclerView, new SearchRecyclerAdapter.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.onItemClicked(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        presenter.getUserCities();

        Snackbar.make(findViewById(R.id.container), "TIP: Long press item to change order!", Snackbar.LENGTH_LONG).show();
    }

    public void onCitiesFound(List<UserCity> cities) {
        UserCityAdapter adapter = new UserCityAdapter(UserCitiesActivity.this, cities);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                presenter.onItemMoved(fromPosition, toPosition);
            }


        });
        recyclerView.setAdapter(dragMgr.createWrappedAdapter(adapter));
        dragMgr.attachRecyclerView(recyclerView);
    }

    static class UserCityHolder extends AbstractDraggableItemViewHolder {
        TextView textView;
        Context context;
        Typeface typeface;
        public UserCityHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/inconsolata.ttf");
            textView = (TextView) itemView.findViewById(R.id.city_name);
            textView.setPaintFlags(textView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            textView.setTypeface(typeface);
        }
    }

    static class UserCityAdapter extends RecyclerView.Adapter<UserCityHolder> implements DraggableItemAdapter<UserCityHolder> {
        List<UserCity> userCities;
        Context context;


        public UserCityAdapter(Context context, List<UserCity> cities) {
            setHasStableIds(true); // this is required for D&D feature.
            this.context = context;
            userCities = cities;
        }

        @Override
        public long getItemId(int position) {
            return userCities.get(position).getId(); // need to return stable (= not change even after reordered) value
        }

        @Override
        public UserCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_user_cities, parent, false);
            return new UserCityHolder(v, context);
        }

        @Override
        public void onBindViewHolder(UserCityHolder holder, int position) {
            UserCity item = userCities.get(position);
            holder.textView.setText(item.getName());
        }

        @Override
        public int getItemCount() {
            return userCities.size();
        }

        @Override
        public void onMoveItem(int fromPosition, int toPosition) {
            UserCity movedItem = userCities.remove(fromPosition);
            Log.d("KUPA", "City " + movedItem.getName() + " with id " + movedItem.getId() +" was moved from " + fromPosition + " to " + toPosition);
            userCities.add(toPosition, movedItem);
            notifyItemMoved(fromPosition, toPosition);

        }

        @Override
        public boolean onCheckCanStartDrag(UserCityHolder holder, int position, int x, int y) {
            return true;
        }

        @Override
        public ItemDraggableRange onGetItemDraggableRange(UserCityHolder holder, int position) {
            return null;
        }

        @Override
        public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
            return true;
        }

    }


}
