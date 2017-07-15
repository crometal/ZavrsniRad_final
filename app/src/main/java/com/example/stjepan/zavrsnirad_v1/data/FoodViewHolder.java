package com.example.stjepan.zavrsnirad_v1.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stjepan.zavrsnirad_v1.R;

/**
 * Created by Stjepan on 15.7.2017..
 */

public class FoodViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView deleteFood;
    public  ImageView editFood;

    public FoodViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.food_name);
        deleteFood = (ImageView)itemView.findViewById(R.id.delete_food);
        editFood = (ImageView)itemView.findViewById(R.id.edit_food);
    }
}
