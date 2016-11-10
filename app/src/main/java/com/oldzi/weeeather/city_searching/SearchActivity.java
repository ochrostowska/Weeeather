package com.oldzi.weeeather.city_searching;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.oldzi.weeeather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 05.09.2016.
 */
public class SearchActivity extends AppCompatActivity {

    private SearchPresenter searchPresenter;
    private SearchRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private String query;
    private List<SearchItem> cityRecords = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchPresenter = new SearchPresenter(this);
        setupView();
    }

    private void setupView() {
        EditText searchCityET = (EditText) findViewById(R.id.search_city_ET);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchRecyclerAdapter(cityRecords);
        recyclerView.setAdapter(adapter);
        searchCityET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                query = charSequence.toString();
                if (query.isEmpty()) onCityNotFound();
                else searchPresenter.findCities(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        recyclerView.addOnItemTouchListener(new SearchRecyclerAdapter.RecyclerItemClickListener(this, recyclerView, new SearchRecyclerAdapter.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                searchPresenter.cityClicked(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                searchPresenter.cityLongClicked(position);
            }
        }));
    }

    public void onCityFound(List<SearchItem> cityNames) {
        cityRecords.clear();
        cityRecords.addAll(cityNames);
        adapter.notifyDataSetChanged();
    }

    public void onCityNotFound() {
        cityRecords.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        searchPresenter.finishWithoutWeather();
    }

    public void onActivityFinish() {
        finish();
    }
}
