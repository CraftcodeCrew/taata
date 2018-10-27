package com.craftcodecrew.android.taata.informationapis;

import android.location.Location;

import org.json.JSONException;

import java.io.IOException;

class EartquakeInsurable {
    private String title = "EarthquakeInsureable";
    private String description = "Is an insurance for earthquakes relevant for you?";
            // "We offer you a very very great offer for earthquake insurance. It is the very best insurance in this beautiful world.";
    private double probability;

    public EartquakeInsurable (Location location){
        probability = setProbability(location);
    }

    private double setProbability(Location location){
        EarthquakeApi api = new EarthquakeApi(location.getLatitude(), location.getLongitude());
        double earthquakesAtLocation = 0;
        try {
            earthquakesAtLocation = api.getHistoricalEearthquakes();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // In 118 Jahren wurden x Erdbeben gemeseen -> Wahrscheinlichkeit pro Jahr:
        double probabilityOfEarthquakePerYear = (earthquakesAtLocation / 118) * 100;
        return probabilityOfEarthquakePerYear;
    }

    public void safeEarthquakeInsurable (){
        
    }
}
