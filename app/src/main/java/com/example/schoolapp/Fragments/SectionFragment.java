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
import com.example.schoolapp.Adapter.HouseAdapter;
import com.example.schoolapp.Adapter.SectionAdapter;
import com.example.schoolapp.Model.HouseModel;
import com.example.schoolapp.Model.SectionModel;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SectionFragment extends Fragment implements SectionAdapter.UpdateSection {
     View view;
    EditText Class;
    Button add;
    RecyclerView recyclerview;
    ProgressDialog progressDoalog;
    SectionAdapter adapter;
    SectionAdapter.UpdateSection updateSection;
    List<SectionModel> sectionModellist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_section, container, false);
        Class=view.findViewById(R.id.Class);

        add=view.findViewById(R.id.add);
        recyclerview=view.findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        progressDoalog=new ProgressDialog(getActivity());
        updateSection=this;
        sectionModellist=new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSection("0",Class.getText().toString(),"true","false");
            }
        });


        LoadSectionDetails();
   return view;
    }



    private void LoadSectionDetails() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllSection,
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
                            //    Toast.makeText(getActivity(),""+obj.getBoolean("IsSuccess"),Toast.LENGTH_SHORT).show();
                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                sectionModellist.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("Id"));
                                    sectionModellist.add(new SectionModel(
                                            studentObject.getString("Id"),
                                            studentObject.getString("SectionName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));


                                }


                                adapter=new SectionAdapter(sectionModellist,getActivity(),updateSection);
                                recyclerview.setAdapter(adapter);

                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),""+"error",Toast.LENGTH_SHORT).show();

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
    public void registerSection(final String ID, final String SectionName, final String IsActive, final String IsDeleted) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.createfeetypedailog);
        final EditText section;
        final TextView IDd;
        final CheckBox IsActived,IsDeletedd;
        Button updated;
        section=dialog.findViewById(R.id.FeeType1);
        IDd=dialog.findViewById(R.id.ID);

        IsActived=dialog.findViewById(R.id.IsActive);
        IsDeletedd=dialog.findViewById(R.id.IsDeleted);
        updated=dialog.findViewById(R.id.update);
        IDd.setText(ID);
        section.setText(SectionName);
        IsActived.setChecked(Boolean.parseBoolean(IsActive));
        IsDeletedd.setChecked(Boolean.parseBoolean(IsDeleted));
        IDd.setText(ID);
        final CheckBox finalIsActived = IsActived;
        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSection(IDd.getText().toString(),section.getText().toString(),IsActive,IsDeleted);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void AddSection(final String ID, final String SectionName, final String IsActive, final String IsDeleted){
        progressDoalog.setMessage("Section is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertSectionDetails,
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
                                LoadSectionDetails();

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
                Log.d("ashishsikarwar1",ID);
                Map<String, String> params = new HashMap<>();

                params.put("ID",ID );


                params.put("SectionName",SectionName);
                params.put("IsActive",IsActive);
                params.put("IsDeleted",IsDeleted);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
}
