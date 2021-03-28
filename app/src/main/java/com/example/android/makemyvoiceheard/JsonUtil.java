package com.example.android.makemyvoiceheard;

import android.util.Log;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtil {
    private static JSONObject jOBJ;
    private static JSONArray jsonOffices;
    private static JSONArray jsonOfficials;
    private static JSONArray official1AddressArray;
    private static Integer senator1Index;
    private static Integer senator2Index;
    private static Integer represenativeIndex;
    private static List<JSONObject> officialJSONArray;
    private static List<JSONArray> addressJSONArray;
    private static List<JSONObject> officesJSONArray;
    private static List<String> officialNameArray;
    private static List<String> officialLine1Array;
    private static List<String> officialCityArray;
    private static List<String> officialStateArray;
    private static List<String> officialZipCodeArray;
    private static List<String> officeNameArray;
    private static List<JSONArray> officialIndexJSONArray;

    public static Boolean parseCivicsJson(String json) {
        // first convert entire response to JSON object
        jOBJ = null;
        jsonOffices = null;
        jsonOfficials = null;
        official1AddressArray = null;
        officialJSONArray = new ArrayList<>();
        addressJSONArray = new ArrayList<>();
        officesJSONArray = new ArrayList<>();
        officialNameArray = new ArrayList<>();
        officialLine1Array = new ArrayList<>();
        officialCityArray = new ArrayList<>();
        officialStateArray = new ArrayList<>();
        officialZipCodeArray = new ArrayList<>();
        officeNameArray = new ArrayList<>();
        officialIndexJSONArray = new ArrayList<>();

        if (!parseJSONObject(json))
            return false;
        Log.d("WWD", " parseJSONObject worked");

        if (!parseNormalizedInput())
            return false;
        Log.d("WWD", " parseNormalizedInput worked");

        if (!parseOffices())
            return false;
        Log.d("WWD", " parseOffices worked");

        if (!parseOfficials())
            return false;
        Log.d("WWD", " parseOfficials");

        if (!parseIndicies())
            return false;
        Log.d("WWD", " parseIndicies worked");

        return true;
    }

    private static Boolean parseJSONObject(String json) {
        try {
            jOBJ = new JSONObject(json);
            //Log.d("WWD", "jOBJ is " + jOBJ.toString());
        } catch (JSONException e) {
            Log.d("WWD", "exception on creating JSON object from json string");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Boolean parseNormalizedInput() {
        // parse normalizedInput
        try {
            JSONObject normalizedInput = jOBJ.getJSONObject("normalizedInput");
            //Log.d("WWD", "normalizedInput is " + normalizedInput);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing normalizedinput");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Boolean parseOffices() {
        try {
            jsonOffices = new JSONArray();
            jsonOffices  = jOBJ.getJSONArray("offices");
            //Log.d("WWD", "\n\n offices is " + jsonOffices);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing offices");
            e.printStackTrace();
            return false;
        }

        int numOffices = jsonOffices.length();
        Log.d("WWD", "numoffices is " + numOffices);
        if (numOffices > 0) {
            for (int i = 0; i < numOffices; i++) {
                try {
                    officesJSONArray.add(jsonOffices.getJSONObject(i));
                    //Log.d("WWD", "\n\noffice is " + officesJSONArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing office");
                    e.printStackTrace();
                    return false;
                }

                try {
                    officeNameArray.add(officesJSONArray.get(i).getString("name"));
                    Log.d("WWD", "\n\n office name " + i + " = " + officeNameArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing name");
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialIndexJSONArray.add(officesJSONArray.get(i).getJSONArray("officialIndices"));
                    Log.d("WWD", "\n\n officialIndexJSONArray is " + officialIndexJSONArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing officialIndexJSONArray");
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private static Boolean parseOfficials() {
        try {
            jsonOfficials = new JSONArray();
            jsonOfficials  = jOBJ.getJSONArray("officials");
            Log.d("WWD", "officials are " + jsonOfficials);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing officials");
            e.printStackTrace();
            return false;
        }
        Log.d("WWD", "the officals arrays size is " + jsonOfficials.length());

        int numOfficials = jsonOfficials.length();
        if (numOfficials > 0) {
            for (int i = 0; i < numOfficials; i++) {
                try {
                    officialJSONArray.add(jsonOfficials.getJSONObject(i));
                    //Log.d("WWD", "official1 is " + officialJSONArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing official1");
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialNameArray.add(officialJSONArray.get(i).getString("name"));
                    //Log.d("WWD", "name " + i + " = " + officialNameArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing name");
                    e.printStackTrace();
                    return false;
                }

                try {
                    addressJSONArray.add(officialJSONArray.get(i).getJSONArray("address"));
                    //Log.d("WWD", "official1AddressArray is " + official1AddressArray);
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing official1AddressArray");
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialLine1Array.add(addressJSONArray.get(i).getJSONObject(0).getString("line1"));
                    //Log.d("WWD", "line1 " + i + " = " + officialLine1Array.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing line " + i);
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialCityArray.add(addressJSONArray.get(i).getJSONObject(0).getString("city"));
                    //Log.d("WWD", "City " + i + " = " + officialCityArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing city" + i );
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialStateArray.add(addressJSONArray.get(i).getJSONObject(0).getString("state"));
                    //Log.d("WWD", "State " + i + " = " + officialStateArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing state " + i);
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialZipCodeArray.add(addressJSONArray.get(i).getJSONObject(0).getString("zip"));
                    //Log.d("WWD", "zip " + i + " = " + officialZipCodeArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing zip " + i);
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private static Boolean parseIndicies() {
        int numNames = officeNameArray.size();
        Log.d("WWD", "numNames is " + numNames);
        if (numNames > 0) {
            for (int i = 0; i < numNames; i++) {
                String name = officeNameArray.get(i);
                Log.d("WWD", "name is " + name);
                if (name.contains("Senator")) {
                    try {
                        senator1Index = (Integer) (officialIndexJSONArray.get(i).get(0));
                        Log.d("WWD", "\n\n ********* senator 1 index is " + senator1Index);
                    } catch (JSONException e) {
                        Log.d("WWD", "error parsing senator 1 index");
                        e.printStackTrace();
                        return false;
                    }
                    try {
                        senator2Index = (Integer) (officialIndexJSONArray.get(i).get(1));
                        Log.d("WWD", "\n\n ************* senator 2 index is " + senator2Index);
                    } catch (JSONException e) {
                        Log.d("WWD", "error parsing senator 2 index");
                        e.printStackTrace();
                        return false;
                    }
                } else if (name.contains("Represenative")) {
                    Log.d("WWD", "found Representative")
                    try {
                        represenativeIndex = (Integer) (officialIndexJSONArray.get(i).get(0));
                        Log.d("WWD", "\n\n **************** representative index is " + represenativeIndex);
                    } catch (JSONException e) {
                        Log.d("WWD", "error parsing representative index");
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        }
        return true;
    }

}