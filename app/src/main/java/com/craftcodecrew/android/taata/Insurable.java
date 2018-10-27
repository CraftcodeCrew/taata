package com.craftcodecrew.android.taata;

public class Insurable {

    private int id;
    private String title;
    private String description;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    private int imageId;
    private double probability;
    private boolean insured;
    Insurance insurance;

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

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

    public Insurable(int id, String title, int imageId,String description, double probability, boolean isInsured, Insurance insurance){
        this.id = id;
        this.title = title;
        this.imageId = imageId;
        this.description = description;
        this.probability = probability;
        this.insurance = insurance;
        setInsured(isInsured);
    }



}
