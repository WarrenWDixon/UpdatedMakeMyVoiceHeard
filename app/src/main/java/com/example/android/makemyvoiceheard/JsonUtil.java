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
        JSONObject senator1 = null;
        JSONObject senator2 = null;
        JSONObject congressman = null;

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
        Log.d("WWD", "the officals arrays size is " + jsonOfficials.length());
        try {
            senator1 = jsonOfficials.getJSONObject(0);
            Log.d("WWD", "senator1 is " + senator1);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing senator1");
            e.printStackTrace();
        }

        try {
            senator2 = jsonOfficials.getJSONObject(1);
            Log.d("WWD", "senator2 is " + senator2);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing senator2");
            e.printStackTrace();
        }

        try {
            congressman = jsonOfficials.getJSONObject(2);
            Log.d("WWD", "congressman is " + congressman);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing congressman");
            e.printStackTrace();
        }

    }
}
