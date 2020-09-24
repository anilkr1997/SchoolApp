package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.onlytest.NewsAdapter;
import com.example.schoolapp.onlytest.NewsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    List<NewsModel> newsModelList=new ArrayList<>();
    NewsAdapter adapter;
    RecyclerView recyclerview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview1=findViewById(R.id.recyclerview1);
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        LoadClassDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void LoadClassDetails() {
        //getting the progressbar

        //making the progressbar visible

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.newsurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion



                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);


                                JSONArray stuarray = obj.getJSONArray("articles");

//
//                            //now looping through all the elements of the json array

                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                  //  Log.d("ashishsikarwar", studentObject.getString("ID"));


                                    newsModelList.add(new NewsModel(
                                            studentObject.getString("title"),
                                            studentObject.getString("content") ,
                                            studentObject.getString("urlToImage") ));






                                //  UpadateFee();
                            }
                            adapter=new NewsAdapter(newsModelList,TestActivity.this);
                            recyclerview1.setAdapter(adapter);


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //displaying the error in toast if occurrs

                        Toast.makeText(TestActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
