package com.example.schoolapp.Transport.Fragment;

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
import com.example.schoolapp.Transport.Adopter.DriverAdopter;
import com.example.schoolapp.Transport.modelclass.DriverListmodelclass;
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

public class DriverList extends Fragment {
    RecyclerView recyclerview;
    List<DriverListmodelclass> driverListmodelclasses;
    FloatingActionButton floatingActionButton;
    Fragment fragment;
    ProgressDialog progressDoalog;
    DriverAdopter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_driverregistation, container, false);
        recyclerview=view.findViewById(R.id.listofVichel);
        floatingActionButton=view.findViewById(R.id.fab);
        progressDoalog=new ProgressDialog(getActivity());

        driverListmodelclasses = new ArrayList<>();
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
                fragment=new DriverRegistation();
                final Bundle bundle=new Bundle();
                bundle.putString("id",driverListmodelclasses.get(position).getID());
                bundle.putString("DriverName",driverListmodelclasses.get(position).getDriverName());
                bundle.putString("Phone",driverListmodelclasses.get(position).getPhone());
                bundle.putString("Email",driverListmodelclasses.get(position).getEmail());
                bundle.putString("Address",driverListmodelclasses.get(position).getAddress());
                bundle.putString("LicenceNo",driverListmodelclasses.get(position).getLicenceNo());
                bundle.putString("LicenceValideDate",driverListmodelclasses.get(position).getLicenceValideDate());
                bundle.putString("LicenceType",driverListmodelclasses.get(position).getLicenceType());
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
                fragment=new DriverRegistation();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetAllDriver,
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
                                driverListmodelclasses.clear();
                                customAdapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    driverListmodelclasses.add(new DriverListmodelclass(studentObject.getString("ID"),
                                            studentObject.getString("DriverName"),
                                            studentObject.getString("Email"),
                                            studentObject.getString("Phone"),
                                            studentObject.getString("Address"),
                                            studentObject.getString("ImagePath"),
                                            studentObject.getString("UpdatedBy"),
                                            studentObject.getString("CreatedDate"),
                                            studentObject.getString("CreatedBy"),
                                            studentObject.getString("UpadatedDate"),
                                            studentObject.getString("LicenceNo"),
                                            studentObject.getString("LicenceValideDate"),
                                            studentObject.getString("LicenceType")
                                    ));

                                }
                                customAdapter=new DriverAdopter(getActivity(),driverListmodelclasses);
                                recyclerview.setAdapter(customAdapter);

                            }

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
