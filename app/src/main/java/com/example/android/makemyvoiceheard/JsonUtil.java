package com.example.android.makemyvoiceheard;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import java.util.List;

class JsonUtil {
    private static JSONObject jOBJ;
    private static JSONArray jsonOffices;
    private static JSONArray jsonOfficials;
    private static JSONArray official1AddressArray;
    private static Integer senator1Index = -1;
    private static Integer senator2Index = -1;
    private static Integer representativeIndex = -1;
    private static List<JSONObject> officialJSONArray;
    private static List<JSONArray> addressJSONArray;
    private static List<JSONArray> phoneJSONArray;
    private static List<JSONObject> officesJSONArray;
    private static List<JSONArray> officialURLArray;
    private static List<String> officialNameArray;
    private static List<String> officialPartyArray;
    private static List<String> officialPhotoURLArray;
    private static List<String> officialLine1Array;
    private static List<String> officialCityArray;
    private static List<String> officialStateArray;
    private static List<String> officialZipCodeArray;
    private static List<String> officeNameArray;
    private static List<JSONArray> officialIndexJSONArray;

    private static String senator1Name = "";
    private static String senator2Name = "";
    private static String representativeName = "";


    //member variables for storing data in shared preferences
    private static SharedPreferences mPreferences;
    private static String sharedPrefFile = "com.example.android.makemyvoiceheardprefs";


    public static Boolean parseCivicsJson(String json) {
        // first convert entire response to JSON object
        jOBJ = null;
        jsonOffices = null;
        jsonOfficials = null;
        official1AddressArray = null;
        officialJSONArray = new ArrayList<>();
        addressJSONArray = new ArrayList<>();
        phoneJSONArray = new ArrayList<>();
        officesJSONArray = new ArrayList<>();
        officialNameArray = new ArrayList<>();
        officialPartyArray = new ArrayList<>();
        officialLine1Array = new ArrayList<>();
        officialCityArray = new ArrayList<>();
        officialStateArray = new ArrayList<>();
        officialZipCodeArray = new ArrayList<>();
        officeNameArray = new ArrayList<>();
        officialURLArray = new ArrayList<>();
        officialPhotoURLArray = new ArrayList<>();
        officialIndexJSONArray = new ArrayList<>();

        if (!parseJSONObject(json))
            return false;
        //Log.d("WWD", " parseJSONObject worked");

        if (!parseNormalizedInput())
            return false;
        //Log.d("WWD", " parseNormalizedInput worked");

        if (!parseOffices())
            return false;
        //Log.d("WWD", " parseOffices worked");

        if (!parseOfficials())
            return false;
        //Log.d("WWD", " parseOfficials worked");

        if (!parseIndicies())
            return false;
        //Log.d("WWD", " parseIndicies worked");

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
            jsonOffices = jOBJ.getJSONArray("offices");
            //Log.d("WWD", "\n\n offices is " + jsonOffices);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing offices");
            e.printStackTrace();
            return false;
        }

        int numOffices = jsonOffices.length();
        //Log.d("WWD", "numoffices is " + numOffices);
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
                   // Log.d("WWD", "\n\n office name " + i + " = " + officeNameArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing name");
                    e.printStackTrace();
                    return false;
                }

                try {
                    officialIndexJSONArray.add(officesJSONArray.get(i).getJSONArray("officialIndices"));
                    //Log.d("WWD", "\n\n officialIndexJSONArray is " + officialIndexJSONArray.get(i));
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
            jsonOfficials = jOBJ.getJSONArray("officials");
            //Log.d("WWD", "officials are " + jsonOfficials);
        } catch (JSONException e) {
            Log.d("WWD", "error parsing officials");
            e.printStackTrace();
            return false;
        }
       // Log.d("WWD", "the officals arrays size is " + jsonOfficials.length());

        int numOfficials = jsonOfficials.length();
        if (numOfficials > 0) {
           // Log.d("WWD", "above parseOfficials for loop");
            for (int i = 0; i < numOfficials; i++) {
               // Log.d("WWD", "=======================================================================");
               // Log.d("WWD", "\n\n ****************** in for loop i = " + i);
               // Log.d("WWD", "=======================================================================");
                try {
                    officialJSONArray.add(jsonOfficials.getJSONObject(i));
                 //   Log.d("WWD", "official1 is " + officialJSONArray.get(i));
                } catch (JSONException e) {
                    Log.d("WWD", "error parsing official1");
                    e.printStackTrace();
                    return false;
                }

                // parse name
               // Log.d("WWD", "\n now parse name");
                try {
                    officialNameArray.add(officialJSONArray.get(i).getString("name"));
                 //   Log.d("WWD", "name " + i + " = " + officialNameArray.get(i));
                } catch (JSONException e1) {
                    Log.d("WWD", "error parsing name");
                    e1.printStackTrace();
                    return false;
                }

                //parse address
               // Log.d("WWD", "\n now parse address");
                try {
                    addressJSONArray.add(officialJSONArray.get(i).getJSONArray("address"));
               //     Log.d("WWD", "addressJSONArray is " + addressJSONArray);
                } catch (JSONException e3) {
                    Log.d("WWD", "error parsing addressJSONArray");
                    e3.printStackTrace();
                    return false;
                }

                // parse party
              //  Log.d("WWD", "\n now parse party");
                try {
                    officialPartyArray.add(officialJSONArray.get(i).getString("party"));
               //     Log.d("WWD", "party " + i + " = " + officialPartyArray.get(i));
                } catch (JSONException e2) {
                    Log.d("WWD", "error parsing party");
                    e2.printStackTrace();
                    return false;
                }

                // parse phones
              //  Log.d("WWD", "\n now parse phones");
                try {
                    phoneJSONArray.add(officialJSONArray.get(i).getJSONArray("phones"));
              //      Log.d("WWD", "phoneJSONArray is " + phoneJSONArray);
                } catch (JSONException e6) {
                    Log.d("WWD", "error parsing phoneJSONArray");
                    e6.printStackTrace();
                    return false;
                }

              //  Log.d("WWD", "now parse official URL");
                try {
                    officialURLArray.add(officialJSONArray.get(i).getJSONArray("urls"));
             //       Log.d("WWD", "url " + i + " = " + officialURLArray.get(i));
                } catch (Exception e5) {
                    officialURLArray.add(new JSONArray());
                    Log.d("WWD", "error parsing office url " + i);
                    //e.printStackTrace();
                    //return false;
                }

                // parse photoURL
             //   Log.d("WWD", "\nnow parse photoUrl");
                try {
                    officialPhotoURLArray.add(officialJSONArray.get(i).getString("photoUrl"));
               //     Log.d("WWD", "url " + i + " = " + officialPhotoURLArray.get(i));
                } catch (Exception e4) {
                    officialPhotoURLArray.add("");
                    Log.d("WWD", "error parsing photo url " + i);
                }

                // now parse elements of address
                try {
                    officialLine1Array.add(addressJSONArray.get(i).getJSONObject(0).getString("line1"));
             //       Log.d("WWD", "line1 " + i + " = " + officialLine1Array.get(i));
                } catch (JSONException e7) {
                    Log.d("WWD", "error parsing line " + i);
                    e7.printStackTrace();
                    return false;
                }

                try {
                    officialCityArray.add(addressJSONArray.get(i).getJSONObject(0).getString("city"));
              //      Log.d("WWD", "City " + i + " = " + officialCityArray.get(i));
                } catch (JSONException e8) {
                    Log.d("WWD", "error parsing city" + i);
                    e8.printStackTrace();
                    return false;
                }

                try {
                    officialStateArray.add(addressJSONArray.get(i).getJSONObject(0).getString("state"));
                    //Log.d("WWD", "State " + i + " = " + officialStateArray.get(i));
                } catch (JSONException e9) {
                    Log.d("WWD", "error parsing state " + i);
                    e9.printStackTrace();
                    return false;
                }

                try {
                    officialZipCodeArray.add(addressJSONArray.get(i).getJSONObject(0).getString("zip"));
                    //Log.d("WWD", "zip " + i + " = " + officialZipCodeArray.get(i));
                } catch (JSONException e10) {
                    Log.d("WWD", "error parsing zip " + i);
                    e10.printStackTrace();
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
        int numIndices;
      //  Log.d("WWD", "numNames is " + numNames);
        if (numNames > 0) {
            for (int i = 0; i < numNames; i++) {
                String name = officeNameArray.get(i);
             //   Log.d("WWD", "name is " + name);
                if (name.contains("Senator")) {
              //      Log.d("WWD", "found Senator");
                    numIndices = officialIndexJSONArray.get(i).length();
               //     Log.d("WWD", "for office " + i + " the number of indices is " + numIndices);
                    try {
                        senator1Index = (Integer) (officialIndexJSONArray.get(i).get(0));
              //          Log.d("WWD", "\n\n ********* senator 1 index is " + senator1Index);
                    } catch (JSONException e) {
                        Log.d("WWD", "error parsing senator 1 index");
                        e.printStackTrace();
                        return false;
                    }
                    try {
                        senator2Index = (Integer) (officialIndexJSONArray.get(i).get(1));
              //          Log.d("WWD", "\n\n ************* senator 2 index is " + senator2Index);
                    } catch (JSONException e) {
                        Log.d("WWD", "error parsing senator 2 index");
                        e.printStackTrace();
                        return false;
                    }
                } else if (name.contains("Representative")) {
              //      Log.d("WWD", "found Representative");
                    try {
                        representativeIndex = (Integer) (officialIndexJSONArray.get(i).get(0));
                        Log.d("WWD", "\n\n **************** representative index is " + representativeIndex);
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

    // ---------------------------------------------------------------------------------------------
    // access methods for senator 1
    // ---------------------------------------------------------------------------------------------
    public static String getSenator1PhotoURL() {
     //   Log.d("WWD", "In getSenator1PhotoUrl, senator1Index is " + senator1Index);
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        if (officialPhotoURLArray.get(senator1Index).length() > 0) {
     //       Log.d("WWD", "senator 1 url is " + officialPhotoURLArray.get(senator1Index));
            return officialPhotoURLArray.get(senator1Index);
        }
     //   Log.d("WWD", "senator 1 has no photo return empty string");
        return "";
    }

    public static String getSenator1URL() {
     //   Log.d("WWD", "In getSenator1Url, senator1Index is " + senator1Index);
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        try {
            if (officialURLArray.get(senator1Index).get(0).toString().length() > 0) {
      //          Log.d("WWD", "senator 1 url is " + officialURLArray.get(senator1Index).get(0).toString());
                return officialURLArray.get(senator1Index).get(0).toString();
            }
        } catch (JSONException e) {
            Log.d("WWD", "senator 1 has no URL return empty string");
            return "";
        }
        return "";
    }

    public static String getSenator1Phone() {
    //    Log.d("WWD", "In getSenator1Phone, senator1Index is " + senator1Index);
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        try {
            if (phoneJSONArray.get(senator1Index).get(0).toString().length() > 0) {
      //          Log.d("WWD", "senator 1 phone is " + phoneJSONArray.get(senator1Index).get(0).toString());
                return phoneJSONArray.get(senator1Index).get(0).toString();
            }
        } catch (JSONException e) {
            Log.d("WWD", "senator 1 has no phone return empty string");
            return "";
        }
        return "";
    }

    public static String getSenator1Name() {
    //    Log.d("WWD", "in getSenator1Name");
        //return "testStringName";
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        if (officialNameArray.get(senator1Index).length() > 0) {
       //     Log.d("WWD", "senator 1 name is " + officialNameArray.get(senator1Index));
            return officialNameArray.get(senator1Index);
        }
        Log.d("WWD", "senator 1 has no name return empty string");
        return "";
    }

    public static String getSenator1Party() {
     //   Log.d("WWD", "in getSenator1Party");
        //return "testStringName";
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        if (officialPartyArray.get(senator1Index).length() > 0) {
       //     Log.d("WWD", "senator 1 party is " + officialPartyArray.get(senator1Index));
            return officialPartyArray.get(senator1Index);
        }
        Log.d("WWD", "senator 1 has no party return empty string");
        return "";
    }

    public static String getSenator1AddressLine1() {
    //    Log.d("WWD", "in getSenator1AddressLine1");
        //return "testStringName";
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        if (officialLine1Array.get(senator1Index).length() > 0) {
       //     Log.d("WWD", "senator 1 party is " + officialLine1Array.get(senator1Index));
            return officialLine1Array.get(senator1Index);
        }
        Log.d("WWD", "senator 1 has no party return empty string");
        return "";
    }

    public static String getSenator1AddressLine2() {
    //    Log.d("WWD", "in getSenator1AddressLine2");
        //return "testStringName";
        String line2 = "";
        if ((senator1Index < 0) || (senator1Index > 2))
            return "";
        if (officialCityArray.get(senator1Index).length() > 0) {
     //       Log.d("WWD", "senator 1 city is " + officialCityArray.get(senator1Index));
            line2 += officialCityArray.get(senator1Index);
        }
        if (officialStateArray.get(senator1Index).length() > 0) {
       //     Log.d("WWD", "senator 1 State is " + officialStateArray.get(senator1Index));
            line2 += ", " + officialStateArray.get(senator1Index);
        }
        if (officialZipCodeArray.get(senator1Index).length() > 0) {
       //     Log.d("WWD", "senator 1 Zip Code is " + officialZipCodeArray.get(senator1Index));
            line2 += " " + officialZipCodeArray.get(senator1Index);
        }
     //   Log.d("WWD", "line2 is " + line2);
        return line2;
    }

    // ---------------------------------------------------------------------------------------------
    // access methods for senator 2
    // ---------------------------------------------------------------------------------------------
    public static String getSenator2URL() {
    //    Log.d("WWD", "In getSenator2Url, senator2Index is " + senator2Index);
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        try {
            if (officialURLArray.get(senator2Index).get(0).toString().length() > 0) {
         //       Log.d("WWD", "senator 2 url is " + officialURLArray.get(senator2Index).get(0).toString());
                return officialURLArray.get(senator2Index).get(0).toString();
            }
        } catch (JSONException e) {
            Log.d("WWD", "senator 2 has no URL return empty string");
            return "";
        }
        return "";
    }

    public static String getSenator2Phone() {
    //    Log.d("WWD", "In getSenator2Phone, senator2Index is " + senator2Index);
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        try {
            if (phoneJSONArray.get(senator2Index).get(0).toString().length() > 0) {
      //          Log.d("WWD", "senator 2 phone is " + phoneJSONArray.get(senator2Index).get(0).toString());
                return phoneJSONArray.get(senator2Index).get(0).toString();
            }
        } catch (JSONException e) {
            Log.d("WWD", "senator 2 has no phone return empty string");
            return "";
        }
        return "";
    }

    public static String getSenator2PhotoURL() {
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        if (officialPhotoURLArray.get(senator2Index).length() > 0) {
    //        Log.d("WWD", "senator 2 url is " + officialPhotoURLArray.get(senator2Index));
            return officialPhotoURLArray.get(senator2Index);
        }
  //      Log.d("WWD", "senator2 has no photo return empty string");
        return "";
    }

    public static String getSenator2Name() {
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        if (officialNameArray.get(senator2Index).length() > 0) {
     //       Log.d("WWD", "senator 2 name is " + officialNameArray.get(senator2Index));
            return officialNameArray.get(senator2Index);
        }
    //    Log.d("WWD", "senator2 has no name return empty string");
        return "";
    }

    public static String getSenator2Party() {
    //    Log.d("WWD", "in getSenator2Party");
        //return "testStringName";
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        if (officialPartyArray.get(senator2Index).length() > 0) {
     //       Log.d("WWD", "senator 2 party is " + officialPartyArray.get(senator2Index));
            return officialPartyArray.get(senator2Index);
        }
   //     Log.d("WWD", "senator 2 has no party return empty string");
        return "";
    }

    public static String getSenator2AddressLine1() {
    //    Log.d("WWD", "in getSenator2AddressLine1");
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        if (officialLine1Array.get(senator2Index).length() > 0) {
      //      Log.d("WWD", "senator 2 party is " + officialLine1Array.get(senator2Index));
            return officialLine1Array.get(senator2Index);
        }
        Log.d("WWD", "senator 2 line 1 return empty string");
        return "";
    }

    public static String getSenator2AddressLine2() {
    //    Log.d("WWD", "in getSenator2AddressLine2");
        //return "testStringName";
        String line2 = "";
        if ((senator2Index < 0) || (senator2Index > 2))
            return "";
        if (officialCityArray.get(senator2Index).length() > 0) {
     //       Log.d("WWD", "senator 2 city is " + officialCityArray.get(senator2Index));
            line2 += officialCityArray.get(senator2Index);
        }
        if (officialStateArray.get(senator2Index).length() > 0) {
      //      Log.d("WWD", "senator 2 State is " + officialStateArray.get(senator2Index));
            line2 += ", " + officialStateArray.get(senator2Index);
        }
        if (officialZipCodeArray.get(senator2Index).length() > 0) {
     //       Log.d("WWD", "senator 2 Zip Code is " + officialZipCodeArray.get(senator2Index));
            line2 += " " + officialZipCodeArray.get(senator2Index);
        }
    //    Log.d("WWD", "line2 is " + line2);
        return line2;
    }

    // ---------------------------------------------------------------------------------------------
    // access methods for representative
    // ---------------------------------------------------------------------------------------------

    public static String getRepresentativePhotoURL() {
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        if (officialURLArray.get(representativeIndex).length() > 0) {
     //       Log.d("WWD", "represenative url is " + officialURLArray.get(representativeIndex));
            return officialPhotoURLArray.get(representativeIndex);
        }
    //    Log.d("WWD", "representaive has no photo return empty string");
        return "";
    }

    public static String getRepresentativeURL() {
    //    Log.d("WWD", "In getSenator2Url, senator2Index is " + senator2Index);
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        try {
            if (officialURLArray.get(representativeIndex).get(0).toString().length() > 0) {
     //           Log.d("WWD", "representative url is " + officialURLArray.get(representativeIndex).get(0).toString());
                return officialURLArray.get(representativeIndex).get(0).toString();
            }
        } catch (JSONException e) {
            Log.d("WWD", "representative has no URL return empty string");
            return "";
        }
        return "";
    }

    public static String getRepresentativePhone() {
     //   Log.d("WWD", "In getRepresentativePhone, representativeIndex is " + representativeIndex);
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        try {
            if (phoneJSONArray.get(representativeIndex).get(0).toString().length() > 0) {
      //          Log.d("WWD", "representative phone is " + phoneJSONArray.get(representativeIndex).get(0).toString());
                return phoneJSONArray.get(representativeIndex).get(0).toString();
            }
        } catch (JSONException e) {
            Log.d("WWD", "representative has no phone return empty string");
            return "";
        }
        return "";
    }

    public static String getRepresentativeName() {
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        if (officialNameArray.get(representativeIndex).length() > 0) {
      //      Log.d("WWD", "representative name is " + officialNameArray.get(representativeIndex));
            return officialNameArray.get(representativeIndex);
        }
        return "";
    }

    public static String getRepresentativeParty() {
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        if (officialPartyArray.get(representativeIndex).length() > 0) {
      //      Log.d("WWD", "representative party is " + officialPartyArray.get(representativeIndex));
            return officialPartyArray.get(representativeIndex);
        }
        return "";
    }

    public static String getRepresentativeAddressLine1() {
    //    Log.d("WWD", "in getRepresentativeAddressLine1");
        //return "testStringName";
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        if (officialLine1Array.get(representativeIndex).length() > 0) {
     //       Log.d("WWD", "senator 2 party is " + officialLine1Array.get(representativeIndex));
            return officialLine1Array.get(representativeIndex);
        }
   //     Log.d("WWD", "representative line 1 return empty string");
        return "";
    }

    public static String getRepresentativeAddressLine2() {
    //    Log.d("WWD", "in getRepresentativeAddressLine2");
        //return "testStringName";
        String line2 = "";
        if ((representativeIndex < 0) || (representativeIndex > 2))
            return "";
        if (officialCityArray.get(representativeIndex).length() > 0) {
      //      Log.d("WWD", "senator 2 city is " + officialCityArray.get(representativeIndex));
            line2 += officialCityArray.get(representativeIndex);
        }
        if (officialStateArray.get(representativeIndex).length() > 0) {
      //      Log.d("WWD", "senator 2 State is " + officialStateArray.get(representativeIndex));
            line2 += ", " + officialStateArray.get(representativeIndex);
        }
        if (officialZipCodeArray.get(representativeIndex).length() > 0) {
    //        Log.d("WWD", "senator 2 Zip Code is " + officialZipCodeArray.get(representativeIndex));
            line2 += " " + officialZipCodeArray.get(representativeIndex);
        }
    //    Log.d("WWD", "line2 is " + line2);
        return line2;
    }

}
