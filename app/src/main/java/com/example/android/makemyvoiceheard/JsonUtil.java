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
        JSONObject official1 = null;
        JSONObject official2 = null;
        JSONObject official3 = null;
        String official1Name = null;
        JSONArray official1AddressArray = null;
        JSONObject official1Address = null;
        String official1AddressLine1 = "";
        String official1AddressCity = "";
        String official1AddressState = "";
        String official1AddressZip = "";

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
            official1 = jsonOfficials.getJSONObject(0);
            Log.d("WWD", "official1 is " + official1);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1");
            e.printStackTrace();
        }

        try {
            official2 = jsonOfficials.getJSONObject(1);
            Log.d("WWD", "official2 is " + official2);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official2");
            e.printStackTrace();
        }

        try {
            official3 = jsonOfficials.getJSONObject(2);
            Log.d("WWD", "official2 is " + official3);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official3");
            e.printStackTrace();
        }

        // parse first officials data
        try {
            official1Name = official1.getString("name");
            Log.d("WWD", "official 1 name is " + official1Name);
        } catch (JSONException e) {
            Log.d("WWD", "error official1Name");
            e.printStackTrace();
        }

        try {
            official1AddressArray = official1.getJSONArray("address");
            Log.d("WWD", "official1AddressArray is " + official1AddressArray);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1AddressArray");
            e.printStackTrace();
        }

        try {
            official1Address = official1AddressArray.getJSONObject(0);
            Log.d("WWD", "official1Address is " + official1Address);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1Address");
            e.printStackTrace();
        }

        try {
            official1AddressLine1 = official1Address.getString("line1");
            Log.d("WWD", "official1AddressLine1 is " + official1AddressLine1);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1AddressLine1");
            e.printStackTrace();
        }

        try {
            official1AddressCity = official1Address.getString("city");
            Log.d("WWD", "official1AddressCity is " + official1AddressCity);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1AddressCity");
            e.printStackTrace();
        }

        try {
            official1AddressState = official1Address.getString("state");
            Log.d("WWD", "official1AddressState is " + official1AddressState);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1AddressState");
            e.printStackTrace();
        }

        try {
            official1AddressZip = official1Address.getString("zip");
            Log.d("WWD", "official1AddressZip is " + official1AddressZip);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing official1AddressZip");
            e.printStackTrace();
        }







    }
}
