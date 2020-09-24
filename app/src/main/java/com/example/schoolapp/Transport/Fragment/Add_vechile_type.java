package com.example.schoolapp.Transport.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.Transport.Adopter.vechileTypeAdopter;
import com.example.schoolapp.Transport.modelclass.VechiltypeModelclass;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Add_vechile_type extends Fragment implements vechileTypeAdopter.UpdateVcheltype  {
    View view;
    EditText Addtype;
    Button add;
    RecyclerView recyclerview;
    ProgressDialog progressDoalog;
    List<VechiltypeModelclass> Vechiltype;
    vechileTypeAdopter adapter;
    vechileTypeAdopter.UpdateVcheltype updateVcheltype;
    String Vehicalname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add_vechile_type, container, false);
        Addtype=view.findViewById(R.id.vechiletype);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicalname=Addtype.getText().toString();
                UpdateClass("0",Vehicalname,"true","False");

            }
        });
        recyclerview=view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        progressDoalog=new ProgressDialog(getActivity());
        Vechiltype=new ArrayList<>();
        LoadClassDetails();
        updateVcheltype=this;
        return view;
    }
    private void LoadClassDetails() {
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_getVechaletype,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDoalog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("Anil",response);
                            if(obj.getBoolean("IsSuccess")) {
                                JSONArray stuarray = obj.getJSONArray("ResponseData");
                                Vechiltype.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    Vechiltype.add(new VechiltypeModelclass(
                                            studentObject.getString("ID"),
                                            studentObject.getString("VehicalTypename"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));
                                }
                                adapter=new vechileTypeAdopter(getActivity(),Vechiltype,updateVcheltype);
                                recyclerview.setAdapter(adapter);
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        updateVcheltype = null;
    }
    @Override
    public void registerClass(final String ID,final String VehicalTypename,final String IsActive, final String IsDeleted) {
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
        Class.setText(VehicalTypename);
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
    private void UpdateClass(final String ID1,final String VehicalTypename, final String IsActive,final String IsDeleted) {
        progressDoalog.setMessage("Class is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_InsertVechaletype,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDoalog.dismiss();
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
                params.put("VehicalTypename",VehicalTypename);
                params.put("IsActive",IsActive);
                params.put("IsDeleted",IsDeleted);
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    }

