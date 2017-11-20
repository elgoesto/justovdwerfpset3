package com.example.justovanderwerf.goestosrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class dishActivity extends AppCompatActivity {

    TextView dishTextView;
    TextView dishPriceView;
    String dishName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        dishTextView = findViewById(R.id.dishView);
        dishPriceView = findViewById(R.id.dishPrice);

        Intent intent = getIntent();
        dishName = intent.getStringExtra("dish");




        // Get the categories
        getCata();
    }







    private void getCata() {
        String url = "https://resto.mprog.nl/menu";

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        stringToArray(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NF", "onErrorResponse: geen jason gedoe gevonden");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void stringToArray(String response) {
        String catArray = "";
        String dishPrice = "";

        try {
            JSONObject jasonObject = new JSONObject(response);
            JSONArray jasonArray = jasonObject.getJSONArray("items");

            for (int i = 0; i < jasonArray.length(); i++){
                JSONObject meal = jasonArray.getJSONObject(i);
                if (meal.getString("name").equals(dishName)) {
                    catArray = meal.getString("description");
                    dishPrice = meal.getString("price");
                    Log.d("DESCRIPTION", dishPrice);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        dishTextView.setText(catArray);
        dishPriceView.setText("Price: $ " + dishPrice);
    }
}
