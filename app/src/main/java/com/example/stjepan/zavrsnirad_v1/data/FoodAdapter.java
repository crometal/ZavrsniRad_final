package com.example.stjepan.zavrsnirad_v1.data;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.R;

import java.util.List;

/**
 * Created by Stjepan on 15.7.2017..
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder>{

    private Context context;
    private List<Food> listFood;

    private FoodDbHelper mDatabase;

    public FoodAdapter(Context context, List<Food> listFood) {
        this.context = context;
        this.listFood = listFood;
        mDatabase = new FoodDbHelper(context);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        final Food singleFood = listFood.get(position);

        holder.name.setText(singleFood.getName());

        holder.editFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleFood);
            }
        });

        holder.deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteFood(singleFood.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }


    private void editTaskDialog(final Food food){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_editor_recycler, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText fatField = (EditText)subView.findViewById(R.id.enter_fat);
        final EditText omega3Field = (EditText)subView.findViewById(R.id.enter_omega3);
        final EditText omega6Field = (EditText)subView.findViewById(R.id.enter_omega6);
        final EditText proteinsField = (EditText)subView.findViewById(R.id.enter_proteins);
        final EditText carboField = (EditText)subView.findViewById(R.id.enter_carbo);
        final EditText energyField = (EditText)subView.findViewById(R.id.enter_energy);

        if(food != null){
            nameField.setText(food.getName());
            fatField.setText(String.valueOf(food.getFat()));
            omega3Field.setText(String.valueOf(food.getOmega3()));
            omega6Field.setText(String.valueOf(food.getOmega6()));
            proteinsField.setText(String.valueOf(food.getProteins()));
            carboField.setText(String.valueOf(food.getCarbo()));
            energyField.setText(String.valueOf(food.getEnergy()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Uredi namirnicu");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("UREDI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final double fat = Double.parseDouble(fatField.getText().toString());
                final double omega3 = Double.parseDouble(omega3Field.getText().toString());
                final double omega6 = Double.parseDouble(omega6Field.getText().toString());
                final double proteins = Double.parseDouble(proteinsField.getText().toString());
                final double carbo = Double.parseDouble(carboField.getText().toString());
                final double energy = Double.parseDouble(energyField.getText().toString());

                if(TextUtils.isEmpty(name) || fat <= 0){
                    Toast.makeText(context, "Došlo je do pogreške. Provjeri unesene vrijednosti.", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateFood(new Food(food.getId(), name, fat, omega3, omega6, proteins, carbo, energy));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("ODUSTANI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(context, "", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
