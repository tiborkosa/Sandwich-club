package com.example.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sandwichclub.model.Sandwich;
import com.example.sandwichclub.utils.JsonUtil;
import com.example.sandwichclub.utils.NetworkUtils;
import com.example.sandwichclub.utils.config;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

/**
 * Sandwich activity for displaying the sandwich data
 */
public class SandwichActivity extends AppCompatActivity {

    private static final String TAG = SandwichActivity.class.getSimpleName();

    public final static String SANDWICH_POSITION = "sandwich_position";
    public final static String SANDWICH_NAME = "sandwich_name";
    private ImageView mImage;
    private TextView mAlsoKnowAs,
        mDescription,
        mIngredients,
        mPlaceOfOrigin;
   // ProgressBar mProgressBar;

    Context ctx = SandwichActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);

        mImage = findViewById(R.id.iv_sandwich);
        mAlsoKnowAs = findViewById(R.id.tv_known_as);
        mDescription = findViewById(R.id.tv_description);
        mIngredients = findViewById(R.id.tv_ingredients);
        mPlaceOfOrigin = findViewById(R.id.tv_place_of_origin);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(SANDWICH_NAME));

        URL sandwichUrl = NetworkUtils.buildPathUrl(config.SANDWICHES+"/"+ intent.getStringExtra(SANDWICH_POSITION));

        new SingleSandwichData().execute(sandwichUrl);
    }

    /**
     * Background task for getting the sandwich data
     */
    public class SingleSandwichData extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // do the loading or something here
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // hide the loading or something
            // populate the fields
            Sandwich sandwich = JsonUtil.parseSandwich(s);
            Log.d(TAG, s);
            Picasso.with(ctx)
                    .load(sandwich.getImage())
                    .into(mImage);

            populateFields(sandwich);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String sandwich = null;

            try {
                sandwich = NetworkUtils.getResponseFromHttpUrls(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Could not load the sandwich data!");
            }
            return sandwich;
        }
    }

    /**
     * After getting the data from the back end we populate the necessary fields
     * @param sandwich
     */
    private void populateFields(Sandwich sandwich) {
        String also = "N/A";
        if(sandwich.getAlsoKnownAs() != null) {
            also = TextUtils.join("\n",sandwich.getAlsoKnownAs());
        }
        mAlsoKnowAs.setText(also);

        String ing = "";
        if(sandwich.getIngredients() != null){
            ing = TextUtils.join("\n", sandwich.getIngredients());
        }
        mDescription.setText(sandwich.getDescription());
        mIngredients.setText(ing);
        String poo = sandwich.getPlaceOfOrigin();
        if(poo == null || poo.equals("")){
            poo = "unknown";
        }
        mPlaceOfOrigin.setText(poo);
    }

}
