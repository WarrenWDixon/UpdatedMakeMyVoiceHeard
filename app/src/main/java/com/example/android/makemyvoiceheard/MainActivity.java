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

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String userAddress = "temp";
        new CivicQueryTask().execute(userAddress);
        try {
            Thread.sleep(1000);
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
                civicResults = NetworkUtils.getResponseFromHttpUrl(address);
                Log.d("WWD", "civicResults):" + civicResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return civicResults;
        }


        @Override
        protected void onPostExecute(String civicSearchResults) {
            Log.d("WWD", "in onPostExecute");
            if (NetworkUtils.getNetworkConnected()) {
                if (civicSearchResults != null && !civicSearchResults.equals("")) {
                    Log.d("WWD", "got civics results" + civicSearchResults);
                    //JsonUtil.parseCivcsJson(civicSearchResults);
                }
            } else {
                //showErrorMessage();
            }
        }

    }
}