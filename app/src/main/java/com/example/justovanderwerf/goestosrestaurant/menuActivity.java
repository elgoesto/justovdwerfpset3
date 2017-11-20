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

public class menuActivity extends AppCompatActivity {

    ArrayList<String> menus;
    ListView theMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        Intent intent = getIntent();
        String catMenu = intent.getStringExtra("Cat");

        // Get the categories
        getCata(catMenu);
    }







    private void getCata(String catMenu) {
        String url = "https://resto.mprog.nl/menu?category=" + catMenu ;

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
        final ArrayList<String> catArray = new ArrayList<>();

        try {
            JSONObject jasonObject = new JSONObject(response);
            JSONArray jasonArray = jasonObject.getJSONArray("items");

            for (int i = 0; i < jasonArray.length(); i++){
                JSONObject meal = jasonArray.getJSONObject(i);
                catArray.add(meal.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        menus = catArray;
        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                menus);

        theMenuView = findViewById(R.id.theMenuView);

        theMenuView.setAdapter(menuAdapter);

        theMenuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d("positie", "onItemClick: ");


                Intent intent = new Intent(menuActivity.this, dishActivity.class);
                String chosenDish = catArray.get(position);
                intent.putExtra("dish", chosenDish);
                Log.d("DISH",chosenDish );
                startActivity(intent);
            }
        });
    }
}
