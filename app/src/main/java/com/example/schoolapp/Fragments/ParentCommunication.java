package com.example.schoolapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.ParentCommunicateAdapter;
import com.example.schoolapp.Adapter.StudentListAdapter;
import com.example.schoolapp.Model.StudentListModel;
import com.example.schoolapp.R;
import com.example.schoolapp.StudentAdmission;
import com.example.schoolapp.StudentList;
import com.example.schoolapp.URLs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParentCommunication extends Fragment {


  View view;
    ParentCommunicateAdapter adapter;
    List<StudentListModel> studentLists;
    RecyclerView recyclerview;
    ProgressDialog progressDoalog;

    SearchView searchview;
    Spinner class_spin,section_spin;
    ArrayAdapter<String> classadapter=null;
    ArrayList<String > classlist=new ArrayList<>();
    ArrayList<String > classidlist=new ArrayList<>();
    String myclassid="0";

    String mysectionid="0";
    ArrayAdapter<String> sectionadapter=null;
    ArrayList<String > sectionlist=new ArrayList<>();
    ArrayList<String > sectionidlist=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_parent_communication, container, false);
        progressDoalog=new ProgressDialog(getActivity());
         recyclerview=view.findViewById(R.id.recyclerview);
        searchview=view.findViewById(R.id.searchview);
        class_spin=view.findViewById(R.id.class_spin);
        section_spin=view.findViewById(R.id.section_spin);
        studentLists=new ArrayList<>();
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        LoadClassDetails();



 return view;
    }



    private void LoadStudentList() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.studentsdetails,
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
                                studentLists.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("StudentName"));
                                    if(myclassid.equalsIgnoreCase("0") && mysectionid.equalsIgnoreCase("0")){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("Phone"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }

                                    else if(myclassid.equalsIgnoreCase( studentObject.getString("ClassId")  ) &&
                                            mysectionid.equalsIgnoreCase( "0")){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("Phone"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }

                                    else if(myclassid.equalsIgnoreCase("0"  ) &&
                                            mysectionid.equalsIgnoreCase( studentObject.getString("SectionId")  )){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("Phone"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }
                                    else if(myclassid.equalsIgnoreCase( studentObject.getString("ClassId")  ) &&
                                            mysectionid.equalsIgnoreCase( studentObject.getString("SectionId")  )){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("Phone"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }

                                }
                                adapter=new ParentCommunicateAdapter(getActivity(),studentLists);
                                recyclerview.setAdapter(adapter);

                                searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        // filter recycler view when query submitted
                                        adapter.getFilter().filter(query);
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String query) {
                                        // filter recycler view when text is changed
                                        adapter.getFilter().filter(query);
                                        return false;
                                    }
                                });
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
                        Toast.makeText(getActivity(), "Network Connection is not working ", Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



    private void LoadClassDetails() {
        //getting the progressbar
        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.classdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressDoalog.dismiss();


                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array

                                classadapter=null;
                                classlist.clear();;
                                classidlist.clear();


                                classlist.add("All Class");
                                classidlist.add("0");
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    classlist.add(studentObject.getString("ClassName"));
                                    classidlist.add(studentObject.getString("ID"));
                                }

                                classadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,classlist);
                                class_spin.setAdapter(classadapter);


                                class_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        myclassid=classidlist.get(position);
                                        LoadSection();

                                        // LoadStudentList();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                progressDoalog.dismiss();
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


    private void LoadSection() {
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
                                sectionlist.clear();
                                sectionidlist.clear();
                                adapter=null;
                                sectionlist.add( "All Section");
                                sectionidlist.add( "0");
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("Id"));

                                    sectionlist.add( studentObject.getString("SectionName"));
                                    sectionidlist.add( studentObject.getString("Id"));

                                }


                                sectionadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,sectionlist);
                                section_spin.setAdapter(sectionadapter);

                                section_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        mysectionid=sectionidlist.get(position);


                                        LoadStudentList();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
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

}
