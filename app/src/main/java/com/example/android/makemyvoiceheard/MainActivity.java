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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView senator1NameTV;
    private TextView senator2NameTV;
    private TextView representativeNameTV;
    private ImageView senator1Image;
    private ImageView senator2Image;
    private ImageView representativeImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String userAddress = "temp";
        new CivicQueryTask().execute(userAddress);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToDetail(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
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
            Log.d("WWD", "in doInBackground");
            try {
                Log.d("WWD", "call network utils");
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
            Log.d("WWD", "in onPostExecute");
            if (NetworkUtils.getNetworkConnected()) {
                if (civicSearchResults != null && !civicSearchResults.equals("")) {
                    // ---------------------------------------------------------------------
                    // first set the names to the text views
                    // ---------------------------------------------------------------------
                    JsonUtil.parseCivicsJson(civicSearchResults);

                    senator1NameTV = (TextView) findViewById(R.id.senator_1_name);
                    senator1Name = JsonUtil.getSenator1Name();
                    Log.d("WWD", "senator1Name is " + senator1Name);
                    if (senator1Name.length() > 0)
                        Log.d("WWD", "senator 1 name is " + senator1Name);
                    else
                        Log.d("WWD", "senator 1 name not defined");
                    senator1NameTV.setText(JsonUtil.getSenator1Name());

                    senator2NameTV = (TextView) findViewById(R.id.senator_2_name);
                    senator2Name = JsonUtil.getSenator2Name();
                    if (senator2Name.length() > 0)
                        Log.d("WWD", "senator 2 name is " + senator2Name);
                    else
                        Log.d("WWD", "senator 2 name not defined");
                    senator2NameTV.setText(JsonUtil.getSenator2Name());

                    representativeNameTV = (TextView) findViewById(R.id.representative_name);
                    representativeName = JsonUtil.getRepresentativeName();
                    if (representativeName.length() > 0)
                        Log.d("WWD", "representative name is " + representativeName);
                    else
                        Log.d("WWD", "representative name not defined");
                    representativeNameTV.setText(JsonUtil.getRepresentativeName());

                    // ---------------------------------------------------------------------
                    // next set the images using picasso
                    // ---------------------------------------------------------------------
                    senator1Image = (ImageView) findViewById(R.id.senator_1_label);
                    String senator1Url = JsonUtil.getSenator1PhotoURL();
                    Log.d("WWD", "senator1Url is " + senator1Url);
                    if (senator1Url.length() == 0) {
                        senator1Image.setImageResource(R.drawable.nophoto);
                    } else {
                        Picasso.get().load(senator1Url).into(senator1Image);
                    }

                    senator2Image = (ImageView) findViewById(R.id.senator_2_label);
                    String senator2Url = JsonUtil.getSenator2PhotoURL();
                    Log.d("WWD", "senator2Url is " + senator2Url);
                    if (senator2Url.length() == 0) {
                        senator2Image.setImageResource(R.drawable.nophoto);
                    } else {
                        Picasso.get().load(senator2Url).into(senator2Image);
                    }

                    representativeImage = (ImageView) findViewById(R.id.representative_image);
                    String representativeUrl = JsonUtil.getRepresentativePhotoURL();
                    Log.d("WWD", "representatvieUrl is " + representativeUrl);
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