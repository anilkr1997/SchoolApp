package com.example.schoolapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.schoolapp.Adapter.AddStaffAdapter;
import com.example.schoolapp.Adapter.SessionAdapter;
import com.example.schoolapp.Model.AddStaffModel;
import com.example.schoolapp.Model.SessionModel;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddStaffProfileFrag extends Fragment implements AddStaffAdapter.updateStaff {

    RecyclerView recyclerview;
    View view;
   FloatingActionButton floatingActionButton;
   ProgressDialog progressDoalog;
   List<AddStaffModel> addStaffModelList=new ArrayList<>();
   AddStaffAdapter adapter=null;
   AddStaffAdapter.updateStaff updateStaff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_staff_profile, container, false);
        updateStaff=this;
        progressDoalog=new ProgressDialog(getActivity());
        recyclerview=view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        floatingActionButton=view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog();
            }
        });
        LoadStaffProfile();
   return view;
    }

    @Override
    public void UpdateStaff(AddStaffModel addStaffModel) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.createfeetypedailog);
        final EditText profile;
        final TextView IDd;
        final CheckBox IsActived,IsDeletedd;
        Button updated;
        profile=dialog.findViewById(R.id.FeeType1);
        IDd=dialog.findViewById(R.id.ID);
        IsActived=dialog.findViewById(R.id.IsActive);
        IsDeletedd=dialog.findViewById(R.id.IsDeleted);
        updated=dialog.findViewById(R.id.update);

        IDd.setText(addStaffModel.getID());
        profile.setText(addStaffModel.getStaffProfile());
        IsActived.setChecked(Boolean.parseBoolean(addStaffModel.getIsActive()));
        IsDeletedd.setChecked(Boolean.parseBoolean(addStaffModel.getIsDeleted()));


        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProfile(IDd.getText().toString(),profile.getText().toString(),"true","false");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void OpenDialog(){

        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.createfeetypedailog);
        final EditText profile;
        final TextView IDd;
        final CheckBox IsActived,IsDeletedd;
        Button updated = null;
        profile=dialog.findViewById(R.id.FeeType1);
        IDd=dialog.findViewById(R.id.ID);

        IsActived=dialog.findViewById(R.id.IsActive);
        IsDeletedd=dialog.findViewById(R.id.IsDeleted);
        IsActived.setVisibility(View.GONE);
        IsDeletedd.setVisibility(View.GONE);
        updated=dialog.findViewById(R.id.update);

        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProfile("0",profile.getText().toString(),"true","false");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void AddProfile(final String ID1, final String Session, final String IsActive1, final String IsDeleted1) {
        progressDoalog.setMessage("Session is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertEmployeeTypedetails,
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
                                LoadStaffProfile();


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

                params.put("ID",ID1 );


                params.put("EmploeeTypeName",Session);
                params.put("IsActive",IsActive1);
                params.put("IsDeleted",IsDeleted1);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
    private void LoadStaffProfile() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllEmployeeType,
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
                                addStaffModelList.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    addStaffModelList.add(new AddStaffModel(studentObject.getString("ID"),
                                            studentObject.getString("EmploeeTypeName"),
                                            studentObject.getString("IsActive"),
                                            studentObject.getString("IsDeleted")

                                    ));
                                    Log.d("ashishsikarwar", studentObject.getString("ID"));


                                }
                                adapter=new AddStaffAdapter(addStaffModelList,getActivity(),updateStaff);
                                recyclerview.setAdapter(adapter);
                                Log.d("ashishsikarwar", obj.getBoolean("IsSuccess")+"");
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
}
