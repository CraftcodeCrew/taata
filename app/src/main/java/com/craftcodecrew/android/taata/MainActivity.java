package com.craftcodecrew.android.taata;

import android.os.AsyncTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.craftcodecrew.android.taata.informationapis.AndroidGps;
import com.craftcodecrew.android.taata.informationapis.EarthquakeApi;


public class MainActivity extends AppCompatActivity {

    private final static String OUR_REST_API = "http://172.16.1.175:8080/api/categories";

    public final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MiniAsyncTask task = new MiniAsyncTask();
        task.execute();
    }


    private class MiniAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            QueryUtils.fetchInsurableCategoriesData(OUR_REST_API);

            return null;
        }
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            AndroidGps androidGps = new AndroidGps(location);
            androidGps.sendNewEeathquakeIsrueable();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    // Register the listener with the Location Manager to receive location updates
    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
}
