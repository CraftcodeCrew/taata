package com.craftcodecrew.android.taata;

import java.util.ArrayList;

public class InsurableCategory {

    private int id;
    private String title;
    private String description;
    private String imageId;
    private ArrayList<Insurable> insurables;

    public InsurableCategory(int id, String title, String description, String imageId, ArrayList<Insurable> insurables) {
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

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setInsurables(ArrayList<Insurable> insurables) {
        this.insurables = insurables;
    }

    public String getImageId() {
        return imageId;
    }

    public ArrayList<Insurable> getInsurables() {
        return insurables;
    }


}
