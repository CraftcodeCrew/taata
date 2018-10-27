package com.craftcodecrew.android.taata;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.craftcodecrew.android.taata.cards.SliderAdapter;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.List;
import com.craftcodecrew.android.taata.informationapis.EarthquakeInsuranceController;


public class MainActivity extends AppCompatActivity {

    private final static String OUR_REST_API = "http://172.16.1.175:8080/api/categories";
    static final Integer FINE_LOCATION = 0x3;
    static final Integer HARDWARE_LOC_GPS = 0x4;
    public final static String OUR_REST_API_NACKT = "http://172.16.1.175:8080/api";

    private SliderAdapter sliderAdapter;

    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;
    private int currentPosition;
    private List<InsurableCategory> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to not make a HttpRequest in Main Thread
        MiniAsyncTask task = new MiniAsyncTask();
        task.execute();
    }


            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    EarthquakeInsuranceController earthquakeInsuranceController =
                            new EarthquakeInsuranceController(location.getLatitude(), location.getLongitude());
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };




    private class MiniAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            categoriesList = QueryUtils.fetchInsurableCategoriesData(OUR_REST_API);

            return null;
        }
    }


    public void onClick(View view) {
        int categoriesListSize = categoriesList.size();
        int[] pics = new int[categoriesListSize];
        for (int i=0; i < categoriesListSize; i++){
            String drawableName = categoriesList.get(i).getImageId();
            pics[i] = getResources()
                    .getIdentifier(
                            drawableName,
                            "drawable",
                            getPackageName());
        }

        sliderAdapter = new SliderAdapter(pics, 20, new OnCardClickListener());

        initRecyclerView();
    }


    // Register the listener with the Location Manager to receive location updates

    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
            return false;
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean ask() {
        boolean answer = false;
        if (askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION)) {
            if(askForPermission(Manifest.permission.LOCATION_HARDWARE, HARDWARE_LOC_GPS)){
                answer = true;
            }
        } else {
            answer = false;
        }
        return answer;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }





    //// everything with the card-carousel!

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.carousel);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }

        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        currentPosition = pos;
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm =  (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }
        }
    }


}
