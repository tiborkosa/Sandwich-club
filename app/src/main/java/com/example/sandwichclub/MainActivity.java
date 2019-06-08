package com.example.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandwichclub.model.Sandwiches;
import com.example.sandwichclub.utils.JsonUtil;
import com.example.sandwichclub.utils.NetworkUtils;

import com.example.sandwichclub.utils.config;

import java.io.IOException;
import java.net.URL;

/**
 * the main landing page where we show all the sandwiches
 */
public class MainActivity extends AppCompatActivity implements SandwichListAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    ProgressBar mProgressBar;
    TextView mError;
    private Sandwiches sandwiches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.pb_main);
        mError = findViewById(R.id.tv_error);

        URL url = NetworkUtils.buildPathUrl(config.NAMES);
        new SandwichDataTask().execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mi_reset){
            Context ctx = MainActivity.this;
            Toast.makeText(ctx,"Selected this", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The listener class of the list items
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Context ctx = MainActivity.this;

        Intent intent = new Intent(MainActivity.this, SandwichActivity.class);
        intent.putExtra(SandwichActivity.SANDWICH_NAME, sandwiches.getOne(clickedItemIndex));
        intent.putExtra(SandwichActivity.SANDWICH_POSITION, String.valueOf(clickedItemIndex) );

        ctx.startActivity(intent);
    }

    /**
     * reaching out to get the sandwich data
     */
    public class SandwichDataTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mError.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressBar.setVisibility(View.INVISIBLE);

            if(s == null || s.equals("")) {
                mError.setVisibility(View.VISIBLE);
                mError.setText("Could not load data!");
                Log.e(TAG, "String is empty");
                return;
            }

            sandwiches = JsonUtil.parseSandwiches(s);
            RecyclerView rv = findViewById(R.id.recycler_view);
            SandwichListAdapter adapter = new SandwichListAdapter(sandwiches.getSandwiches(), MainActivity.this);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Log.d(TAG, sandwiches.toString());
        }

        @Override
        protected String doInBackground(URL... urls) {
            String result = null;
            URL url = urls[0];
            try {
                result = NetworkUtils.getResponseFromHttpUrls(url);
            } catch (IOException e){
                Log.e(TAG, "Could not load data");
            }
            return result;
        }

    }
}
