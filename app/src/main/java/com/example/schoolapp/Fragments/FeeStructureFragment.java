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
import android.view.WindowManager;
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
import com.example.schoolapp.Adapter.FeeStructureAdapter;
import com.example.schoolapp.Model.FeeStructureModel;
import com.example.schoolapp.Model.StudentUpdatedetails;
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


public class FeeStructureFragment extends Fragment implements FeeStructureAdapter.UpadatStructure {
    View view;
    RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    FeeStructureAdapter.UpadatStructure upadatStructure;
    List<FeeStructureModel> feeStructureModelList=new ArrayList<>();
    FeeStructureAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        upadatStructure=this;
        view= inflater.inflate(R.layout.fragment_fee_structure, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDoalog=new ProgressDialog(getActivity());
        LoadFeeStructureList();


    return view;
    }

    private void LoadFeeStructureList() {
        //getting the progressbar

        //making the progressbar visible

        progressDoalog.setMessage("Students Details are loading....");
        progressDoalog.setTitle("Processing..");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllclassFee,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressDoalog.dismiss();


                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                feeStructureModelList.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);


                                        feeStructureModelList.add(new FeeStructureModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("ClassId"),
                                                studentObject.getString("FeeTypeID"),
                                                studentObject.getString("SessionId"),
                                                studentObject.getString("Anually"),
                                                studentObject.getString("Course_Monthly"),
                                                studentObject.getString("Quaterly")
                                        ));


                                }
                               adapter=new FeeStructureAdapter(upadatStructure,getActivity(),feeStructureModelList);
                                recyclerView.setAdapter(adapter);

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
    public void UpdateFee(final String ID_, final String ClassId_, final String SessionId_
            , final String Anually_
            , final String Course_Monthly_
            , final String Quaterly_, final String IsActive_, final String IsDeleted_){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.feestructuredialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        final TextView ID;
                final EditText ClassId,SessionId,Anually,Course_Monthly,Quaterly;
                Button Update;
        ID=dialog.findViewById(R.id.ID);
        ClassId=dialog.findViewById(R.id.ClassId);
        SessionId=dialog.findViewById(R.id.SessionId);
        Anually=dialog.findViewById(R.id.Anually);
        Course_Monthly=dialog.findViewById(R.id.Course_Monthly);
        Quaterly=dialog.findViewById(R.id.Quaterly);
        Update=dialog.findViewById(R.id.Update);
        ID.setText(ID_);
        ClassId.setText(ClassId_);
        SessionId.setText(SessionId_);
        Anually.setText(Anually_);
        Course_Monthly.setText(Course_Monthly_);
        Quaterly.setText(Quaterly_);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStructure(
                        ID.getText().toString(),
                        ClassId.getText().toString(),
                        SessionId.getText().toString(),
                        Anually.getText().toString(),
                        Course_Monthly.getText().toString(),
                        Quaterly.getText().toString(),"true","false"
                        );
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void UpdateStructure(final String ID, final String ClassId, final String SessionId
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
