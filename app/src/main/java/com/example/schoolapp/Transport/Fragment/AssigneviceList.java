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
import com.example.schoolapp.Transport.Adopter.Driverassigneadopter;
import com.example.schoolapp.Transport.modelclass.Modeldriverassigne;
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


public class AssigneviceList extends Fragment {
    RecyclerView recyclerview;
    List<Modeldriverassigne> modeldriverassigne;
    FloatingActionButton floatingActionButton;
    Fragment fragment;
    ProgressDialog progressDoalog;
    Driverassigneadopter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_driver_assignevicel, container, false);
             recyclerview=view.findViewById(R.id.listofVichel);
        floatingActionButton=view.findViewById(R.id.fab);
        progressDoalog=new ProgressDialog(getActivity());

        modeldriverassigne = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        registerClass();
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                fragment=new VechileAssigneform();
                final Bundle bundle=new Bundle();
//                bundle.putString("id",modeldriverassigne.get(position).getID());
//                bundle.putString("DriverName",modeldriverassigne.get(position).getDriverName());
//                bundle.putString("Phone",modeldriverassigne.get(position).getPhone());
//                bundle.putString("Email",modeldriverassigne.get(position).getEmail());
//                bundle.putString("Address",modeldriverassigne.get(position).getAddress());
//                bundle.putString("LicenceNo",modeldriverassigne.get(position).getLicenceNo());
//                bundle.putString("LicenceValideDate",modeldriverassigne.get(position).getLicenceValideDate());
//                bundle.putString("LicenceType",modeldriverassigne.get(position).getLicenceType());
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
                fragment=new VechileAssigneform();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetDriverVehicle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDoalog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {
                                JSONArray stuarray = obj.getJSONArray("ResponseData");
                                modeldriverassigne.clear();
                                customAdapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    modeldriverassigne.add(new Modeldriverassigne(studentObject.getString("ID"),
                                            studentObject.getString("VehicleID"),
                                            studentObject.getString("DriverID"),
                                            studentObject.getString("Isactive"),
                                            studentObject.getString("CreatedOn"),
                                            studentObject.getString("Createdby"),
                                            studentObject.getString("UpdatedOn"),
                                            studentObject.getString("UpdatedBy")
                                    ));
                                }
                                customAdapter=new Driverassigneadopter(getActivity(),modeldriverassigne);
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
