package com.craftcodecrew.android.taata.informationapis;

import android.location.Location;


public class AndroidGps {
    Location location;

    public AndroidGps (Location location){
        this.location = location;
    }

    public void sendNewEeathquakeIsrueable(){
        EartquakeInsurable eartquakeEnsurable = new EartquakeInsurable(this.location);
    }

}
