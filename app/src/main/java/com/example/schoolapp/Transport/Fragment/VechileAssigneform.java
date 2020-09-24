package com.example.schoolapp.Transport.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VechileAssigneform extends Fragment {
Button regisration;
Spinner vehiclenumber,drivername;
    ProgressDialog progressDoalog;
    private ArrayList<String> vechile;
    private ArrayList<String> Driverlist;
    private ArrayList<String> ids;
    private ArrayList<String> drids;
    String VehicleTypeid="1";
    String Driverid="1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_vechile_assigneform, container, false);
        vehiclenumber=view.findViewById(R.id.VehicleNumber);
        drivername=view.findViewById(R.id.DriverName);
        regisration=view.findViewById(R.id.registation);
        vechile=new ArrayList<String>();
        Driverlist=new ArrayList<String>();
        ids=new ArrayList<String>();
        drids=new ArrayList<String>();
        progressDoalog=new ProgressDialog(getActivity());

        getvechiletype();
        getDrivername();
        regisration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postData();

            }
        });
        return view;
    }

    private void getDrivername() {

        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetAllDriver,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDoalog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            Log.d("Anil",response);
                            if(obj.getBoolean("IsSuccess")) {
                                progressDoalog.dismiss();
                                JSONArray stuarray = obj.getJSONArray("ResponseData");
                                for (int i = 0; i < stuarray.length(); i++) {
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Driverlist.add(studentObject.getString("DriverName"));
                                    drids.add(studentObject.getString("ID"));

                                }
                            }
                            drivername.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Driverlist));
                            //setspinner id and vechileid
                            int spincount=0;
                            for(int i=0;i<drids.size();i++){
                                if(drids.get(i).equals(Driverid))
                                    break;
                                spincount=spincount+1;
                            }
                            try{
                                if(drids.size()>spincount)
                                    drivername.setSelection(spincount);
                                else{
                                    spincount=0;
                                    drivername.setSelection(spincount);
                                }
                            }
                            catch (Exception e){
                                Log.d("ashishsikarwar",e+"");
                                drivername.setSelection(0);
                            }
                            drivername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Driverid= drids.get(position); }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) { }
                            });

                        } catch (Exception e) { e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) ;
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void getvechiletype() {

        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetVechale,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDoalog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("Anil",response);
                            if(obj.getBoolean("IsSuccess")) {
                                JSONArray stuarray = obj.getJSONArray("ResponseData");
                                for (int i = 0; i < stuarray.length(); i++) {

                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    vechile.add(studentObject.getString("VehicleNo"));
                                    ids.add(studentObject.getString("ID"));

                                }
                            }
                            vehiclenumber.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vechile));
                            //setspinner id and vechileid
                            int spincount=0;
                            for(int i=0;i<ids.size();i++){
                                if(ids.get(i).equals(VehicleTypeid))
                                    break;
                                spincount=spincount+1;
                            }
                            try{
                                if(ids.size()>spincount)
                                    vehiclenumber.setSelection(spincount);
                                else{
                                    spincount=0;
                                    vehiclenumber.setSelection(spincount);
                                }
                            }
                            catch (Exception e){
                                Log.d("ashishsikarwar",e+"");
                                vehiclenumber.setSelection(0);
                            }
                            vehiclenumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    VehicleTypeid= ids.get(position); }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) { }
                            });

                        } catch (Exception e) { e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) ;
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    public void postData() {
        progressDoalog=new ProgressDialog(getActivity());
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_InsertDriverVehicleassoDetails,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDoalog.dismiss();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
              //  params.put("Id",id);

                return params;
            }
        } ;
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



}
