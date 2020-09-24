package com.example.schoolapp.Transport.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.schoolapp.AboutSchool;
import com.example.schoolapp.AttendanceManagment;
import com.example.schoolapp.FeeManagmentTypes;
import com.example.schoolapp.GalleryAchievements;
import com.example.schoolapp.ParentTeacherCommunication;
import com.example.schoolapp.R;

import com.example.schoolapp.RecyclerTouchListener;
import com.example.schoolapp.SchoolDashBoard;
import com.example.schoolapp.StaffManagment;
import com.example.schoolapp.StudentAdmissionMenu;
import com.example.schoolapp.StudentList;
import com.example.schoolapp.TestActivity;
import com.example.schoolapp.TimeTableActivity;
import com.example.schoolapp.Transport.Adopter.ListofVachileAdopter;
import com.example.schoolapp.Transport.modelclass.VehicleListModelClass;
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


public class ListofVichaile_fragment extends Fragment {
    RecyclerView recyclerview;
    List<VehicleListModelClass> VehicleListModelClass;
    FloatingActionButton floatingActionButton;
    Fragment fragment;
    ProgressDialog progressDoalog;
    ListofVachileAdopter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_listof_vichaile_fragment, container, false);
        recyclerview=view.findViewById(R.id.listofVichel);
        floatingActionButton=view.findViewById(R.id.fab);
        progressDoalog=new ProgressDialog(getActivity());
        VehicleListModelClass = new ArrayList<>();



     //   UserName: Vikas01 EmailId: Stylewouldindia@gmail.com Licence Key: c20800cedadd2b84a7b81b9db577c9a7
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
                fragment=new VeheclesRegistation();
               final Bundle bundle=new Bundle();
                bundle.putString("id",VehicleListModelClass.get(position).getID());
                bundle.putString("VehicleTypeId",VehicleListModelClass.get(position).getVehicleTypeId());
                bundle.putString("VehicleNo",VehicleListModelClass.get(position).getVehicleNo());
                bundle.putString("RcNo",VehicleListModelClass.get(position).getRcNo());
                bundle.putString("WonerName",VehicleListModelClass.get(position).getWonerName());
                bundle.putString("WonerAddress",VehicleListModelClass.get(position).getWonerAddress());
                bundle.putString("PhoneNo",VehicleListModelClass.get(position).getPhoneNo());
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
                fragment=new VeheclesRegistation();
                assert getFragmentManager() != null;
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetVechale,
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
//                            //now looping through all the elements of the json array
                                VehicleListModelClass.clear();
                                customAdapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    VehicleListModelClass.add(new VehicleListModelClass (studentObject.getString("ID"),
                                            studentObject.getString("VehicleTypeId"),
                                            studentObject.getString("VehicleNo"),
                                            studentObject.getString("RcNo"),
                                            studentObject.getString("WonerName"),
                                            studentObject.getString("WonerAddress"),
                                            studentObject.getString("PhoneNo")));
                                }
                                customAdapter=new ListofVachileAdopter(getActivity(),VehicleListModelClass);
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
