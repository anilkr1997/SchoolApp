package com.example.schoolapp.aboutschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.schoolapp.R;
import com.example.schoolapp.RecyclerTouchListener;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;
import com.example.schoolapp.interfaces.ClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Schoollist extends Fragment {
    RecyclerView recyclerview;
    List<Schoolmodelclass> schoolmodelclasses;
    FloatingActionButton floatingActionButton;
    Fragment fragment;
    ProgressDialog progressDoalog;
    SchoolListAdopter customAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_schoollist, container, false);
        recyclerview=view.findViewById(R.id.listofVichel);
        floatingActionButton=view.findViewById(R.id.fab);
        progressDoalog=new ProgressDialog(getActivity());
        schoolmodelclasses = new ArrayList<>();

        //  customAdapter = new ListofVachileAdopter(getActivity(), list);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        // recyclerview.setAdapter(customAdapter);
        registerClass();
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                fragment=new AddSchool();
                final Bundle bundle=new Bundle();
                bundle.putString("ID",schoolmodelclasses.get(position).getID());
                bundle.putString("SchoolName",schoolmodelclasses.get(position).getSchoolName());
                bundle.putString("Address",schoolmodelclasses.get(position).getAddress());
                bundle.putString("OwnerName",schoolmodelclasses.get(position).getOwnerName());
                bundle.putString("PrincipalName",schoolmodelclasses.get(position).getPrincipalName());
                bundle.putString("AboutSchool",schoolmodelclasses.get(position).getAboutSchool());
                bundle.putString("PhoneNo",schoolmodelclasses.get(position).getPhoneNo());
                bundle.putString("Email",schoolmodelclasses.get(position).getEmail());
                bundle.putString("BoardID",schoolmodelclasses.get(position).getBoardID());
                bundle.putString("websiteUrl",schoolmodelclasses.get(position).getWebsiteUrl());
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new AddSchool();
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
    public void registerClass() {
        progressDoalog.setMessage("Vehicle List is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetAllSchool,
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

                                //     Toast.makeText(getActivity(), ""+stuarray, Toast.LENGTH_SHORT).show();
//                            //now looping through all the elements of the json array
                                schoolmodelclasses.clear();
                                customAdapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    schoolmodelclasses.add(new Schoolmodelclass(
                                            studentObject.getString("ID"),
                                            studentObject.getString("SchoolName"),
                                            studentObject.getString("Address"),
                                            studentObject.getString("OwnerName"),
                                            studentObject.getString("PrincipalName"),
                                            studentObject.getString("AboutSchool"),
                                            studentObject.getString("ImagePath"),
                                            studentObject.getString("PhoneNo"),
                                            studentObject.getString("Email"),
                                            studentObject.getString("BoardID"),
                                            studentObject.getString("websiteUrl")
                                    ));
                                }
                                customAdapter=new SchoolListAdopter(getActivity(),schoolmodelclasses);
                                recyclerview.setAdapter(customAdapter);

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
                        Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

}
