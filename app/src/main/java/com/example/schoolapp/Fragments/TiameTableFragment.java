package com.example.schoolapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.schoolapp.Adapter.TimeTableAdapter;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.Model.StudentUpdatedetails;
import com.example.schoolapp.Model.TimeTableModel;
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


public class TiameTableFragment extends Fragment implements   TimeTableAdapter.UpdateTime{


    View view;
    ArrayList<String> daylist=new ArrayList<>();
    ArrayAdapter<String> adapter=null;
    Spinner dayspinner;
    FloatingActionButton fab;
    RecyclerView recyclerview;
    String dayname="Monday";
    String classid;
    String Classname;
    TextView className;

    TimeTableAdapter timeTableAdapter=null;
    TimeTableAdapter.UpdateTime updateTime;
    List<TimeTableModel> timeTableModelList=new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_tiame_table, container, false);
       // setRetainInstance(true);
        progressDialog=new ProgressDialog(getActivity());
        updateTime=this;
        Classname = getArguments().getString("classname");
        classid  = getArguments().getString("classid");
        daylist.add("Monday");
        daylist.add("Tuesday");
        daylist.add("Wednesday");
        daylist.add("Thursday");
        daylist.add("Friday");
        daylist.add("Saturday");
        daylist.add("Sunday");


        className=view.findViewById(R.id.className);
        fab=view.findViewById(R.id.fab);
        dayspinner=view.findViewById(R.id.dayspinner);
        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,daylist);
        dayspinner.setAdapter(adapter);
        className.setText("Class\n"+Classname);


       dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayname=daylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerview=view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);



        GetTimeTable();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.custumsubject);
                final EditText subject=dialog.findViewById(R.id.subject);
                final EditText starttime=dialog.findViewById(R.id.starttime);
                final EditText endtime=dialog.findViewById(R.id.endtime);
                final EditText teacher=dialog.findViewById(R.id.teacher);
                Button ok=dialog.findViewById(R.id.ok);




                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AddTimeTable("0","1",starttime.getText().toString(),
                                endtime.getText().toString(),classid,"1",dayname,
                               "1","1","true","false","2" );
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

   return view;
    }


    @Override
    public void updateTime(final String id, String sub, String starttime, String tilltime, String teacher) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.custumsubject);
        final EditText subject=dialog.findViewById(R.id.subject);
        final EditText starttimes=dialog.findViewById(R.id.starttime);
        final EditText endtime=dialog.findViewById(R.id.endtime);
        final EditText teachers=dialog.findViewById(R.id.teacher);
        subject.setText(sub);
        starttimes.setText(starttime);
        endtime.setText(tilltime);
        teachers.setText(teacher);


        Button ok=dialog.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                dialog.dismiss();
            }
        });
        dialog.show();
    }








    private void GetTimeTable() {
        progressDialog.setTitle("Processing..");
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.show();
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllTimeTable,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        progressDialog.dismiss();

                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                timeTableModelList.clear();
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                 //   if (studentObject.getString("ClassID").equals(classid)) {
                                        timeTableModelList.add(new TimeTableModel(
                                                studentObject.getString("ID"),
                                                studentObject.getString("SubjectId"),
                                                studentObject.getString("StartTime"),
                                                studentObject.getString("EndTime"),
                                                studentObject.getString("ClassID"),
                                                studentObject.getString("SectionId"),
                                                studentObject.getString("Day"),
                                                studentObject.getString("EmpId"),
                                                studentObject.getString("SessionId"),
                                                studentObject.getString("IsActive"),
                                                studentObject.getString("IsDeleted"),
                                                studentObject.getString("Period")

                                        ));
                                   // }
                                }
                                if(timeTableAdapter==null){
                                    timeTableAdapter=new TimeTableAdapter(updateTime,timeTableModelList,getActivity());
                                    recyclerview.setAdapter(timeTableAdapter);
                                }
                                else{
                                    timeTableAdapter.notifyDataSetChanged();
                                }

                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void AddTimeTable(final String ID, final String SubjectId, final String StartTime, final String EndTime,
                              final String ClassID, final String SectionId, final String Day, final String EmpId
            , final String SessionId, final String IsActive, final String IsDeleted, final String Period) {

        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Regiister your data..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertTimeTabledetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            progressDialog.dismiss();
                            GetTimeTable();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            JSONObject jo=obj.getJSONObject("ResponseData");

                            //if no error in response

                         //   finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Network Error Press Buttun save Again."+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID",ID );
                params.put("SubjectId", SubjectId);
                params.put("StartTime",StartTime);
                params.put("EndTime",EndTime);
                params.put("ClassID", ClassID);
                params.put("SectionId", SectionId);
                params.put("Day",Day);
                params.put("EmpId",EmpId);
                params.put("SessionId", SessionId);
                params.put("IsActive",IsActive);
                params.put("IsDeleted", IsDeleted);
                params.put("Period", Period);



                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
}
