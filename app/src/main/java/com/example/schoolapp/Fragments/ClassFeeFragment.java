package com.example.schoolapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.ClassAdapter;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassFeeFragment extends Fragment {
  ProgressDialog progressDoalog;
  View view;
   Spinner spinner,spinnersession;
   ArrayAdapter<String> adapter,adapter2;
   ArrayList<String> list,listid;
   ArrayList<String> list2;
   ArrayList<String> sessionids;
  Button submit;
  String ClassId,SessionId;
  EditText annual,month,quaterly;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressDoalog=new ProgressDialog(getActivity());
        view= inflater.inflate(R.layout.fragment_class_fee, container, false);
        spinner=view.findViewById(R.id.spinner);
        submit=view.findViewById(R.id.submit);
        annual=view.findViewById(R.id.annual);
        month=view.findViewById(R.id.month);
        quaterly=view.findViewById(R.id.quaterly);
        spinnersession=view.findViewById(R.id.spinnersession);
        list=new ArrayList<>();
        listid=new ArrayList<>();
        list2=new ArrayList<>();

        sessionids=new ArrayList<>();
        LoadClassDetails();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                InsertClassFeeDetails("0",ClassId,SessionId,annual.getText().toString(),month.getText().toString(),quaterly.getText().toString(),"true","false");
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClassId=listid.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnersession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SessionId= sessionids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

   return view;
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
                                listid.clear();
                                list.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    list.add(studentObject.getString("ClassName"));
                                    listid.add(studentObject.getString("ID"));
                                   /* classModelList.add(new ClassModel(
                                            studentObject.getString("ID"),
                                            studentObject.getString("ClassName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));*/


                                }

                                adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list);
                                spinner.setAdapter(adapter);
                                LoadSessionDetails();
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
                        Toast.makeText(getActivity(),"Try Again Later"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    private void LoadSessionDetails() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllSession,
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
                                list2.clear();
                                sessionids.clear();
                                adapter2=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("Id"));

                                    list2.add(studentObject.getString("SessionName"));
                                    sessionids.add(studentObject.getString("Id"));
                                   /* classModelList.add(new ClassModel(
                                            studentObject.getString("ID"),
                                            studentObject.getString("ClassName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));*/


                                }

                                adapter2=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list2);
                                spinnersession.setAdapter(adapter2);
                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            Log.d("ashishsikarwar",e+""+response);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(),"Try Again Later"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



    public void InsertClassFeeDetails(final String ID, final String ClassId, final String SessionId
            , final String Anually
            , final String Course_Monthly
            , final String Quaterly, final String IsActive, final String IsDeleted){
        progressDoalog.setMessage("Fee Type is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertClassFeeDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);
                            if(obj.getBoolean("IsSuccess"))


                            Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID",ID );
                params.put("ClassId",ClassId);
                params.put("SessionId",SessionId);
                params.put("Anually",Anually);
                params.put("Course_Monthly",Course_Monthly);
                params.put("Quaterly",Quaterly);
                params.put("IsActive",IsActive);
                params.put("IsDeleted",IsDeleted);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



}
