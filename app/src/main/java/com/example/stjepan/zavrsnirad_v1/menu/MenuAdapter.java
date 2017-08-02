package com.example.stjepan.zavrsnirad_v1.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stjepan.zavrsnirad_v1.R;
import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;

import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private FoodDbHelper mDatabase;
    private List<Food> foodList;
    public AdapterListener adapterListener;

    public MenuAdapter(Context context, List<Food> foodList, AdapterListener adapterListener) {
        this.context = context;
        this.foodList = foodList;
        mDatabase = new FoodDbHelper(context);
        this.adapterListener = adapterListener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item_recycler, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        final Food singleFood = foodList.get(position);

        if(foodList.get(position).getGram() > 0) {
            holder.row_linearlayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }else{
            holder.row_linearlayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.foodName.setText(foodList.get(position).getName());
        holder.foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupInfoDialog(singleFood, position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }



    public void popupInfoDialog(final Food selectedItem, final int position){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.popup_food_info, null);

        final TextView nameTv = (TextView)subView.findViewById(R.id.tvShowName);
        final TextView fatTv = (TextView) subView.findViewById(R.id.tvShowFat);
        final TextView omega3Tv = (TextView) subView.findViewById(R.id.tvShowOmega3);
        final TextView omega6Tv = (TextView) subView.findViewById(R.id.tvShowOmega6);
        final TextView proteinsTv = (TextView) subView.findViewById(R.id.tvShowProteins);
        final TextView carboTv = (TextView) subView.findViewById(R.id.tvShowCarbo);
        final TextView energyTv = (TextView) subView.findViewById(R.id.tvShowEnergy);

        if (selectedItem != null){
            nameTv.setText(selectedItem.getName());
            fatTv.setText(String.valueOf(selectedItem.getFat()));
            omega3Tv.setText(String.valueOf(selectedItem.getOmega3()));
            omega6Tv.setText(String.valueOf(selectedItem.getOmega6()));
            proteinsTv.setText(String.valueOf(selectedItem.getProteins()));
            carboTv.setText(String.valueOf(selectedItem.getCarbo()));
            energyTv.setText(String.valueOf(selectedItem.getEnergy()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Info o namirnici");
        builder.setView(subView);
        builder.create();
        builder.show();

        final String name = nameTv.getText().toString();
        final double fat = Double.parseDouble(fatTv.getText().toString());
        final double omega3 = Double.parseDouble(omega3Tv.getText().toString());
        final double omega6 = Double.parseDouble(omega6Tv.getText().toString());
        final double proteins = Double.parseDouble(proteinsTv.getText().toString());
        final double carbo = Double.parseDouble(carboTv.getText().toString());
        final double energy = Double.parseDouble(energyTv.getText().toString());

        mDatabase.listFood();
        foodList.get(position).setName(name);
        foodList.get(position).setFat(fat);
        foodList.get(position).setOmega3(omega3);
        foodList.get(position).setOmega6(omega6);
        foodList.get(position).setProteins(proteins);
        foodList.get(position).setCarbo(carbo);
        foodList.get(position).setEnergy(energy);
    }

    public interface AdapterListener {
        void editFood(Food clickedFood, int position);
        void popupInfo (Food selectedItem, int position);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView foodName;
        public ImageView enterGram;
        public TextView showGram;
        public EditText editGram;
        LinearLayout row_linearlayout;


        public MenuViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            enterGram = (ImageView) itemView.findViewById(R.id.enter_gram_imageView);
            showGram = (TextView) itemView.findViewById(R.id.showGram);
            editGram = (EditText) itemView.findViewById(R.id.enter_gram);
            row_linearlayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            enterGram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterListener.editFood(foodList.get(getAdapterPosition()), getAdapterPosition());
                   // showGram.setText(editGram.getText().toString());
                }
            });

            foodName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterListener.popupInfo(foodList.get(getAdapterPosition()), getAdapterPosition());
                }
            });

        }

    }

}


