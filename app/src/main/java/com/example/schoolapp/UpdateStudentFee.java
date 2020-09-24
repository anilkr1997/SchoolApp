package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.schoolapp.Adapter.CreateFeeTypeAdapter;
import com.example.schoolapp.Fragments.DepositeStuFee;
import com.example.schoolapp.Fragments.SectionFragment;
import com.example.schoolapp.Model.CreateFeeTypeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateStudentFee extends AppCompatActivity {
    ProgressDialog progressDoalog;

    List<CreateFeeTypeModel> createFeeTypeModelList;
    ListView list;
    ArrayAdapter<String> adapter;
    ArrayList<String> listoffeetype;
    ArrayList<String> listoffeeid;
    Fragment fragment;
    FragmentTransaction fragmentTransaction1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_fee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list=findViewById(R.id.list);
        createFeeTypeModelList=new ArrayList<>();
        listoffeetype=new ArrayList<>();
        listoffeeid=new ArrayList<>();

        progressDoalog=new ProgressDialog(this);
        LoadFeeTypeDetails();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragment=new DepositeStuFee();
                Bundle bundle = new Bundle();
                bundle.putString("feetype",listoffeetype.get(position)  );
                bundle.putString("feeid", listoffeeid.get(position));
                fragment.setArguments(bundle);
                fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void LoadFeeTypeDetails() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.getfeetype,
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
                                adapter=null;
                                listoffeeid.clear();
                                listoffeetype.clear();
                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                createFeeTypeModelList.clear();

                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    try {
                                        Log.d("ashishsikarwar", studentObject.getString("Id"));
                                        Log.d("ashishsikarwar", studentObject.getString("FeeType1"));
                                        Log.d("ashishsikarwar", "" + studentObject.getString("IsActive"));
                                        Log.d("ashishsikarwar", "" + studentObject.getString("IsDeleted"));
                                        createFeeTypeModelList.add(new CreateFeeTypeModel(
                                                studentObject.getString("Id"),
                                                studentObject.getString("FeeType1"),
                                                studentObject.getBoolean("IsActive"),
                                                studentObject.getBoolean("IsDeleted")));
                                        listoffeetype.add( studentObject.getString("FeeType1"))  ;
                                        listoffeeid.add( studentObject.getString("Id"))  ;
                                    }
                                    catch (Exception e){
                                        createFeeTypeModelList.add(new CreateFeeTypeModel(
                                                studentObject.getString("Id"),
                                                "null",
                                                true,
                                                false));
                                    }

                                }
                               adapter=new ArrayAdapter<>(UpdateStudentFee.this,android.R.layout.simple_list_item_1,listoffeetype);
                                list.setAdapter(adapter);


                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            Log.d("ashishsikarwar", ""+e);

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(UpdateStudentFee.this, "Your Internet Connection is Failed ", Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateStudentFee.this);

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
