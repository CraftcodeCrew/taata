package com.craftcodecrew.android.taata.informationapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

public class EarthquakeApi {
    private String api;
    private static double maxradiuskm = 30.0;
    private static String starttime = "1900-01-01";
    private static String endtime = "2018-10-27";

    public EarthquakeApi(double latitude, double longitude) {

        this.api = String.format(Locale.US, "https://earthquake.usgs.gov/fdsnws/event/1/count?format=geojson&" +
                "latitude=%f&" +
                "longitude=%f&" +
                "maxradiuskm=%f&" +
                "starttime=%s&" +
                "endtime=%s"
                , latitude, longitude, maxradiuskm, starttime, endtime);
    }


    public int getHistoricalEearthquakes() throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(this.api);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader(JsonModel.class);
        JsonModel jsonEarthquakeModel = reader.readValue(jsonString);

        return jsonEarthquakeModel.getCount();
    }

    public static void main(String [] args) throws IOException, JSONException {
        double latitude = 48.78232;
        double longitude = 9.17702;

        EarthquakeApi ea = new EarthquakeApi(latitude, longitude);
        int earthquakes = ea.getHistoricalEearthquakes();
        System.out.println("" + earthquakes);

    }




}
