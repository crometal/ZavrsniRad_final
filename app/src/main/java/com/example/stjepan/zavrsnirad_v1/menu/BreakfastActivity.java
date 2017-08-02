package com.example.stjepan.zavrsnirad_v1.menu;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.R;
import com.example.stjepan.zavrsnirad_v1.TotalActivity;
import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodContract;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class BreakfastActivity extends AppCompatActivity implements MenuAdapter.AdapterListener {

    private FoodDbHelper mDatabase;
    private FloatingActionButton fab;
    MenuAdapter mAdapter;
    List<Food> allFood = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        RecyclerView foodViewRecycler = (RecyclerView) findViewById(R.id.menu_recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        foodViewRecycler.setLayoutManager(linearLayoutManager);
        foodViewRecycler.setHasFixedSize(true);

        mDatabase = new FoodDbHelper(this);
        allFood.clear();
        allFood = mDatabase.listFood();

        if (allFood.size() > 0) {
            foodViewRecycler.setVisibility(View.VISIBLE);
            mAdapter = new MenuAdapter(this, allFood, this);
            foodViewRecycler.setAdapter(mAdapter);

        } else {
            foodViewRecycler.setVisibility(View.GONE);
            //Toast.makeText(this, "There is no product in the database. ", Toast.LENGTH_LONG).show();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakfastActivity.this, TotalActivity.class);
                startActivity(intent);



            }
        });
    }
/*
    private void popupInfo (final Food food, final int position){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.popup_food_info, null);

        final TextView displayName = (TextView) findViewById(R.id.tvShowName);
        final TextView displayFat = (TextView) findViewById(R.id.tvShowFat);
        final TextView displayOmega3 = (TextView) findViewById(R.id.tvShowOmega3);
        final TextView displayOmega6 = (TextView) findViewById(R.id.tvShowOmega6);
        final TextView displayProteins = (TextView) findViewById(R.id.tvShowProteins);
        final TextView displayCarbo = (TextView) findViewById(R.id.tvShowCarbo);
        final TextView displayEnergy = (TextView) findViewById(R.id.tvShowEnergy);

        displayName.getText().toString();
    }*/

    public void showGram(final Food gramFood, final int position){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.menu_list_item_recycler, null);

        final TextView showGramTv = (TextView) subView.findViewById(R.id.showGram);
        if (gramFood != null){
            showGramTv.setText(String.valueOf(gramFood.getGram()));
        }

        final double gram = Double.parseDouble(showGramTv.getText().toString());

        mDatabase.listGram();
        allFood.get(position).setGram(gram);
    }

    private void editTaskDialog(final Food food, final int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.menu_gram, null);

        final EditText gramField = (EditText) subView.findViewById(R.id.enter_gram);

        if (food != null) {
            gramField.setText(String.valueOf(food.getGram()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unesi željenu gramažu");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Prihvati", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double gram = Double.parseDouble(gramField.getText().toString());

                if (gram <= 0) {
                    Toast.makeText(BreakfastActivity.this, "Krivo si unio gramažu", Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase.updateGram(new Food(food.getId(), gram));
                    allFood.get(position).setGram(gram);

                    mAdapter.notifyDataSetChanged();

                    showGram(food, position);

                    Toast.makeText(BreakfastActivity.this, "Spremljeno u bazi", Toast.LENGTH_SHORT).show();
                }
            }

        });

        builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BreakfastActivity.this, "Bravo odusto si", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }




    @Override
    public void editFood(Food food, int position) {
        editTaskDialog(food, position);
    }

    @Override
    public void popupInfo(Food selectedItem, int position) {

    }
}