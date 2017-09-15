package com.example.stjepan.zavrsnirad_v1.models;

/**
 * Created by Stjepan on 15.9.2017..
 */

public class History {
    private int id_menu;
    private double total;
    private String date;

    public History(int id_menu, double total, String date) {
        this.id_menu = id_menu;
        this.total = total;
        this.date = date;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
