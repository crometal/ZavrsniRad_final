package com.example.stjepan.zavrsnirad_v1.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.stjepan.zavrsnirad_v1.data.FoodContract.FoodEntry;
import com.example.stjepan.zavrsnirad_v1.models.GramHelp;
import com.example.stjepan.zavrsnirad_v1.models.History;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.autoUrlDetect;
import static android.R.attr.id;
import static android.R.attr.negativeButtonText;
import static android.R.attr.thickness;
import static android.R.attr.value;

public class FoodDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FoodDbHelper.class.getSimpleName();
    private static FoodDbHelper INSTANCE;
    private static final String DATABASE_NAME = "foodlist.db";
    private static final int DATABASE_VERSION = 20;

    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "Created");
    }

    public static FoodDbHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FoodDbHelper(context);
        }
        return INSTANCE;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FOOD_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME + " ("
                + FoodEntry._ID_FOODS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.COLUMN_FOOD_NAME + " TEXT NOT NULL, "
                + FoodEntry.COLUMN_FAT_TOTAL + " DECIMAL(7,3) NOT NULL, "
                + FoodEntry.COLUMN_OMEGA3 + " DECIMAL(7,2), "
                + FoodEntry.COLUMN_OMEGA6 + " DECIMAL(7,2), "
                + FoodEntry.COLUMN_PROTEINS + " DECIMAL(7,2), "
                + FoodEntry.COLUMN_CARBOHYDRATES + " DECIMAL(7,3), "
                + FoodEntry.COLUMN_ENERGY + " DECIMAL(7,3)" + ")";
        db.execSQL(SQL_CREATE_FOOD_TABLE);

        String SQL_CREATE_MENU_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME_MENU + " ("
                + FoodEntry._ID_MENU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.FOOD_TIME + " VARCHAR " + ");";
        db.execSQL(SQL_CREATE_MENU_TABLE);

        String SQL_CREATE_HELPER_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME_HELPER + " ("
                + FoodEntry._ID_HELPER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.FOREIGN_FOODS_ID + " INTEGER, "
                + FoodEntry.FOREIGN_MENU_ID + " INTEGER, "
                + FoodEntry.COLUMN_GRAM + " DECIMAL(7,3), "
                + FoodEntry.COLUMN_GRAM_TOTAL + " DECIMAL(7,3), "
                + FoodEntry.COLUMN_DATE + " DATE, "
                + " FOREIGN KEY (" + FoodEntry.FOREIGN_FOODS_ID + ") REFERENCES "
                + FoodEntry.TABLE_NAME + "(" + FoodEntry._ID_FOODS + "),"
                + " FOREIGN KEY (" + FoodEntry.FOREIGN_MENU_ID + ") REFERENCES "
                + FoodEntry.TABLE_NAME_MENU + "(" + FoodEntry._ID_MENU + ")"
                + ")";
        db.execSQL(SQL_CREATE_HELPER_TABLE);

        String SQL_CREATE_MENU_TOTAL_TABLE = " CREATE TABLE " + FoodEntry.TABLE_NAME_MENU_TOTAL + " ("
                + FoodEntry._ID_MENU_TOTAL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.COLUMN_BREAKFAST + " DECIMAL(7,3), "
                + FoodEntry.COLUMN_LUNCH + " DECIMAL(7,3), "
                + FoodEntry.COLUMN_DINNER + " DECIMAL(7,3) " + ");";
        db.execSQL(SQL_CREATE_MENU_TOTAL_TABLE);

        String SQL = "INSERT INTO " + FoodEntry.TABLE_NAME_MENU + " ( "
                + FoodEntry.FOOD_TIME + ") VALUES " + "('"
                + FoodEntry.COLUMN_BREAKFAST + "'), ( '"
                + FoodEntry.COLUMN_LUNCH + "'), ( '"
                + FoodEntry.COLUMN_DINNER + "');";
        db.execSQL(SQL);


        db.execSQL("INSERT INTO " + FoodEntry.TABLE_NAME + " (" +
                FoodEntry.COLUMN_FOOD_NAME + " , " +
                FoodEntry.COLUMN_FAT_TOTAL + " , " +
                FoodEntry.COLUMN_OMEGA3 + " , " +
                FoodEntry.COLUMN_OMEGA6 + " , " +
                FoodEntry.COLUMN_PROTEINS + " , " +
                FoodEntry.COLUMN_CARBOHYDRATES + " , " +
                FoodEntry.COLUMN_ENERGY + " ) " +
                "VALUES ('Mlijeko 2.8% m.m', 2.8, 0.1, 0.0, 3.3, 4.7, 57), ('kj', 1,2,3,4,5,6);");

   /*     db.execSQL("INSERT INTO " +FoodEntry.TABLE_NAME + " SELECT '1' AS " +FoodEntry._ID_FOODS + ", " +
                "'Burek' AS " + FoodEntry.COLUMN_FOOD_NAME + ", " +
                " '20' AS " + FoodEntry.COLUMN_FAT_TOTAL + ", " +
                " '30' AS " +FoodEntry.COLUMN_OMEGA3 + ", " +
                " '30' AS " +FoodEntry.COLUMN_OMEGA6 + ", " +
                " '30' AS " +FoodEntry.COLUMN_PROTEINS + ", " +
                " '30' AS " +FoodEntry.COLUMN_CARBOHYDRATES + ", " +
                " '30' AS " +FoodEntry.COLUMN_ENERGY +
                " UNION ALL SELECT '2' 'Burek2', '30', '40', '50', '60', '70', '80'"
        );*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + FoodEntry.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }


    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FoodEntry.TABLE_NAME, FoodEntry._ID_FOODS + "	= ?",
                new String[]{String.valueOf(id)});
    }


    public void updateFood(Food food) {
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, food.getName());
        values.put(FoodEntry.COLUMN_FAT_TOTAL, food.getFat());
        values.put(FoodEntry.COLUMN_OMEGA3, food.getOmega3());
        values.put(FoodEntry.COLUMN_OMEGA6, food.getOmega6());
        values.put(FoodEntry.COLUMN_PROTEINS, food.getProteins());
        values.put(FoodEntry.COLUMN_CARBOHYDRATES, food.getCarbo());
        values.put(FoodEntry.COLUMN_ENERGY, food.getEnergy());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(FoodEntry.TABLE_NAME, values, FoodEntry._ID_FOODS + "	= ?",
                new String[]{String.valueOf(food.getId())});
    }


    public String multiplyFat(List<Food> listFood, List<GramHelp> listGram) {
        double totalFat = 0;
        if (listFood.size() == 0 || listGram.size() == 0) {
            return "0";
        }
        for (Food food : listFood) {
            for (GramHelp gramHelp : listGram) {
                if (food.getId() == gramHelp.getId_food()) {
                    totalFat += food.getFat() * gramHelp.getGram();
                    break;
                }
            }
        }
        return String.valueOf(totalFat);
    }

    public String multiply(List<Food> listFood, List<GramHelp> listGram) {
        double totalFat = 0;
        if (listFood.size() == 0 || listGram.size() == 0) {
            return "0";
        }
        for (Food food : listFood) {
            for (GramHelp gramHelp : listGram) {
                if (food.getId() == gramHelp.getId_food()) {
                    gramHelp.setGram_total(food.getFat() * gramHelp.getGram());
                    addGram(gramHelp);
                    break;
                }
            }
        }
        return String.valueOf(totalFat);
    }

    public int listMenuType(int menu) {
        String sql = "";
        switch (menu) {
            case 1:
                sql = "SELECT " + FoodEntry._ID_MENU + " FROM " + FoodEntry.TABLE_NAME_MENU + " WHERE "
                        + FoodEntry.FOOD_TIME + " = 'Doručak' ;";
                break;
            case 2:
                sql = "SELECT " + FoodEntry._ID_MENU + " FROM " + FoodEntry.TABLE_NAME_MENU + " WHERE "
                        + FoodEntry.FOOD_TIME + " = 'Ručak' ;";
                break;
            case 3:
                sql = "SELECT " + FoodEntry._ID_MENU + " FROM " + FoodEntry.TABLE_NAME_MENU + " WHERE "
                        + FoodEntry.FOOD_TIME + " = 'Večera' ;";
                break;
        }
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int id_menu = 0;
        if (cursor.moveToFirst()) {
            id_menu = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return id_menu;
    }

    public String listMenuHistory(int menu) {
        String sql = "";

         sql = "SELECT " + FoodEntry.FOOD_TIME + " FROM " + FoodEntry.TABLE_NAME_MENU + " WHERE "
                        + FoodEntry._ID_MENU + " = " +menu;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        String menuName="";
        if (cursor.moveToFirst()) {
            menuName = cursor.getString(0);
        }
        cursor.close();
        return menuName;
    }

    /*public List<GramHelp> listHistory() {
        String sql = "SELECT * FROM " + FoodEntry.TABLE_NAME_HELPER + " ORDER BY " + FoodEntry.COLUMN_DATE + " ASC ";
        SQLiteDatabase db = this.getReadableDatabase();
        List<GramHelp> listDate = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = (cursor.getInt(0));
                int id_food = (cursor.getInt(1));
                int id_menu = (cursor.getInt(2));
                double gram = cursor.getDouble(3);
                double gram_total = cursor.getDouble(4);
                String date = new String(cursor.getString(5));
                listDate.add(new GramHelp(id, id_food, id_menu, gram, gram_total, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listDate;
    }*/
    public List<History> listHistory(){
        String sql = "SELECT SUM (" +FoodEntry.COLUMN_GRAM_TOTAL + ") ," +FoodEntry.FOREIGN_MENU_ID + ", " +FoodEntry.COLUMN_DATE +
                " FROM " +FoodEntry.TABLE_NAME_HELPER + " GROUP BY " +FoodEntry.COLUMN_DATE + ", " +FoodEntry.FOREIGN_MENU_ID +
                " ORDER BY " +FoodEntry.COLUMN_DATE + " DESC ";
        SQLiteDatabase db = getReadableDatabase();
        List<History> listHistory = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                double totalGram = cursor.getDouble(0);
                int idMenu = cursor.getInt(1);
                String date = cursor.getString(2);
                listHistory.add(new History(idMenu, totalGram, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listHistory;
    }

   /* public List<GramHelp> listHistoryInfo(){
        String sql = "SELECT " +FoodEntry._ID_MENU + ", " +FoodEntry.FOOD_TIME + ", " +FoodEntry.COLUMN_DATE + " FROM "
                +FoodEntry.TABLE_NAME_MENU + ", " +FoodEntry.TABLE_NAME_HELPER + " WHERE "
                +FoodEntry._ID_MENU + " = " +FoodEntry.FOOD_TIME;
        //String sql = "SELECT " +FoodEntry.FOOD_TIME + ", " +FoodEntry.COLUMN_DATE + " FROM " +FoodEntry.TABLE_NAME_HELPER;
        SQLiteDatabase db = this.getReadableDatabase();
        List<GramHelp> listDate = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                String foodTime = cursor.getString(0);
                double totalFat = cursor.getDouble(1);
                listDate.add(new GramHelp(foodTime, totalFat));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listDate;
    }*/

    public List<Food> listFood() {
        String sql = "SELECT * FROM " + FoodEntry.TABLE_NAME + " ORDER BY " + FoodEntry.COLUMN_FOOD_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Food> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                double fat = Double.parseDouble(cursor.getString(2));
                double omega3 = Double.parseDouble(cursor.getString(3));
                double omega6 = Double.parseDouble(cursor.getString(4));
                double proteins = Double.parseDouble(cursor.getString(5));
                double carbo = Double.parseDouble(cursor.getString(6));
                double energy = Double.parseDouble(cursor.getString(7));
                storeProducts.add(new Food(id, name, fat, omega3, omega6, proteins, carbo, energy));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public List<Food> listSelectedFood(List<GramHelp> lista) {
        List<Food> listFood = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        for (GramHelp gramHelp : lista) {
            String sql = " SELECT * FROM " + FoodEntry.TABLE_NAME + " WHERE " + FoodEntry._ID_FOODS + " = "
                    + gramHelp.getId_food() + ";";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                double fat = Double.parseDouble(cursor.getString(2));
                double omega3 = Double.parseDouble(cursor.getString(3));
                double omega6 = Double.parseDouble(cursor.getString(4));
                double proteins = Double.parseDouble(cursor.getString(5));
                double carbo = Double.parseDouble(cursor.getString(6));
                double energy = Double.parseDouble(cursor.getString(7));
                listFood.add(new Food(id, name, fat, omega3, omega6, proteins, carbo, energy));
            }
        }
        return listFood;
    }

    public List<Food> listTotalFood(List<GramHelp> idFoodList) {
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        for (GramHelp gramHelp : idFoodList) {
            String sql = "SELECT * FROM " + FoodEntry.TABLE_NAME + " WHERE " + FoodEntry._ID_FOODS + " = " + gramHelp.getId_food() + ";";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                double fat = Double.parseDouble(cursor.getString(2));
                double omega3 = Double.parseDouble(cursor.getString(3));
                double omega6 = Double.parseDouble(cursor.getString(4));
                double proteins = Double.parseDouble(cursor.getString(5));
                double carbo = Double.parseDouble(cursor.getString(6));
                double energy = Double.parseDouble(cursor.getString(7));
                foodList.add(new Food(id, name, fat, omega3, omega6, proteins, carbo, energy));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return foodList;
    }

    public List<GramHelp> listGram(List<Integer> idFoodList, int var) {
        DateFormat datum = new SimpleDateFormat("yyyy-MM-dd");
        List<GramHelp> listGram = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        for (Integer integer : idFoodList) {
            String sql = "SELECT * FROM " + FoodEntry.TABLE_NAME_HELPER + " WHERE " + FoodEntry.FOREIGN_FOODS_ID + " = " + integer +
                    //" AND " + FoodEntry.COLUMN_DATE + " = " + datum.format(Calendar.getInstance().getTime()) +
                    " AND " + FoodEntry.FOREIGN_MENU_ID + " = " + var;

            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = (cursor.getInt(0));
                int id_food = (cursor.getInt(1));
                int id_menu = (cursor.getInt(2));
                double gram = cursor.getDouble(3);
                //double gram_total = cursor.getDouble(4);
                //Date date = new Date(cursor.getLong(4));

                listGram.add(new GramHelp(id, id_food, id_menu, gram));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return listGram;
    }

    public Cursor searchItem(String searchTerm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {FoodEntry._ID_FOODS, FoodEntry.COLUMN_FOOD_NAME};
        Cursor cursor = null;
        if (searchTerm != null && searchTerm.length() > 0) {
            String sql = "SELECT * FROM " + FoodEntry.TABLE_NAME + " WHERE " + FoodEntry.COLUMN_FOOD_NAME
                    + " LIKE '%" + searchTerm + "%'";
            cursor = db.rawQuery(sql, null);
            return cursor;
        }
        cursor = db.query(FoodEntry.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, food.getName());
        values.put(FoodEntry.COLUMN_FAT_TOTAL, food.getFat());
        values.put(FoodEntry.COLUMN_OMEGA3, food.getOmega3());
        values.put(FoodEntry.COLUMN_OMEGA6, food.getOmega6());
        values.put(FoodEntry.COLUMN_PROTEINS, food.getProteins());
        values.put(FoodEntry.COLUMN_CARBOHYDRATES, food.getCarbo());
        values.put(FoodEntry.COLUMN_ENERGY, food.getEnergy());
        db.insert(FoodEntry.TABLE_NAME, null, values);
    }

    public void addGram(GramHelp gramHelp) {
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_GRAM, gramHelp.getGram());
        values.put(FoodEntry.FOREIGN_FOODS_ID, gramHelp.getId_food());
        values.put(FoodEntry.FOREIGN_MENU_ID, gramHelp.getId_menu());
        values.put(FoodEntry.COLUMN_GRAM_TOTAL, gramHelp.getGram_total());
        values.put(FoodEntry.COLUMN_DATE, String.valueOf(gramHelp.getDate()));
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(FoodEntry.TABLE_NAME_HELPER, null, values);
    }


}
