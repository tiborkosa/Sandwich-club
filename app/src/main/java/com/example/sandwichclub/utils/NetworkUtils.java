package com.example.sandwichclub.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.sandwichclub.utils.config.*;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    // param for query
    final static String PARAM_QUERY = "q";

    // sorts
    final static String PARAM_SORT = "sort";
    final static String sortBy = "name.mainName";


    /**
     *  NOT implemented
     *
     * This method will build  a search url where the data will be fetched
     *
     * @param searchQuery the query that we want to search
     * @return the built URL
     * @throws MalformedURLException
     */
    public static URL buildSearchUrl(String searchQuery) {
        Uri builtUri = Uri
                .parse(SANDWICH_BASE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_QUERY, searchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while building the search URL!");
        }
        return url;
    }

    /**
     *  This method is used to build a path url
     *
     * @param path where the data will be fetched
     * @return the built url
     * @throws MalformedURLException
     */
    public static URL buildPathUrl (String path){
        Uri builtUri = Uri
                .parse(SANDWICH_BASE_URL)
                .buildUpon()
                .appendPath(path+".json")
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while building the path URL!");
        }
        return url;
    }

    /**
     *   This method gets the http connection and returns the entire result
     *
     *   @param url the url we want to fetch the data from
     *   @return the response result from the url or null
     *   @throws IOException related to network and stream reading
     */
    public static String getResponseFromHttpUrls(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                Log.v(TAG, "No data return from url!");
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
