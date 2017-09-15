package com.example.stjepan.zavrsnirad_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Stjepan on 26.8.2017..
 */

public class GramHelp implements Serializable {
    private int id;
    private int id_food;
    private int id_menu;
    private double gram;
    private double gram_total;
    private String date;
    private String foodTime;


    public GramHelp(int id, int id_food, int id_menu, double gram, String date) {
        this.id = id;
        this.id_food = id_food;
        this.id_menu = id_menu;
        this.gram = gram;
        this.date = date;
    }

    public GramHelp(int id, int id_food, int id_menu, double gram) {
        this.id = id;
        this.id_food = id_food;
        this.id_menu = id_menu;
        this.gram = gram;
    }

    public GramHelp(){}

    public GramHelp (String foodTime, double gram_total){
        this.foodTime = foodTime;
        this.gram_total = gram_total;
    }

    public GramHelp(int id, int id_food, int id_menu, double gram, double gram_total, String date) {
        this.id = id;
        this.id_food = id_food;
        this.id_menu = id_menu;
        this.gram = gram;
        this.gram_total = gram_total;
        this.date = date;
    }

    public double getGram_total() {
        return gram_total;
    }

    public void setGram_total(double gram_total) {
        this.gram_total = gram_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public double getGram() {
        return gram;
    }

    public void setGram(double gram) {
        this.gram = gram;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}


