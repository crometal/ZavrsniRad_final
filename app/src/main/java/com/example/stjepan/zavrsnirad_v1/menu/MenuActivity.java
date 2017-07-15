package com.example.stjepan.zavrsnirad_v1.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stjepan.zavrsnirad_v1.R;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button breakfastButton = (Button) findViewById(R.id.btnBreakfast);
        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BreakfastActivity.class);
                startActivity(intent);
            }
        });

        Button lunchButton = (Button) findViewById(R.id.btnLunch);
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, LunchActivity.class);
                startActivity(intent);
            }
        });

        Button dinnerButton = (Button) findViewById(R.id.btnDinner);
        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DinnerActivity.class);
                startActivity(intent);
            }
        });
    }
}
