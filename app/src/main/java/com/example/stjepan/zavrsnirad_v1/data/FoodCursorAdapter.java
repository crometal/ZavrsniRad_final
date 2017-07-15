package com.example.stjepan.zavrsnirad_v1.data;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.stjepan.zavrsnirad_v1.data.FoodContract.FoodEntry;

import com.example.stjepan.zavrsnirad_v1.R;

public class FoodCursorAdapter extends CursorAdapter {

    public FoodCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_NAME);
        int fatColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FAT_TOTAL);

        String foodName = cursor.getString(nameColumnIndex);
        String foodFat = cursor.getString(fatColumnIndex);

        nameTextView.setText(foodName);
        summaryTextView.setText(foodFat);
    }
}
