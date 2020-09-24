package com.example.schoolapp.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.schoolapp.Adapter.ClassAdapter;
import com.example.schoolapp.Adapter.CreateFeeTypeAdapter;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.Model.CreateFeeTypeModel;
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


public class CreateFeeTypes extends Fragment implements CreateFeeTypeAdapter.UpdateFeeType {

    View view;
    RecyclerView recyclerview;
    FloatingActionButton floatingActionButton;

    ProgressDialog progressDoalog;

    List<CreateFeeTypeModel> createFeeTypeModelList;
    CreateFeeTypeAdapter adapter;
    CreateFeeTypeAdapter.UpdateFeeType mListener;

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle","onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause");
    }

    @Override
    public void onStop() {
        Log.d("lifecycle","onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("lifecycle","onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("lifecycle","onDestroyView");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        Log.d("lifecycle","onDestroyOptionsMenu");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("lifecycle","onAttach");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_create_fee_types, container, false);
        Log.d("lifecycle","onCreateView");
        floatingActionButton=view.findViewById(R.id.fab);
        recyclerview=view.findViewById(R.id.recyclerview);


        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFeetype("0","","false","false");
            }
        });


        createFeeTypeModelList=new ArrayList<>();
        mListener=this;
        progressDoalog=new ProgressDialog(getActivity());
        LoadFeeTypeDetails();
       return view;
    }


    private void LoadFeeTypeDetails() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.getfeetype,
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
                                createFeeTypeModelList.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    try {
                                        Log.d("ashishsikarwar", studentObject.getString("Id"));
                                        Log.d("ashishsikarwar", studentObject.getString("FeeType1"));
                                        Log.d("ashishsikarwar", "" + studentObject.getString("IsActive"));
                                        Log.d("ashishsikarwar", "" + studentObject.getString("IsDeleted"));
                                        createFeeTypeModelList.add(new CreateFeeTypeModel(
                                                studentObject.getString("Id"),
                                                studentObject.getString("FeeType1"),
                                                studentObject.getBoolean("IsActive"),
                                                studentObject.getBoolean("IsDeleted")));
                                    }
                                    catch (Exception e){
                                        createFeeTypeModelList.add(new CreateFeeTypeModel(
                                                studentObject.getString("Id"),
                                                "null",
                                               true,
                                                false));
                                    }

                                }

                                adapter=new CreateFeeTypeAdapter(createFeeTypeModelList,getActivity(),mListener);
                                recyclerview.setAdapter(adapter);

                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            Log.d("ashishsikarwar", ""+e);
                            mListener1.onbackPressed();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), "Your Internet Connection is Failed ", Toast.LENGTH_SHORT).show();
                        mListener1.onbackPressed();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    @Override
    public void registerFeetype(final String ID, String FeeType1, String IsActive, String IsDeleted) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.createfeetypedailog);
        final EditText FeeType1d;
        TextView IDd;
       final CheckBox IsActived,IsDeletedd;
        Button updated;
        FeeType1d=dialog.findViewById(R.id.FeeType1);
        IDd=dialog.findViewById(R.id.ID);

        IsActived=dialog.findViewById(R.id.IsActive);
        IsDeletedd=dialog.findViewById(R.id.IsDeleted);
        updated=dialog.findViewById(R.id.update);
        IDd.setText(ID);
        FeeType1d.setText(FeeType1);
        IsActived.setChecked(Boolean.parseBoolean(IsActive));
        IsDeletedd.setChecked(Boolean.parseBoolean(IsDeleted));
        IDd.setText(ID);
        final CheckBox finalIsActived = IsActived;
        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Addfeetype(dialog,ID,FeeType1d.getText().toString(), IsActived.isChecked()+"",
                        IsDeletedd.isChecked()+"");
            }
        });
        dialog.show();


    }


    public void Addfeetype(final Dialog dialog, final String ID, final String FeeType1, final String IsActive, final String IsDeleted){
        progressDoalog.setMessage("Fee Type is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertFeeTypeDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);
                            if(obj.getBoolean("IsSuccess"))
                                dialog.dismiss();
                                LoadFeeTypeDetails();
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
                params.put("FeeType",FeeType1);
                params.put("IsActive",IsActive);
                params.put("IsDeleted",IsDeleted);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    OnFragmentInteractionListener mListener1;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("lifecycle","onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener1= (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("lifecycle","onDetach");
        mListener1 = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onbackPressed();
    }
}
