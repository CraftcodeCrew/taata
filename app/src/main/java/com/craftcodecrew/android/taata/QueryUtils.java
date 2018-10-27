package com.craftcodecrew.android.taata;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    // InsurableCategories
    public static List<InsurableCategory> fetchInsurableCategoriesData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractInsurableCategories(jsonResponse);
    }


    private static ArrayList<InsurableCategory> extractInsurableCategories(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        ArrayList<InsurableCategory> insurableCategories = new ArrayList<>();

        try {

            JSONObject jsonRootObject = new JSONObject(json);
            JSONObject jsonResponse = jsonRootObject.optJSONObject("_embedded");
            JSONArray jsonArray = jsonResponse.optJSONArray("categoryList");

            for (int i = 0; i < jsonArray.length(); i++) {

                //// I receive one big JSON with all categories and their insurables and their insurances
                // category
                int categoryId;

                String categoryImageId, categoryTitle, categoryDescription;
                ArrayList<Insurable> insurables = new ArrayList<>();


                JSONObject JSONObject = jsonArray.optJSONObject(i);
                categoryId = JSONObject.optInt("id");
                categoryTitle = JSONObject.optString("title");
                categoryDescription = JSONObject.optString("description");
                categoryImageId = JSONObject.optString("imageId");

                JSONArray jsonInsurables = JSONObject.getJSONArray("insurables");

                if (jsonInsurables.length() == 0 || jsonInsurables.isNull(0)){
                    for (int j=0; i< jsonInsurables.length();i++){

                        // insurable
                        int insurableId;
                        String insurableImageId, insurableTitle, insurableDescription;
                        double probability;
                        boolean insured;

                        JSONObject JSONObjectSecond = jsonInsurables.optJSONObject(j);
                        insurableId = JSONObjectSecond.optInt("id");
                        insurableImageId = JSONObjectSecond.optString("imageId");
                        insurableDescription = JSONObjectSecond.optString("description");
                        insurableTitle = JSONObjectSecond.optString("title");
                        probability = JSONObjectSecond.optDouble("probability");
                        insured = JSONObjectSecond.optBoolean("insured");

                        // stuff for insurance
                        int insuranceId;
                        String insuranceTitle;
                        double insurancePricePerMonth;

                        JSONArray jsonInsurance = JSONObjectSecond.getJSONArray("insurance");
                        JSONObject JSONObjectInsurance = jsonInsurance.optJSONObject(0);
                        insuranceId = JSONObjectInsurance.optInt("id");
                        insuranceTitle = JSONObjectInsurance.optString("name");
                        insurancePricePerMonth = JSONObjectInsurance.optDouble("pricePerMonth");

                        // wrap up
                        Insurance insurance = new Insurance(insuranceId, insuranceTitle, insurancePricePerMonth);
                        Insurable insurable = new Insurable(insurableId,insurableTitle,insurableImageId,insurableDescription,probability, insured, insurance );

                        insurables.add(insurable);
                        Log.i("insurable detected", insurableTitle);
                    }

                } else {
                    Log.e("Darf nicht", "koksnutte");
                }
                Log.i("category detected", categoryTitle);

                InsurableCategory insurableCategory = new InsurableCategory(categoryId,categoryTitle,categoryDescription,categoryImageId,insurables);
                insurableCategories.add(insurableCategory);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Master Koksnutten JSON results", e);
        }

        return insurableCategories;
    }



    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /*
    public static String makeHttpRequest(URL url, String method, List<NameValuePair> headerParams, JSONObject content) {

    }
    */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Only retrieving Koksnutten-JSONs from Sebastian.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}