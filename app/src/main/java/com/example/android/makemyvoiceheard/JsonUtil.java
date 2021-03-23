package com.example.android.makemyvoiceheard;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonUtil {
    public static void parseCivicsJson(String json) {
        // first convert entire response to JSON object
        JSONObject jOBJ = null;
        JSONArray jsonOffices = null;
        JSONArray jsonOfficials = null;
        try {
            jOBJ = new JSONObject(json);
            Log.d("WWD", "jOBJ is " + jOBJ.toString());
        } catch (JSONException e) {
            Log.d("WWD", "exception on creating JSON object from json string");
            e.printStackTrace();
        }

        // parse normalizedInput
        try {
            JSONObject normalizedInput = jOBJ.getJSONObject("normalizedInput");
            Log.d("WWD", "normalizedInput is " + normalizedInput);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing normalizedinput");
            e.printStackTrace();
        }

        //parse offices
        try {
            jsonOffices = new JSONArray();
            jsonOffices  = jOBJ.getJSONArray("offices");
            Log.d("WWD", "offices is " + jsonOffices);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing offices");
            e.printStackTrace();
        }

        //parse officials
        try {
            jsonOfficials = new JSONArray();
            jsonOfficials  = jOBJ.getJSONArray("officials");
            Log.d("WWD", "officials are " + jsonOfficials);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing officials");
            e.printStackTrace();
        }

    }
}
