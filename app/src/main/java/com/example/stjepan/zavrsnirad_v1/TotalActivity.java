package com.example.stjepan.zavrsnirad_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodContract;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;
import com.example.stjepan.zavrsnirad_v1.menu.MenuAdapter;
import com.example.stjepan.zavrsnirad_v1.menu.TotalAdapter;

import java.util.ArrayList;
import java.util.List;

public class TotalActivity extends AppCompatActivity implements MenuAdapter.AdapterListener {

    private FoodDbHelper mDatabase;
    TotalAdapter mAdapter;
    List <Food> totalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        RecyclerView totalRecycler = (RecyclerView) findViewById(R.id.total_recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        totalRecycler.setLayoutManager(linearLayoutManager);
        totalRecycler.setHasFixedSize(true);

        mDatabase = new FoodDbHelper(this);
        totalList.clear();
        totalList = mDatabase.listFood();



        if (totalList.size() > 0){
            totalRecycler.setVisibility(View.VISIBLE);
            mAdapter = new TotalAdapter(this, totalList, this);
            totalRecycler.setAdapter(mAdapter);
        } else{
            totalRecycler.setVisibility(View.GONE);
        }


/*
        List<Food> foodListNew = new ArrayList<Food>();
                foodListNew.addAll(mDatabase.listFood());

                float prosjek;
                float ukupno;
                for (Food food : foodListNew) {
                    if (food.getGram() > 0) {
                        Toast.makeText(this, "There is no product in the database. ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "hahahah", Toast.LENGTH_SHORT).show();
                    }
                }*/
    }

    @Override
    public void editFood(Food clickedFood, int position) {

    }

    @Override
    public void popupInfo(Food selectedItem, int position) {

    }
}
