package com.craftcodecrew.android.taata;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final static String OUR_REST_API = "http://172.16.1.175:8080/api/categories";


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
}
