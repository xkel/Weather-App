package com.example.multi.weatherapp;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

/**
 * Created by multi on 10/2/2017.
 */

public class DownloadClass extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... urls) {
        try {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            url = new URL(urls[0]);

            //Create a URL connection
            urlConnection = (HttpURLConnection) url.openConnection();

            //Create input stream from URL HTTP Connection
            InputStream in = urlConnection.getInputStream();
            //Read input stream
            InputStreamReader reader = new InputStreamReader(in);

            //We have a reader to take the data from the input stream
            int data = reader.read();

            //When data equals -1, the stream is finished. This runs while we still have data
            while(data != -1){
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            //Returned result that JSON Data is stored in
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        //Object created to parse through the JSON
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            double temperature = Double.parseDouble(weatherData.getString("temp"));

            //Changes the temperature from Kelvin to Fahreinheit
            int temperatureInteger = (int) (temperature*1.8-459.67);

            String placeName = jsonObject.getString("name");

            MainActivity.temperatureTextView.setText(String.valueOf(temperatureInteger));

            MainActivity.placeTextView.setText(placeName);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
