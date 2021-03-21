package com.example.android.makemyvoiceheard;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
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

    private static boolean networkConnected;

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(String address) throws IOException {
        //String urlString = CIVICS_URL + CIVICS_URL_PART2;
        String urlString = CIVICS_URL;
        Uri builtUri = Uri.parse(urlString).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.d("WWD", "in getResponseFromHttpUrl url is " + url);
        try {
            Log.d("WWD", "try urlConnection.getInputStream");
            InputStream in = urlConnection.getInputStream();
            if (in == null) {
                networkConnected = false;
                Log.d("WWD", "network connection failed");
            }
            else {
                networkConnected = true;
                Log.d("WWD", "network connection worked");
            }

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            Log.d("WWD", "hasInput is " + hasInput);
            if (hasInput) {
                Log.d("WWD", "read data from Civics API");
                return scanner.next();
            } else {
                Log.d("WWD", "no data read from Civics API ");
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean getNetworkConnected() {
        return networkConnected;
    }
}
