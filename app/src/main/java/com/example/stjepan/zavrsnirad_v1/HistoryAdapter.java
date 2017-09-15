package com.example.stjepan.zavrsnirad_v1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stjepan.zavrsnirad_v1.data.Food;
import com.example.stjepan.zavrsnirad_v1.data.FoodContract;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;
import com.example.stjepan.zavrsnirad_v1.models.GramHelp;
import com.example.stjepan.zavrsnirad_v1.models.History;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private FoodDbHelper mDatabase;
    private List<Food> foodList;
    private List<History> gramList;
    public AdapterListener adapterListener;

    public HistoryAdapter(Context context, List<History> gramList, AdapterListener adapterListener){
        this.context = context;
        this.gramList = gramList;
        this.adapterListener = adapterListener;
        mDatabase = new FoodDbHelper(context);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item_recycler, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryViewHolder holder, final int position) {
        final History singleGram = gramList.get(position);

       // DateFormat datum = new SimpleDateFormat("MMM dd yyyy, h:mm");
        //String date = datum.format(Calendar.getInstance().getTime());
        holder.date.setText(singleGram.getDate());
        holder.menuType.setText(mDatabase.listMenuHistory(singleGram.getId_menu()));

        //holder.date.setText(gramList.get(position).getDate());

        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popupInfoDialog(singleGram, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gramList.size();
    }

    public void popupInfoDialog(final History selectedItem, final int position){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.popup_history_info, null);

        final TextView menuTv = (TextView) subView.findViewById(R.id.textViewShowObrok);
        final TextView totalFatTv = (TextView) subView.findViewById(R.id.textViewShowFatGram);

        if (selectedItem != null){
            menuTv.setText(mDatabase.listMenuHistory(selectedItem.getId_menu()));
            totalFatTv.setText(String.valueOf(selectedItem.getTotal()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ukupne masti za:");
        builder.setView(subView);
        builder.create();
        builder.show();

       // final String menu = (menuTv.getText().toString());
       // final double totalFat = Double.parseDouble(totalFatTv.getText().toString());

       // mDatabase.listHistoryInfo();
       // gramList.get(position).setId_menu(selectedItem.getId_menu());
       // gramList.get(position).setTotal(totalFat);
    }

    public interface AdapterListener {
        void popupInfo (History selectedItem, int position);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView date;
        public TextView menuType;

        public HistoryViewHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.textViewDate);
            menuType = (TextView) itemView.findViewById(R.id.textViewMenuType);

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterListener.popupInfo(gramList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
