package com.example.sandwichclub.utils;

import android.util.Log;

import com.example.sandwichclub.model.Sandwich;
import com.example.sandwichclub.model.Sandwiches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *  This is for practice purposes instead of using third party library
 */
public class JsonUtil {

    private final static String TAG = JsonUtil.class.getSimpleName();

    private final static String SANDWICH_NAME = "name";
    private final static String SANDWICH_MAIN_NAME = "mainName";
    private final static String SANDWICH_DESCRIPTION = "description";
    private final static String SANDWICH_IMAGE  = "image";
    private final static String SANDWICH_INGREDIENTS = "ingredients";
    private final static String SANDWICH_KNOWN_AS = "alsoKnownAs";
    private final static String SANDWICH_ORIGIN = "placeOfOrigin";

    /**
     *  This method will receive the json string and returns the Sandwiches list.
     *  Sandwiches list contains all sandwich names
     *
     * @param json string
     * @return Sandwiches
     * @throws JSONException
     */
    public static Sandwiches parseSandwiches(String json){
        Log.v(TAG,json);
        List<String> nameList = null;
        Sandwiches sandwiches;

        if(json == null || json.equals("")) return null;

        try {
            JSONArray jsonArray = new JSONArray(json);

            nameList = convertJSONObjListToList(jsonArray, SANDWICH_NAME);

        } catch (JSONException e){
            Log.e(TAG, "Error parsing the sandwich names");
            e.printStackTrace();
        }

        sandwiches = new Sandwiches(nameList);

        return sandwiches;
    }

    /**
     *  This method will receive a json string and returns a single sandwich.
     *
     * @param json
     * @return Sandwich
     * @throws JSONException
     */
    public static Sandwich parseSandwich(String json){

        Sandwich sandwich = null;
        JSONObject sandwichJson;

        if(json == null || json.equals("")) return null;

        try{
            sandwichJson = new JSONObject(json);

            JSONObject jsonName = sandwichJson.optJSONObject(SANDWICH_NAME);
            String mainName = jsonName.optString(SANDWICH_MAIN_NAME);
            JSONArray alsoKnownAsJSON = jsonName.optJSONArray(SANDWICH_KNOWN_AS);

            List<String> alsoKnownAs = convertToList(alsoKnownAsJSON);

            String image = sandwichJson.optString(SANDWICH_IMAGE);
            String description = sandwichJson.optString(SANDWICH_DESCRIPTION);
            JSONArray ingJson = sandwichJson.optJSONArray(SANDWICH_INGREDIENTS);
            List<String> ingredients = convertToList(ingJson);

            String placeOfOrigin = sandwichJson.optString(SANDWICH_ORIGIN);

            sandwich = new Sandwich(
                    mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients);


        } catch (JSONException e){
            Log.e(TAG, "Error parsing the sandwich names");
            e.printStackTrace();
        }

        return sandwich;
    }

    /**
     *  This method will receive a jsonArray of objects
     *  The jsonArray contains object
     *
     * @param jsonArray
     * @param name  object name in the jsonArray
     * @return list of strings
     * @throws JSONException
     */
    private static List<String> convertJSONObjListToList(JSONArray jsonArray , String name) throws JSONException{
        if(jsonArray == null) return null;
        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject nameObj =  jsonArray.getJSONObject(i);
            nameList.add(nameObj.optString(name));
            Log.d(TAG, nameList.get(i));
        }

        return nameList;
    }

    /**
     *  This method receives a jsonArray of strings
     * @param jsonArray
     * @return list of strings
     * @throws JSONException
     */
    private static List<String> convertToList(JSONArray jsonArray) throws JSONException{
        if(jsonArray == null) return null;
        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            nameList.add(jsonArray.get(i).toString());
            Log.d(TAG, nameList.get(i));
        }

        return nameList;
    }
}
