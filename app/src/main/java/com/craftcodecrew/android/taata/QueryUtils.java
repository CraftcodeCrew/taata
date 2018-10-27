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
/*

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
        ArrayList<Insurable> insurables = new ArrayList<>();

        try {

            JSONObject jsonRootObject = new JSONObject(json);
            JSONObject jsonResponse = jsonRootObject.optJSONObject("_embedded");
            JSONArray jsonArray = jsonResponse.optJSONArray("categoryList");

            for (int i = 0; i < jsonArray.length(); i++) {

                //// I receive one big JSON with all categories and their insurables and their insurances
                // First all the category data:
                int categoryId, imageId;
                String categoryTitle, categoryDescription;

                // Next all the insurables of the categories


                // Next all the insurances
                Insurance insurance;



                JSONObject JSONObject = jsonArray.optJSONObject(i);
                categoryId = JSONObject.optInt("id");
                categoryTitle = JSONObject.optString("webUrl");
                id = JSONObject.optInt("webTitle");
                imageId = JSONObject.optInt("sectionName");
                webPublicationDate = JSONObject.optString("webPublicationDate");

                JSONArray jsonTags = JSONObject.getJSONArray("tags");
                if (jsonTags.length() == 0 || jsonTags.isNull(0)){
                    contributor = "Unknown";
                } else {
                    JSONObject jsonContributor = jsonTags.optJSONObject(0);
                    contributor = jsonContributor.optString("webTitle");
                }

                InsurableCategory insurableCategory = new insurableCategory();
                articles.add(article);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        return articles;
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
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
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



*/
}