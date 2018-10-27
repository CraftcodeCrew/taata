package com.craftcodecrew.android.taata;

public class Insurance {

    private int id;
    private String name;
    private double pricePerMonth;

    public Insurance(int id, String name, double pricePerMonth){
        this.id = id;
        this.name = name;
        this.pricePerMonth = pricePerMonth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
