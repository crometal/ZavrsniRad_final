package com.example.stjepan.zavrsnirad_v1.menu;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stjepan.zavrsnirad_v1.R;
import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;

import java.util.List;


public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.TotalViewHolder> {

    private Context context;
    private FoodDbHelper mDatabase;
    private List<Food> totalFoodList;
    public MenuAdapter.AdapterListener adapterListener;

    public TotalAdapter(Context context, List<Food> totalFoodList, MenuAdapter.AdapterListener adapterListener){
        this.context = context;
        this.totalFoodList = totalFoodList;
        this.adapterListener = adapterListener;
        mDatabase = new FoodDbHelper(context);
    }

    @Override
    public TotalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_list_item_recycler, parent, false);
        return new TotalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TotalViewHolder holder, int position) {
        holder.foodName.setText(totalFoodList.get(position).getName());
      /*  if (totalFoodList.get(position).getGram() > 0 ) {
            holder.foodName.setText(totalFoodList.get(position).getName());
        } else {
            Toast.makeText(context, "Nema ništa", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public int getItemCount() {
        return totalFoodList.size();
    }

    public class TotalViewHolder extends RecyclerView.ViewHolder{

        public TextView foodName;
        LinearLayout row_linearLayout;

        public TotalViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            row_linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }
}
