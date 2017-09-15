package com.example.stjepan.zavrsnirad_v1.menu;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.R;
import com.example.stjepan.zavrsnirad_v1.TotalActivity;
import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;
import com.example.stjepan.zavrsnirad_v1.models.GramHelp;

import java.io.BufferedReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BreakfastActivity extends AppCompatActivity implements MenuAdapter.AdapterListener {

    private FoodDbHelper mDatabase;
    private FloatingActionButton fab;
    MenuAdapter mAdapter;
    List<Food> allFood = new ArrayList<>();
    List<GramHelp> selectedFood = new ArrayList<>();
    int value;
    RecyclerView productView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        productView = (RecyclerView) findViewById(R.id.menu_recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        Intent intent = getIntent();
        value = intent.getIntExtra("breakfast", -1);

        mDatabase = new FoodDbHelper(this);
        allFood = mDatabase.listFood();

        if (allFood.size() > 0) {
            productView.setVisibility(View.VISIBLE);
            mAdapter = new MenuAdapter(this, allFood, this);
            productView.setAdapter(mAdapter);
        } else {
            productView.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFood.size() == 0) {
                    Toast.makeText(BreakfastActivity.this, "Nisi odabrao namirnicu", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BreakfastActivity.this, TotalActivity.class);
                    intent.putExtra("valueBreakfast", value);
                    intent.putExtra("selectedFood", (ArrayList<GramHelp>) selectedFood);
                    startActivity(intent);
                }
            }
        });
    }

   /* public void showGram(final Food gramFood, final int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.menu_list_item_recycler, null);

        final TextView showGramTv = (TextView) subView.findViewById(R.id.showGram);
      //  final EditText gramField = (EditText) findViewById(R.id.enter_gram);

        if (gramFood != null) {
            showGramTv.setText(String.valueOf(gramFood.getGram()));
        }

        final double gram = Double.parseDouble(showGramTv.getText().toString());

     //   String content = gramField.getText().toString();
     //   showGramTv.setText(content);

        allFood.get(position).setGram(gram);
        mDatabase.listGram();


    }*/

    private void getSearchedName(String searchTerm){
        allFood.clear();
        FoodDbHelper db = new FoodDbHelper(this);
        db.getWritableDatabase();
        Food food = null;
        Cursor cursor = db.searchItem(searchTerm);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            food = new Food();
            food.setId(id);
            food.setName(name);
            allFood.add(food);
        }
        db.close();
        productView.setAdapter(mAdapter);
    }

    private void editTaskDialog(final Food clickedFood, final int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.menu_gram, null);
        final EditText gramField = (EditText) subView.findViewById(R.id.enter_gram);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unesi masu u gramima");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Prihvati", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double gram = Double.parseDouble(gramField.getText().toString());

                if (gram <= 0) {
                    Toast.makeText(BreakfastActivity.this, "Unio si krivu masu. Unesi broj veči od 0", Toast.LENGTH_SHORT).show();
                } else {
                    GramHelp gramHelp = new GramHelp();
                    gramHelp.setId_food(clickedFood.getId());
                    gramHelp.setId_menu(value);
                    gramHelp.setGram(gram);
                    selectedFood.add(gramHelp);

                    //    showGram(food, position);
                }
            }

        });

        builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }
    @Override
    public void editFood(Food clickedFood, int position) {
        editTaskDialog(clickedFood, position);
    }

    @Override
    public void popupInfo(Food selectedItem, int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchedName(newText);
                return false;
            }
        });
        return true;
    }

}