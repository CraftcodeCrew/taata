package com.craftcodecrew.android.taata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import android.support.annotation.StyleRes;
import android.widget.ViewSwitcher;

import com.craftcodecrew.android.taata.cards.SliderAdapter;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;
import com.craftcodecrew.android.taata.informationapis.EarthquakeInsuranceController;


public class MainActivity extends AppCompatActivity {

    private final static String OUR_REST_API = "http://172.16.1.175:8089/api/categories";
    static final Integer FINE_LOCATION = 0x3;
    static final Integer HARDWARE_LOC_GPS = 0x4;
    public final static String OUR_REST_API_NACKT = "http://172.16.1.175:8089/api";

    private SliderAdapter sliderAdapter;

    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;
    private int currentPosition;
    private List<InsurableCategory> categoriesList;

    private ImageView imageView;
    private String[] categoryNames;
    private String[] categoryDescriptions;
    private int[] categoryImageId;
    private List<List<Insurable>> categorysInsurables;
    private String[] categoryCoverage = {"80% safe", "56% safe","25% safe","90% safe","72%","80%","95%"};

    private TextSwitcher categoryNameSwitcher;
    private TextSwitcher categoryDescriptionSwitcher;
    private TextSwitcher categoryCoverageSwitcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.login);
        imageView.setVisibility(View.VISIBLE);

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

            int categoriesListSize = categoriesList.size();
            categoryImageId = new int[categoriesListSize];
            categoryNames = new String[categoriesListSize];
            categoryDescriptions = new String[categoriesListSize];
            categorysInsurables = new ArrayList<List<Insurable>>(categoriesListSize);



            for (int i=0; i < categoriesListSize; i++){

                categoryNames[i] = categoriesList.get(i).getTitle();
                categoryDescriptions[i] = categoriesList.get(i).getDescription();
                //categorysInsurables.set(i,categoriesList.get(i).getInsurables());

                String drawableName = categoriesList.get(i).getImageId();
                categoryImageId[i] = getResources()
                        .getIdentifier(
                                drawableName,
                                "drawable",
                                getPackageName());


            }

            sliderAdapter = new SliderAdapter(categoryImageId, 7, new OnCardClickListener());


            return null;
        }
    }


    public void onClick(View view) {
        initRecyclerView();
        initSwitchers();
        imageView.setVisibility(View.GONE);
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

    private void initSwitchers(){
        categoryNameSwitcher = (TextSwitcher) findViewById(R.id.category_title);
        categoryNameSwitcher.setFactory(new TextViewFactory(R.style.categoryTitleStyle,false));
        categoryNameSwitcher.setCurrentText(categoryNames[0]);

        categoryDescriptionSwitcher = (TextSwitcher) findViewById(R.id.category_description);
        categoryDescriptionSwitcher.setFactory(new TextViewFactory(R.style.categoryDescriptionStyle,false));
        categoryDescriptionSwitcher.setCurrentText(categoryDescriptions[0]);

        categoryCoverageSwitcher = (TextSwitcher) findViewById(R.id.category_coverage);
        categoryCoverageSwitcher.setFactory(new TextViewFactory(R.style.categoryCoverageStyle,false));
        categoryCoverageSwitcher.setCurrentText(categoryCoverage[0]);
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

        categoryNameSwitcher.setInAnimation(MainActivity.this, animH[0]);
        categoryNameSwitcher.setOutAnimation(MainActivity.this, animH[1]);
        categoryNameSwitcher.setText(categoryNames[pos%categoryNames.length]);


        categoryDescriptionSwitcher.setInAnimation(MainActivity.this, animH[0]);
        categoryDescriptionSwitcher.setOutAnimation(MainActivity.this, animH[1]);
        categoryDescriptionSwitcher.setText(categoryDescriptions[pos%categoryDescriptions.length]);

        categoryCoverageSwitcher.setInAnimation(MainActivity.this, animH[0]);
        categoryCoverageSwitcher.setOutAnimation(MainActivity.this, animH[1]);
        categoryCoverageSwitcher.setText(categoryCoverage[pos%categoryCoverage.length]);

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


    // everything for the switchers
    private class TextViewFactory implements  ViewSwitcher.ViewFactory {

        @StyleRes final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(MainActivity.this);

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(MainActivity.this, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }
}
