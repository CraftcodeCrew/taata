package com.craftcodecrew.android.taata.informationapis;

public class InsurableCreate{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleDescription() {
        return titleDescription;
    }

    public void setTitleDescription(String titleDescription) {
        this.titleDescription = titleDescription;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(long insuranceId) {
        this.insuranceId = insuranceId;
    }

    private String title;
    private String titleDescription;
    private String imageId;
    private double probability;
    private long insuranceId;

    public InsurableCreate(String title, String titleDescription, String imageId, double probability, long insuranceId) {
        this.title = title;
        this.titleDescription = titleDescription;
        this.imageId = imageId;
        this.probability = probability;
        this.insuranceId = insuranceId;
    }
}