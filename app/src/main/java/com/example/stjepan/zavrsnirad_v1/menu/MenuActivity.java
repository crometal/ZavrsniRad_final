package com.example.stjepan.zavrsnirad_v1.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stjepan.zavrsnirad_v1.R;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;


public class MenuActivity extends AppCompatActivity {

    FoodDbHelper mDatabase = new FoodDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button breakfastButton = (Button) findViewById(R.id.btnBreakfast);
        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BreakfastActivity.class);
                intent.putExtra("breakfast", mDatabase.listMenuType(1));
                startActivity(intent);
            }
        });

        Button lunchButton = (Button) findViewById(R.id.btnLunch);
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, LunchActivity.class);
                intent.putExtra("lunch", mDatabase.listMenuType(2));
                startActivity(intent);
            }
        });

        Button dinnerButton = (Button) findViewById(R.id.btnDinner);
        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DinnerActivity.class);
                intent.putExtra("dinner", mDatabase.listMenuType(3));
                startActivity(intent);
            }
        });
    }
}
