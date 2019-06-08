package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        JSONObject js = null;
        try {
            js = new JSONObject(json);

            JSONObject name = js.optJSONObject(KEY_NAME);

            String mainName = null;
            JSONArray alsoKnownAs = null;

            if (name != null) {
                mainName = name.optString(KEY_MAIN_NAME);
                alsoKnownAs = name.optJSONArray(KEY_ALSO_KNOWN_AS);
            }

            List<String> knownAs = convertJsonArrayToList(alsoKnownAs);
            String placeOfOrigin = js.optString(KEY_PLACE_OF_ORIGIN);
            String description = js.optString(KEY_DESCRIPTION);
            String image = js.optString(KEY_IMAGE);
            JSONArray ing = js.optJSONArray(KEY_INGREDIENTS);
            List<String> ingredients = convertJsonArrayToList(ing);

            sandwich = new Sandwich(
                    mainName,
                    knownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    /*
     * Converting JSON array to List of strings
     * JSON array must be an array of strings
     * If element is null it will not be added to the list
     *
     * @param JSONArray(String)
     * @return List<String> or N/A as list
     *
     * */
    private static List<String> convertJsonArrayToList(JSONArray arr) {
        if (arr == null || arr.length() == 0) return Arrays.asList("N/A");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            if (arr.optString(i) != null) {
                list.add(arr.optString(i));
            }
        }
        return list;
    }
}
