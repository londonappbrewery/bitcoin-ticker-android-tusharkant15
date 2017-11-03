package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoin ...";

    // Member Variables:
    TextView mPriceTextView;
    private String currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        setCurrency(0);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setCurrency(position);
                letsDoSomeNetworking();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setCurrency (int position) {
        currency = getResources().getStringArray(R.array.currency_array)[position];
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking() {
        String url = "https://apiv2.bitcoinaverage.com/indices/global/ticker/all?crypto=BTC&fiat=" + currency;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-testing", "testing");
        mPriceTextView.setText("Loading...");
        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    BitcoinModel bitcoinModel = BitcoinModel.fromJson(response, currency);
                    updateUI(bitcoinModel);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Unable to parse response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Toast.makeText(MainActivity.this, "Unable to make request", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateUI(BitcoinModel bitcoinModel) {
        mPriceTextView.setText(bitcoinModel.getLabel());
    }


}
