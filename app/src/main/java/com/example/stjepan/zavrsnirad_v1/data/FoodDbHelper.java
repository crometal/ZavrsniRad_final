package com.example.stjepan.zavrsnirad_v1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stjepan.zavrsnirad_v1.data.FoodContract.FoodEntry;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.thickness;

public class FoodDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FoodDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "foodlist.db";
    private static final int DATABASE_VERSION = 10;

    public FoodDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** Create a String that contains the SQL statement to create the food table */
        String SQL_CREATE_FOOD_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME + " ("
                + FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.COLUMN_FOOD_NAME + " TEXT NOT NULL, "
                + FoodEntry.COLUMN_FAT_TOTAL + " DECIMAL(3,1) NOT NULL, "
                + FoodEntry.COLUMN_OMEGA3 + " DECIMAL(2,1), "
                + FoodEntry.COLUMN_OMEGA6 + " DECIMAL(2,1), "
                + FoodEntry.COLUMN_PROTEINS + " DECIMAL(3,1), "
                + FoodEntry.COLUMN_CARBOHYDRATES + " DECIMAL(3,1), "
            //    + FoodEntry.COLUMN_FIBERS + " DECIMAL(3,1), "
                + FoodEntry.COLUMN_ENERGY + " DECIMAL(3,1)" +")";
        db.execSQL(SQL_CREATE_FOOD_TABLE);

        String SQL_CREATE_MENU_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME_MENU + " ("
                +FoodEntry._ID_MENU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +FoodEntry.COLUMN_BREAKFAST + " DECIMAL(3,1),"
                +FoodEntry.COLUMN_LUNCH + " DECIMAL(3,1),"
                +FoodEntry.COLUMN_DINNER + " DECIMAL(3,1),"
                +FoodEntry.COLUMN_GRAM + " DECIMAL(3,1)" +")";
        db.execSQL(SQL_CREATE_MENU_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + FoodEntry.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }



    public void deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FoodEntry.TABLE_NAME, FoodEntry._ID	+ "	= ?", new String[] { String.valueOf(id)});
    }


    public void updateFood(Food food){
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, food.getName());
        values.put(FoodEntry.COLUMN_FAT_TOTAL, food.getFat());
        values.put(FoodEntry.COLUMN_OMEGA3, food.getOmega3());
        values.put(FoodEntry.COLUMN_OMEGA6, food.getOmega6());
        values.put(FoodEntry.COLUMN_PROTEINS, food.getProteins());
        values.put(FoodEntry.COLUMN_CARBOHYDRATES, food.getCarbo());
        values.put(FoodEntry.COLUMN_ENERGY, food.getEnergy());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(FoodEntry.TABLE_NAME, values, FoodEntry._ID	+ "	= ?", new String[] { String.valueOf(food.getId())});
    }

    public void updateGram(Food food){
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_GRAM, food.getGram());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(FoodEntry.TABLE_NAME_MENU, values, FoodEntry._ID_MENU + " =?", new String[] {String.valueOf(food.getId())});
    }

    public List<Food> listFood(){
        String sql = "SELECT * FROM " + FoodEntry.TABLE_NAME + " ORDER BY " +FoodEntry.COLUMN_FOOD_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Food> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                double fat = Double.parseDouble(cursor.getString(2));
                double omega3 = Double.parseDouble(cursor.getString(3));
                double omega6 = Double.parseDouble(cursor.getString(4));
                double proteins = Double.parseDouble(cursor.getString(5));
                double carbo = Double.parseDouble(cursor.getString(6));
                double energy = Double.parseDouble(cursor.getString(7));
                storeProducts.add(new Food(id, name, fat, omega3, omega6, proteins, carbo, energy));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public List<Food> listGram(){
        String sql = "SELECT " +FoodEntry._ID_MENU + " , " +FoodEntry.COLUMN_GRAM + " FROM " +FoodEntry.TABLE_NAME_MENU;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Food> storeGram = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                double gram = Double.parseDouble(cursor.getString(1));
                storeGram.add(new Food(id, gram));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeGram;
    }


    public void addFood(Food food){
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, food.getName());
        values.put(FoodEntry.COLUMN_FAT_TOTAL, food.getFat());
        values.put(FoodEntry.COLUMN_OMEGA3, food.getOmega3());
        values.put(FoodEntry.COLUMN_OMEGA6, food.getOmega6());
        values.put(FoodEntry.COLUMN_PROTEINS, food.getProteins());
        values.put(FoodEntry.COLUMN_CARBOHYDRATES, food.getCarbo());
        values.put(FoodEntry.COLUMN_ENERGY, food.getEnergy());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(FoodEntry.TABLE_NAME, null, values);
    }




}
