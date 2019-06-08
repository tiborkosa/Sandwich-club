package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = JsonUtils.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Log.d(TAG, sandwich.toString());

        TextView origin = (TextView) findViewById(R.id.origin_tv);
        // is this check better here or in the POJOs' getMethod?
        if (sandwich.getPlaceOfOrigin() == null || sandwich.getPlaceOfOrigin().equals("")) {
            origin.setText("unknown");
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        TextView getAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        String alsoKnownString = TextUtils.join("\n", sandwich.getAlsoKnownAs());
        getAlsoKnownAs.setText(alsoKnownString);

        TextView ingredients = (TextView) findViewById(R.id.ingredients_tv);
        ingredients.setText(TextUtils.join("\n", sandwich.getIngredients()));

        TextView description = (TextView) findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());
    }
}
