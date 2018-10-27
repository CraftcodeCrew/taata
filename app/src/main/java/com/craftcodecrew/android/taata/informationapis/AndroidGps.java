package com.craftcodecrew.android.taata.informationapis;

import android.location.Location;
import android.util.Log;


public class AndroidGps {
    Location location;

    public AndroidGps (Location location){
        this.location = location;
        Log.i("darf schon:", location.getLongitude() + " " + location.getAltitude() + " Koksnutten hier");
    }

    public void sendNewEeathquakeInsurable(){
        EartquakeInsurable eartquakeEnsurable = new EartquakeInsurable(this.location);
    }

}
