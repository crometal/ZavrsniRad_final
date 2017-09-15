package com.example.stjepan.zavrsnirad_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;
import com.example.stjepan.zavrsnirad_v1.menu.MenuAdapter;
import com.example.stjepan.zavrsnirad_v1.menu.TotalAdapter;
import com.example.stjepan.zavrsnirad_v1.models.GramHelp;
import com.example.stjepan.zavrsnirad_v1.models.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.AdapterListener {

    FoodDbHelper mDatabase;
    List<History> listHistory = new ArrayList<>();
    HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final RecyclerView historyRecycler = (RecyclerView) findViewById(R.id.history_recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyRecycler.setHasFixedSize(true);

        mDatabase = new FoodDbHelper(this);
        listHistory = mDatabase.listHistory();

        if (listHistory.size() > 0) {
            historyRecycler.setVisibility(View.VISIBLE);
            mAdapter = new HistoryAdapter(this, listHistory, this);
            historyRecycler.setAdapter(mAdapter);
        } else {
            historyRecycler.setVisibility(View.GONE);
            Toast.makeText(HistoryActivity.this, "Nema podataka", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void popupInfo(History selectedItem, int position) {

    }
}
