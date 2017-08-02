package com.example.stjepan.zavrsnirad_v1.data;

/**
 * Created by Stjepan on 15.7.2017..
 */

public class Food {
    private int id;
    private String name;
    private double fat, omega3, omega6, proteins, carbo, energy, gram;
    boolean selected = false;

    public Food (String name, double fat, double omega3, double omega6, double proteins, double carbo, double energy){
        this.name = name;
        this.fat = fat;
        this.omega3 = omega3;
        this.omega6 = omega6;
        this.proteins = proteins;
        this.carbo = carbo;
        this.energy = energy;
    }

    public Food (int id, String name, double fat, double omega3, double omega6, double proteins, double carbo, double energy){
        this.id = id;
        this.name = name;
        this.fat = fat;
        this.omega3 = omega3;
        this.omega6 = omega6;
        this.proteins = proteins;
        this.carbo = carbo;
        this.energy = energy;
    }

    public Food (int id,  double gram){
        this.id = id;
        this.gram = gram;
    }

    public Food (int id, String name, double fat, double omega3, double omega6, double proteins, double carbo, double energy, double gram){
        this.id = id;
        this.name = name;
        this.fat = fat;
        this.omega3 = omega3;
        this.omega6 = omega6;
        this.proteins = proteins;
        this.carbo = carbo;
        this.energy = energy;
        this.gram = gram;
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

    public double getOmega3() {
        return omega3;
    }

    public void setOmega3(double omega3) {
        this.omega3 = omega3;
    }

    public double getOmega6() {
        return omega6;
    }

    public void setOmega6(double omega6) {
        this.omega6 = omega6;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbo() {
        return carbo;
    }

    public void setCarbo(double carbo) {
        this.carbo = carbo;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getGram() {
        return gram;
    }

    public void setGram(double gram) {
        this.gram = gram;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }
}
