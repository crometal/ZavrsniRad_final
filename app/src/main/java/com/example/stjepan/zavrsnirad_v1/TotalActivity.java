package com.example.stjepan.zavrsnirad_v1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodContract;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;
import com.example.stjepan.zavrsnirad_v1.menu.BreakfastActivity;
import com.example.stjepan.zavrsnirad_v1.menu.MenuActivity;
import com.example.stjepan.zavrsnirad_v1.menu.MenuAdapter;
import com.example.stjepan.zavrsnirad_v1.menu.TotalAdapter;
import com.example.stjepan.zavrsnirad_v1.models.GramHelp;
import com.facebook.stetho.Stetho;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TotalActivity extends AppCompatActivity implements TotalAdapter.AdapterListener {

    private FoodDbHelper mDatabase;
    TotalAdapter mAdapter;
    private FloatingActionButton fab;
    List<Food> foodList = new ArrayList<>();
    List<Food> foodListLunch = new ArrayList<>();
    List<Food> foodListDinner = new ArrayList<>();
    List<GramHelp> selectedFood = new ArrayList<>();

    int breakfast = 1;
    int lunch = 2;
    int dinner = 3;
    int var;
    int vara;
    int varaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        final RecyclerView totalRecycler = (RecyclerView) findViewById(R.id.total_recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        totalRecycler.setLayoutManager(linearLayoutManager);
        totalRecycler.setHasFixedSize(true);
        final TextView showTotal = (TextView) findViewById(R.id.textViewTotalValue);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mDatabase = new FoodDbHelper(this);

        Intent intent = getIntent();
        selectedFood = (ArrayList<GramHelp>) intent.getSerializableExtra("selectedFood");

         var = intent.getIntExtra("valueBreakfast", -1);
         vara = intent.getIntExtra("valueLunch", -1);
         varaa = intent.getIntExtra("valueDinner", -1);

        if (breakfast == var){
            foodList = mDatabase.listTotalFood(selectedFood);
            showTotal.setText(mDatabase.multiplyFat(foodList, selectedFood));
        }  else if (lunch == vara){
            foodListLunch = mDatabase.listTotalFood(selectedFood);
            showTotal.setText(mDatabase.multiplyFat(foodListLunch, selectedFood));
        } else if (dinner == varaa){
            foodListDinner = mDatabase.listTotalFood(selectedFood);
            showTotal.setText(mDatabase.multiplyFat(foodListDinner, selectedFood));
        }

        if (selectedFood.size() > 0) {
            totalRecycler.setVisibility(View.VISIBLE);
            List<Food> list = mDatabase.listSelectedFood(selectedFood);
            mAdapter = new TotalAdapter(this, list);
            totalRecycler.setAdapter(mAdapter);
            //showTotal.setText(mDatabase.multiplyFat(foodList, selectedFood) );
        } else {
            totalRecycler.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final double gram_Total = Double.parseDouble(showTotal.getText().toString());

                DateFormat date = new SimpleDateFormat("MMM dd yyyy, h:mm");
                String dateFormat = date.format(Calendar.getInstance().getTime());


                /*GramHelp gramHelp = new GramHelp();
                gramHelp.setGram_total(gram_Total);
                gramHelp.setDate(Calendar.getInstance().getTime());
                gramHelp.setDate(dateFormat);*/

                for (GramHelp gramHelp : selectedFood) {
                    gramHelp.setDate(dateFormat);
                }

                if (breakfast == var){
                    mDatabase.multiply(foodList, selectedFood);
                }  else if (lunch == vara){
                    mDatabase.multiply(foodListLunch, selectedFood);
                } else if (dinner == varaa){
                    mDatabase.multiply(foodListDinner, selectedFood);
                }

                //mDatabase.multiply(foodList, selectedFood);

                Toast.makeText(TotalActivity.this, "Total saved", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
