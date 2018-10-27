package com.craftcodecrew.android.taata;

import java.util.ArrayList;

public class InsurableCategory {

    private int id;
    private String title;
    private String description;
    private int imageId;
    private ArrayList<Insurable> insurables;

    public InsurableCategory(int id, String title, String description, int imageId, ArrayList<Insurable> insurables) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
        this.insurables = insurables;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setInsurables(ArrayList<Insurable> insurables) {
        this.insurables = insurables;
    }

    public int getImageId() {
        return imageId;
    }

    public ArrayList<Insurable> getInsurables() {
        return insurables;
    }


}
