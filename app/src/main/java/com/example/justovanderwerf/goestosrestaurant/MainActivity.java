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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> categories;
    ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get the categories
        getMenu();


    }

    private void getMenu() {
        String url = "https://resto.mprog.nl/categories";

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
                Log.d("error", "geen json gevonden, restart android studio ");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void stringToArray(String response) {
        Log.d("RESP", response);
        ArrayList<String> catArray = new ArrayList<>();

        try {
            JSONObject jasonObject = new JSONObject(response);
            JSONArray jasonArray = jasonObject.getJSONArray("categories");

            for (int i = 0; i < jasonArray.length(); i++){
                catArray.add(jasonArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        categories = catArray;
        ListAdapter  theAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                categories);

        theListView = findViewById(R.id.CategoryView);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d("positie", "onItemClick: ");


                Intent intent = new Intent(MainActivity.this, menuActivity.class);
                String chosenCat = categories.get(position);
                intent.putExtra("Cat", chosenCat);
                startActivity(intent);
            }
        });
    }


}


