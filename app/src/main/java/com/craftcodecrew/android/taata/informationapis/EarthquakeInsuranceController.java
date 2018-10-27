package com.craftcodecrew.android.taata.informationapis;

import com.craftcodecrew.android.taata.Insurance;
import com.craftcodecrew.android.taata.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class EarthquakeInsuranceController {
    private String insurableTitle = "Earthquake Insureable";
    private String insureableDescription = "Is an insurance for earthquakes relevant for you?";

    // "We offer you a very very great offer for earthquake insurance. It is the very best insurance in this beautiful world.";
    private double probability;
    private String imageId = "earthquake";

    private String insuranceName = "Earthquake Insurance";
    private double pricePerMonth;

    public EarthquakeInsuranceController(double latitude, double longditude) {
        this.probability = calculateProbability(latitude, longditude);
        this.pricePerMonth = calculatePricePerMonth(probability);
    }


    private double calculateProbability(double latitude, double longditude) {
        EarthquakeApi api = new EarthquakeApi(latitude, longditude);
        double earthquakesAtLocation = 0;
        try {
            earthquakesAtLocation = api.getHistoricalEearthquakes();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        double eathquakepropability = 100*earthquakesAtLocation / (10+Math.abs(earthquakesAtLocation));
        return eathquakepropability;
        //return earthquakesAtLocation / (1 + Math.abs(earthquakesAtLocation));
    }


    private double calculatePricePerMonth(double probability) {
        double pricePerMonth = probability * 0.145657; // mal gerade / parabel ...
        return Math.round(pricePerMonth * 100.0) / 100.0;
    }


    public Insurance safeEarthquakeInsurance() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithType(Insurance.class);
        String jsonContent = writer.writeValueAsString(new Insurance(0, insuranceName, pricePerMonth));

        String jsonResponse = doPostRequest("/insurances", jsonContent);

        ObjectReader reader = mapper.reader(Insurance.class);
        Insurance insurance = reader.readValue(jsonResponse);
        return insurance;
    }


    public void safeEarthquakeInsurable(Insurance insurance) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithType(InsurableCreate.class);
        String jsonContent = writer.writeValueAsString(new InsurableCreate(insurableTitle, insureableDescription, imageId, probability, insurance.getId()));

        String jsonResponse = doPostRequest("/insurables", jsonContent);
    }






    private String doPostRequest(String restPath, String jsonContent) throws IOException {
        URL url = new URL(MainActivity.OUR_REST_API_NACKT + restPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();

        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
        os.writeBytes(jsonContent);

        os.flush();
        os.close();

        String jsonResponse = "";
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = conn.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } else {
           // Penis
        }

        System.out.println(jsonResponse);
        conn.disconnect();

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




    public static void main(String[] args) throws IOException {
        EarthquakeInsuranceController controller = new EarthquakeInsuranceController(
                47.590615, 19.31452);
        Insurance insurance = controller.safeEarthquakeInsurance();
        controller.safeEarthquakeInsurable(insurance);
    }
}
