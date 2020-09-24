package com.example.schoolapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.schoolapp.Model.SchoolDashModel;
import com.example.schoolapp.Model.StudentUpdatedetails;
import com.example.schoolapp.R;
import com.example.schoolapp.StudentAdmissionMenu;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFragment extends Fragment implements ClassAdapter.UpdateClass {
   View view;
   EditText Class;
   Button add;
   RecyclerView recyclerview;
  ProgressDialog progressDoalog;

    List<ClassModel> classModelList;
    ClassAdapter adapter;
    ClassAdapter.UpdateClass mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class, container, false);
        Class=view.findViewById(R.id.Class);

        add=view.findViewById(R.id.add);
        recyclerview=view.findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        progressDoalog=new ProgressDialog(getActivity());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateClass("0",Class.getText().toString(),"true","False");
            }
        });


        mListener=this;
        classModelList=new ArrayList<>();
        LoadClassDetails();

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
                                classModelList.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    classModelList.add(new ClassModel(
                                            studentObject.getString("ID"),
                                            studentObject.getString("ClassName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));


                                }

                                adapter=new ClassAdapter(classModelList,getActivity(),mListener);
                                recyclerview.setAdapter(adapter);

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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void registerClass(final String ID, final String ClassName, final String IsActive, final String IsDeleted) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.createfeetypedailog);
        final EditText Class;
        final TextView IDd;
        final CheckBox IsActived,IsDeletedd;
        Button updated;
        Class=dialog.findViewById(R.id.FeeType1);
        IDd=dialog.findViewById(R.id.ID);

        IsActived=dialog.findViewById(R.id.IsActive);
        IsDeletedd=dialog.findViewById(R.id.IsDeleted);
        updated=dialog.findViewById(R.id.update);
        IDd.setText(ID);
        Class.setText(ClassName);
        IsActived.setChecked(Boolean.parseBoolean(IsActive));
        IsDeletedd.setChecked(Boolean.parseBoolean(IsDeleted));
        IDd.setText(ID);
        final CheckBox finalIsActived = IsActived;
        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateClass(IDd.getText().toString(),Class.getText().toString(),IsActive,IsDeleted);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void UpdateClass(final String ID1, final String ClassName1, final String IsActive1, final String IsDeleted1) {
        progressDoalog.setMessage("Class is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.classinsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            if(obj.getBoolean("IsSuccess"))
                                LoadClassDetails();


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
               Log.d("ashishsikarwar1",ID1);
                params.put("ID",ID1 );


                params.put("ClassName",ClassName1);
                params.put("IsActive",IsActive1);
                params.put("IsDeleted",IsDeleted1);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

}

