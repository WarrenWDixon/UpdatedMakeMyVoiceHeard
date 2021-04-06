/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.makemyvoiceheard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView senator1NameTV;
    private TextView senator2NameTV;
    private TextView representativeNameTV;
    private ImageView senator1Image;
    private ImageView senator2Image;
    private ImageView representativeImage;

    public static final Integer SENATOR_ONE = 1;
    public static final Integer SENATOR_TWO = 2;
    public static final Integer REPRESENTATIVE = 3;
    public static final String IMAGE_SELECTION = "SELECTION";

    // GPSTracker class
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        String mCallPermission = Manifest.permission.CALL_PHONE;
        final int REQUEST_CODE_PERMISSION = 2;
        String encodeAddress;
        String encodedAddress = "";

        try {
            if ((ContextCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) ||  (ContextCompat.checkSelfPermission(this, mCallPermission)
                    != PackageManager.PERMISSION_GRANTED)) {
                Log.d("WWD", "requesting permission");
                requestPermissions(new String[]{mPermission,mCallPermission}, REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){
            Log.d("WWD", "canGetLocation returned true");
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d("WWD", " ---------- latitude is " + latitude);
            Log.d("WWD", " ---------- longitude is " + longitude);
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);
                if (listAddress != null && listAddress.size() > 0) {
                    String address = "";
                    address += listAddress.get(0).getAddressLine(0);
                    Log.d("WWD", " -------------  address line is " + address);
                    encodeAddress = URLEncoder.encode(address, "UTF-8");
                    Log.d("WWD", "-------------- the the encoded address is " + encodeAddress);
                    encodedAddress = encodeAddress.replace("+", "%20");
                    Log.d("WWD", " -------------- the encoded address afer replacment is " + encodedAddress);

                   // URL url = new URL(address);
                    //Log.d("WWD", "-------------- first url is " + url.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        new CivicQueryTask().execute(encodedAddress);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSenator1(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(IMAGE_SELECTION, SENATOR_ONE);
        startActivity(intent);
    }

    public void onClickSenator2(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(IMAGE_SELECTION, SENATOR_TWO);
        startActivity(intent);
    }

    public void onClickRepresentative(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(IMAGE_SELECTION, REPRESENTATIVE);
        startActivity(intent);
    }

    public class CivicQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String address = params[0];
            String civicResults = null;
          //  Log.d("WWD", "in doInBackground");
            try {
            //    Log.d("WWD", "call network utils");
                civicResults = NetworkUtils.getResponseFromHttpUrl(address);
                //Log.d("WWD", "civicResults):" + civicResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return civicResults;
        }


        @Override
        protected void onPostExecute(String civicSearchResults) {
            String senator1Name;
            String senator2Name;
            String representativeName;
          // Log.d("WWD", "in onPostExecute");
            if (NetworkUtils.getNetworkConnected()) {
                if (civicSearchResults != null && !civicSearchResults.equals("")) {
                    // ---------------------------------------------------------------------
                    // first set the names to the text views
                    // ---------------------------------------------------------------------
                    JsonUtil.parseCivicsJson(civicSearchResults);

                    senator1NameTV = (TextView) findViewById(R.id.senator_1_name);
                    senator1Name = JsonUtil.getSenator1Name();
                 //   Log.d("WWD", "senator1Name is " + senator1Name);
                 //   if (senator1Name.length() > 0)
                //        Log.d("WWD", "senator 1 name is " + senator1Name);
                //    else
                //        Log.d("WWD", "senator 1 name not defined");
                    senator1NameTV.setText(JsonUtil.getSenator1Name());

                    senator2NameTV = (TextView) findViewById(R.id.senator_2_name);
                    senator2Name = JsonUtil.getSenator2Name();
                 //   if (senator2Name.length() > 0)
                 //       Log.d("WWD", "senator 2 name is " + senator2Name);
                 //   else
                 //       Log.d("WWD", "senator 2 name not defined");
                    senator2NameTV.setText(JsonUtil.getSenator2Name());

                    representativeNameTV = (TextView) findViewById(R.id.representative_name);
                    representativeName = JsonUtil.getRepresentativeName();
                //    if (representativeName.length() > 0)
                 //       Log.d("WWD", "representative name is " + representativeName);
                //    else
               //        Log.d("WWD", "representative name not defined");
                    representativeNameTV.setText(JsonUtil.getRepresentativeName());

                    // ---------------------------------------------------------------------
                    // next set the images using picasso
                    // ---------------------------------------------------------------------
                    senator1Image = (ImageView) findViewById(R.id.senator_1_label);
                    String senator1Url = JsonUtil.getSenator1PhotoURL();
               //     Log.d("WWD", "senator1Url is " + senator1Url);
                    if (senator1Url.length() == 0) {
                        senator1Image.setImageResource(R.drawable.nophoto);
                    } else {
                        Picasso.get().load(senator1Url).into(senator1Image);
                    }

                    senator2Image = (ImageView) findViewById(R.id.senator_2_label);
                    String senator2Url = JsonUtil.getSenator2PhotoURL();
               //     Log.d("WWD", "senator2Url is " + senator2Url);
                    if (senator2Url.length() == 0) {
                        senator2Image.setImageResource(R.drawable.nophoto);
                    } else {
                        Picasso.get().load(senator2Url).into(senator2Image);
                    }

                    representativeImage = (ImageView) findViewById(R.id.representative_image);
                    String representativeUrl = JsonUtil.getRepresentativePhotoURL();
                 //   Log.d("WWD", "representatvieUrl is " + representativeUrl);
                     if (representativeUrl.length() == 0) {
                        representativeImage.setImageResource(R.drawable.nophoto);
                    } else {
                        Picasso.get().load(representativeUrl).into(representativeImage);
                    }

                }
            } else {
                Log.d("WWD", "network error");
                //showErrorMessage();
            }
        }

    }
}