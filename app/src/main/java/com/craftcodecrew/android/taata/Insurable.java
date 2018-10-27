package com.craftcodecrew.android.taata;

public class Insurable {

    private int id;
    private String title;
    private String description;
    private double probability;
    private int insuranceId;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public Insurable(int id, String title, String description, double probability, int insuranceId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.probability = probability;
        this.insuranceId = insuranceId;
    }



}
