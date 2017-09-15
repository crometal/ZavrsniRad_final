package com.example.stjepan.zavrsnirad_v1;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodAdapter;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FoodListActivity extends AppCompatActivity /*implements SearchView.OnQueryTextListener */ {

    private FoodDbHelper mDatabase;
    FoodAdapter mAdapter;
    List<Food> allFood = new ArrayList<>();
    RecyclerView productView;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist_recycler);

        productView = (RecyclerView)findViewById(R.id.recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
       // searchView = (SearchView) findViewById(R.id.searchAJDEEE);
        mAdapter = new FoodAdapter(this, allFood);

        mDatabase = new FoodDbHelper(this);
        List<Food> allFood = mDatabase.listFood();


        if(allFood.size() > 0){
            productView.setVisibility(View.VISIBLE);
            FoodAdapter mAdapter = new FoodAdapter(this, allFood);
            productView.setAdapter(mAdapter);

        }else {
            productView.setVisibility(View.GONE);
            Toast.makeText(this, "Nema namirnica u bazi. Dodaj novu.", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(FoodListActivity.this, EditorActivity.class);
                //startActivity(intent);
                addTaskDialog();
            }
        });

     /*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchedName(newText);
                return false;
            }
        });*/

    }

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

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_editor_recycler, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText fatField = (EditText)subView.findViewById(R.id.enter_fat);
        final EditText omega3Field = (EditText)subView.findViewById(R.id.enter_omega3);
        final EditText omega6Field = (EditText)subView.findViewById(R.id.enter_omega6);
        final EditText proteinsField = (EditText)subView.findViewById(R.id.enter_proteins);
        final EditText carboField = (EditText)subView.findViewById(R.id.enter_carbo);
        final EditText energyField = (EditText)subView.findViewById(R.id.enter_energy);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodaj novu namirnicu");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("DODAJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String fat = fatField.getText().toString();

                final String omega3 = omega3Field.getText().toString();
                final String omega6 = omega6Field.getText().toString();
                final String proteins = proteinsField.getText().toString();
                final String carbo = carboField.getText().toString();
                final String energy = energyField.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(fat)){
                    Toast.makeText(FoodListActivity.this, "Pogrešno upisana vrijednost", Toast.LENGTH_LONG).show();
                }
                else{
                    Food newFood = new Food();
                    newFood.setName(name);
                    newFood.setFat(Double.parseDouble(fat));
                    newFood.setOmega3(Double.parseDouble(omega3));
                    newFood.setOmega6(Double.parseDouble(omega6));
                    newFood.setProteins(Double.parseDouble(proteins));
                    newFood.setCarbo(Double.parseDouble(carbo));
                    newFood.setEnergy(Double.parseDouble(energy));

                    mDatabase.addFood(newFood);

                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("ODUSTANI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(FoodListActivity.this, "", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
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

















