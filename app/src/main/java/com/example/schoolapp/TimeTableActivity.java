package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.ClassAdapter;
import com.example.schoolapp.Adapter.TimeTableAdapter;
import com.example.schoolapp.Fragments.CreateFeeTypes;
import com.example.schoolapp.Fragments.TiameTableFragment;
import com.example.schoolapp.Model.ClassModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> className=new ArrayList<>();
    ArrayList<String> classid=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    Fragment fragment;
    FragmentTransaction fragmentTransaction1;
    ProgressDialog progressDoalog;

    ArrayList<String> myitem=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=findViewById(R.id.listview);
        progressDoalog=new ProgressDialog(this);
        myitem.add("Add Subject");
        myitem.add("Set Time Table");

        adapter=new ArrayAdapter<>(TimeTableActivity.this,android.R.layout.simple_spinner_dropdown_item,myitem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        break;
                        case 1:
                            LoadClassDetails();
                        break;

                }
            }
        });

    }


    private void LoadClassDetails() {
        //getting the progressbar
        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.classdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressDoalog.dismiss();


                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                className.clear();
                                classid.clear();
                                adapter1=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                   /* classModelList.add(new ClassModel(
                                            studentObject.getString("ID"),
                                            studentObject.getString("ClassName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));
                                   */
                                    classid.add(  studentObject.getString("ID"));
                                    className.add(  studentObject.getString("ClassName"));

                                }


                                final Dialog dialog=new Dialog(TimeTableActivity.this);
                                dialog.setContentView(R.layout.dialogclass);
                                ListView listView=dialog.findViewById(R.id.list);


                                adapter1=new ArrayAdapter<>(TimeTableActivity.this,android.R.layout.simple_spinner_dropdown_item,className);
                                listView.setAdapter(adapter1);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                Bundle bundle=new Bundle();
                                                bundle.putString("classname",className.get(position));
                                                bundle.putString("classid",classid.get(position));
                                                fragment = new TiameTableFragment();
                                                fragment.setArguments(bundle);
                                                fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                                  dialog.dismiss();


                                    }
                                });
                                dialog.show();
                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(TimeTableActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(TimeTableActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
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
}
