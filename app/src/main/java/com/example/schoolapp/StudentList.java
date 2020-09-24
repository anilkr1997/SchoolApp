package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
import com.example.schoolapp.Adapter.ClassAdapter;
import com.example.schoolapp.Adapter.SectionAdapter;
import com.example.schoolapp.Adapter.StudentListAdapter;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.Model.SectionModel;
import com.example.schoolapp.Model.StudentListModel;
import com.example.schoolapp.Model.StudentUpdatedetails;
import com.example.schoolapp.interfaces.ClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {
    StudentListAdapter adapter;
    List<StudentListModel> studentLists;
    RecyclerView recyclerview;
    ProgressDialog progressDoalog;
    FloatingActionButton fab;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDoalog=new ProgressDialog(StudentList.this);
        fab=findViewById(R.id.fab);
        recyclerview=findViewById(R.id.recyclerview);
        searchview=findViewById(R.id.searchview);
        class_spin=findViewById(R.id.class_spin);
        section_spin=findViewById(R.id.section_spin);
        studentLists=new ArrayList<>();
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(StudentList.this));


        LoadClassDetails();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentList.this,StudentAdmission.class).putExtra("ID","0"));
            }
        });
/*
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerview, new ClickListener() {
            @Override
        public void onClick(View view, final int position) {
            //Values are passing Sc activity & to fragment as well
                }

            @Override
            public void onLongClick(View view, int position) {
            *//*    Toast.makeText(SchoolDashBoard.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();*//*
            }}));*/


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
                                            studentObject.getString("AdmissionNo"),
                                            studentObject.getString("ClassId")
                                    ));
                                    }

                                    else if(myclassid.equalsIgnoreCase( studentObject.getString("ClassId")  ) &&
                                            mysectionid.equalsIgnoreCase( "0")){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("AdmissionNo"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }

                                    else if(myclassid.equalsIgnoreCase("0"  ) &&
                                            mysectionid.equalsIgnoreCase( studentObject.getString("SectionId")  )){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("AdmissionNo"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }
                                    else if(myclassid.equalsIgnoreCase( studentObject.getString("ClassId")  ) &&
                                            mysectionid.equalsIgnoreCase( studentObject.getString("SectionId")  )){
                                        studentLists.add(new StudentListModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("ImagePath") ,
                                                studentObject.getString("AdmissionNo"),
                                                studentObject.getString("ClassId")
                                        ));
                                    }

                                }
                                adapter=new StudentListAdapter(StudentList.this,studentLists);
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
                        Toast.makeText(getApplicationContext(), "Network Connection is not working ", Toast.LENGTH_SHORT).show();
                       finish();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

                                classadapter=new ArrayAdapter<>(StudentList.this,android.R.layout.simple_spinner_dropdown_item,classlist);
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
                        Toast.makeText(StudentList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentList.this);

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


                                sectionadapter=new ArrayAdapter<>(StudentList.this,android.R.layout.simple_spinner_dropdown_item,sectionlist);
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
                            Toast.makeText(StudentList.this,""+"error",Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(StudentList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentList.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
       // LoadClassDetails();
    }
}
