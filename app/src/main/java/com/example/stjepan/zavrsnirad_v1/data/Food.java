package com.example.stjepan.zavrsnirad_v1.data;

/**
 * Created by Stjepan on 15.7.2017..
 */

public class Food {
    private int id;
    private String name;
    private double fat;

    public Food (String name, double fat){
        this.name = name;
        this.fat = fat;
    }

    public Food(int id, String name, double fat){
        this.id = id;
        this.name = name;
        this.fat = fat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }
}
