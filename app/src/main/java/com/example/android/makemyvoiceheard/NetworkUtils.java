package com.example.android.makemyvoiceheard;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String CIVICS_URL = "https://civicinfo.googleapis.com/civicinfo/v2/representatives?address=6703%20Canterbury%20Dr&includeOffices=true&levels=country&roles=legislatorUpperBody&roles=legislatorLowerBody&key=AIzaSyD_7MEtxj3fSEG6eADD5W2tReHTfr6buY4";
    final static String CIVICS_URL_PART2 = "--header 'Accept: application/json' --compressed";
    final static String HTTP_STRING  = "https://civicinfo.googleapis.com/civicinfo/v2/representatives?address=6703%20Canterbury%20Dr%2C%20Frisco%2C%20TX%2075035&includeOffices=true&levels=country&roles=legislatorUpperBody&roles=legislatorLowerBody&key=AIzaSyD_7MEtxj3fSEG6eADD5W2tReHTfr6buY4";
    final static String API_BASE_STRING  = "https://civicinfo.googleapis.com/civicinfo/v2/representatives?address=";
    final static String API_PARAMETERS_STRING  = "&includeOffices=true&levels=country&roles=legislatorUpperBody&roles=legislatorLowerBody&key=AIzaSyD_7MEtxj3fSEG6eADD5W2tReHTfr6buY4";
    final static String ACCEPT = " \n\n  Accept: application/json";
    private static boolean networkConnected;

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(String address) throws IOException {
        Log.d("WWD", " ---------------- getResponseFromHttpUrl address is " + address);
        //String urlString = CIVICS_URL + CIVICS_URL_PART2;
        String urlString = API_BASE_STRING + address + API_PARAMETERS_STRING;
        //urlString += ACCEPT;
        Log.d("WWD", "the urlstring is " + urlString);
        Log.d("WWD", " the original HTTP_STRING is " + HTTP_STRING);

        Uri builtUri = Uri.parse(urlString).buildUpon()
                .build();

        Log.d("WWD", "built uri " + builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d("WWD", "url build failed");
            e.printStackTrace();
        }
        Log.d("WWD", "built url " + url.toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        Log.d("WWD", "in getResponseFromHttpUrl url is " + url);
        try {
            Log.d("WWD", "lets try urlConnection.getInputStream");
            InputStream in = urlConnection.getInputStream();
            Log.d("WWD", "after getInputStream");
            if (in == null) {
                networkConnected = false;
                Log.d("WWD", "network connection failed");
            }
            else {
                networkConnected = true;
                Log.d("WWD", "network connection worked");
            }

          /*   Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            Log.d("WWD", "hasInput is " + hasInput);
            if (hasInput) {
                Log.d("WWD", "read data from Civics API");
                return scanner.next();
            } else {
                Log.d("WWD", "no data read from Civics API ");
                return null;
            } */
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                Log.d("WWD", "read " + line);
            }
            return sb.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean getNetworkConnected() {
        return networkConnected;
    }
}
