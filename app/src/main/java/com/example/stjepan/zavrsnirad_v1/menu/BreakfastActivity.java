package com.example.stjepan.zavrsnirad_v1.menu;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.stjepan.zavrsnirad_v1.FoodListActivity;
import com.example.stjepan.zavrsnirad_v1.R;
import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodAdapter;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;

import java.util.List;


public class BreakfastActivity extends AppCompatActivity {

    private FoodDbHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView foodView = (RecyclerView)findViewById(R.id.menu_recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        foodView.setLayoutManager(linearLayoutManager);
        foodView.setHasFixedSize(true);

        mDatabase = new FoodDbHelper(this);
        List<Food> allFood = mDatabase.listFood();

        if(allFood.size() > 0){
            foodView.setVisibility(View.VISIBLE);
            MenuAdapter mAdapter = new MenuAdapter(this, allFood);
            foodView.setAdapter(mAdapter);

        }else {
            foodView.setVisibility(View.GONE);
            //Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}