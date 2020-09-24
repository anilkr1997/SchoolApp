package com.example.schoolapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.ClassAdapter;
import com.example.schoolapp.Adapter.SectionAdapter;
import com.example.schoolapp.Adapter.SessionAdapter;
import com.example.schoolapp.Adapter.StudentListAdapter;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.Model.GetAllclassFeeModel;
import com.example.schoolapp.Model.SectionModel;
import com.example.schoolapp.Model.SessionModel;
import com.example.schoolapp.Model.StudentListModel;
import com.example.schoolapp.Model.StudentUpdatedetails;
import com.example.schoolapp.R;
import com.example.schoolapp.StudentList;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DepositeStuFee extends Fragment {



    ProgressDialog progressDoalog;
    View view;


    List<SessionModel> sessionModelList;
    Spinner spinnersession;
    ArrayList<String> sessionid,sessionname;
    ArrayAdapter<String> sessionadapter;


    List<ClassModel> classModelList;
    Spinner spinnerclass;
    ArrayList<String> classid,classname;
    ArrayAdapter<String> classadapter;

    List<SectionModel> sectionModellist;
    Spinner spinnersection;
    ArrayList<String> sectionid,sectionname;
    ArrayAdapter<String> sectionadapter;

    List<StudentUpdatedetails> studentLists;
    Spinner spinnerstudent;
    ArrayList<String> studentid,studentname;
    ArrayAdapter<String> studentadapter;

   GetAllclassFeeModel getAllclassFeeModel;
   int fee=0;
   CheckBox jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec;
   ArrayList<String> monthlist=new ArrayList<>();
   EditText amount,currendate;
   String StusessionId="";
   String StuClassId="";
    StudentUpdatedetails submitfeestudent;
    String Month;
    Button submit;
    String formattedDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_deposite_stu_fee, container, false);
        progressDoalog=new ProgressDialog(getActivity());


        submit=view.findViewById(R.id.submit);
        amount=view.findViewById(R.id.amount);
        currendate=view.findViewById(R.id.currendate);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        formattedDate = df.format(c);
        currendate.setText(""+formattedDate);
        jan=view.findViewById(R.id.jan);
        feb=view.findViewById(R.id.feb);
        mar=view.findViewById(R.id.mar);
        apr=view.findViewById(R.id.apr);
        may=view.findViewById(R.id.may);
        jun=view.findViewById(R.id.jun);
        jul=view.findViewById(R.id.jul);
        aug=view.findViewById(R.id.aug);
        sep=view.findViewById(R.id.sep);
        oct=view.findViewById(R.id.oct);
        nov=view.findViewById(R.id.nov);
        dec=view.findViewById(R.id.dec);

        jan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(jan.isChecked(),jan.getText().toString());
            }
        });
        feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(feb.isChecked(),feb.getText().toString());
            }
        });
        mar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(mar.isChecked(),mar.getText().toString());
            }
        });
        apr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(apr.isChecked(),apr.getText().toString());
            }
        });
        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(may.isChecked(),may.getText().toString());
            }
        });
        jun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(jun.isChecked(),jun.getText().toString());
            }
        });
        jul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(jul.isChecked(),jul.getText().toString());
            }
        });
        aug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(aug.isChecked(),aug.getText().toString());
            }
        });
        sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(sep.isChecked(),sep.getText().toString());
            }
        });
        oct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(oct.isChecked(),oct.getText().toString());
            }
        });
        nov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(nov.isChecked(),nov.getText().toString());
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillFee(dec.isChecked(),dec.getText().toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitStuFee(formattedDate);
            }
        });


        spinnersession=view.findViewById(R.id.spinnersession);
        sessionModelList=new ArrayList<>();
        sessionid=new ArrayList<>();
        sessionname=new ArrayList<>();

        spinnerclass=view.findViewById(R.id.spinnerclass);
        classModelList=new ArrayList<>();
        classid=new ArrayList<>();
        classname=new ArrayList<>();


        spinnersection=view.findViewById(R.id.spinnersection);
        sectionModellist=new ArrayList<>();
        sectionid=new ArrayList<>();
        sectionname=new ArrayList<>();




        spinnerstudent=view.findViewById(R.id.spinnerstudent);
        studentLists=new ArrayList<>();
        studentid=new ArrayList<>();
        studentname=new ArrayList<>();

        LoadSessionDetails();


        return view;
    }


    private void LoadSessionDetails() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Session");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllSession,
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
                                sessionModelList.clear();
                                sessionid.clear();
                                sessionname.clear();
                                sessionadapter=null;
                                sessionModelList.add(new SessionModel("-1","Select Session","true","false"));
                                sessionname.add("Select Session");
                                sessionid.add("-1");
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    sessionModelList.add(new SessionModel(studentObject.getString("Id"),
                                            studentObject.getString("SessionName"),
                                            studentObject.getString("IsActive"),
                                            studentObject.getString("IsDeleted")

                                    ));
                                    sessionname.add(studentObject.getString("SessionName"));
                                    sessionid.add(studentObject.getString("Id"));
                                    Log.d("ashishsikarwar", studentObject.getString("Id"));


                                }
                               sessionadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,sessionname);
                                spinnersession.setAdapter(sessionadapter);



                                spinnersession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,final int position, long id) {
                                        Log.d("ashishsikarwar",""+position);
                                        StusessionId=sessionid.get(position);

                                            if(position!=0)
                                                LoadClassDetails();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            Log.d("ashishsikarwar",e+""+response);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(),"Try Again Later"+ error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        progressDoalog.dismiss();


                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                classModelList.clear();
                                classname.clear();
                                classid.clear();
                                classadapter=null;
                                classModelList.add(new ClassModel("-1","Select Class",true,false));
                                classid.add( "-1");
                                classname.add("Select Class ");
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("ID"));
                                    classModelList.add(new ClassModel(
                                            studentObject.getString("ID"),
                                            studentObject.getString("ClassName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));
                                    classid.add( studentObject.getString("ID"));
                                    classname.add(studentObject.getString("ClassName"));

                                }

                                classadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,classname);
                                spinnerclass.setAdapter(classadapter);

                                spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        StuClassId=classid.get(position);
                                        if(position!=0)
                                            LoadSectionDetails();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void LoadSectionDetails() {
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
                                sectionModellist.clear();
                                sectionid.clear();
                                sectionname.clear();
                                sectionadapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    Log.d("ashishsikarwar", studentObject.getString("Id"));
                                    sectionModellist.add(new SectionModel(
                                            studentObject.getString("Id"),
                                            studentObject.getString("SectionName"),
                                            studentObject.getBoolean("IsActive"),
                                            studentObject.getBoolean("IsDeleted") ));
                                    sectionname.add( studentObject.getString("SectionName"));
                                    sectionid.add( studentObject.getString("Id"));

                                }

                                sectionadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,sectionname);
                                spinnersection.setAdapter(sectionadapter);
                                LoadStudentList();


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


    private void LoadStudentList() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog=new ProgressDialog(getActivity());
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
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                studentLists.clear();
                                studentname.clear();
                                studentid.clear();
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    if (studentObject.getString("SessionId").equals(StusessionId) &&
                                            studentObject.getString("ClassId").equals(StuClassId)  ) {
                                        Log.d("ashishsikarwar",studentObject.getString("StudentName"));
                                        studentLists.add(new StudentUpdatedetails(
                                                studentObject.getString("ID"),
                                                studentObject.getString("AdmissionNo"),
                                                studentObject.getString("StudentName"),
                                                studentObject.getString("FatherName"),
                                                studentObject.getString("MotherName"),
                                                studentObject.getString("Address"),
                                                studentObject.getString("Phone"),
                                                studentObject.getString("DOB"),
                                                studentObject.getString("OcupetionOfFather"),
                                                studentObject.getString("SessionId"),
                                                studentObject.getString("RegisterDate"),
                                                studentObject.getString("CreatedDate"),
                                                studentObject.getString("CreatedBy"),
                                                studentObject.getString("UpdatedDate"),
                                                studentObject.getString("UpdatedBy"),
                                                studentObject.getString("ClassId"),
                                                studentObject.getString("BoardId"),
                                                studentObject.getString("IsActive"),
                                                studentObject.getString("IsDeleted"),

                                                studentObject.getString("FatherEducation"),
                                                studentObject.getString("MotherOccupation"),
                                                studentObject.getString("MotherEducation"),
                                                studentObject.getString("PreviousSchoolName"),
                                                studentObject.getString("BloodGroup"),
                                                studentObject.getString("Email"),
                                                studentObject.getString("Sibiling"),
                                                studentObject.getString("emergencyContactNo"),
                                                studentObject.getString("AdharCardNo"),
                                                studentObject.getString("SectionId"),
                                                studentObject.getString("ImagePath")
                                        ));
                                        studentname.add(studentObject.getString("StudentName"));
                                        studentid.add(studentObject.getString("ID"));
                                        Log.d("ashishsikarwar",studentObject.getString("StudentName"));
                                    }
                                }

                                studentadapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,studentname);
                                spinnerstudent.setAdapter(studentadapter);
                                spinnerstudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        submitfeestudent=studentLists.get(position);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                getFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDoalog.dismiss();
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


    private void getFee() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog=new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
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
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array

                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    if (studentObject.getString("SessionId").equals("1") &&
                                            studentObject.getString("ClassId").equals(StuClassId)  ) {
                                        Log.d("ashishsikarwar",studentObject.getString("Course_Monthly"));
                                     fee= Integer.parseInt( studentObject.getString("Course_Monthly") ) ;


                                    }
                                }

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
public void FillFee(boolean checkvalue,String monthvalue){
    if(checkvalue){
        monthlist.add(monthvalue);
        amount.setText(""+fee*monthlist.size());
    }
    else {
        monthlist.remove(monthvalue);
        amount.setText(""+fee*monthlist.size());
    }
     Month=monthlist.toString().substring(1,monthlist.toString().lastIndexOf("]"));
    Log.d("ashishsikarwar",Month);
}




    private void SubmitStuFee(final String formattedDate) {


        progressDoalog.setMessage("Loding....");
        progressDoalog.setTitle("Register your data..");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.submitstufee,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar1",response);
                            if(response.equals("null")){
                                Toast.makeText(getActivity(), "Fee Already Submitted", Toast.LENGTH_SHORT).show();

                            }
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_SHORT).show();

                            //if no error in response


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(getActivity(),"Network Error Press Buttun save Again."+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<>();



                params.put( "ID","1");
                params.put("PaymentModeId","1");
                params.put("FeeTypeId","1");
                params.put("SubmissionDate",formattedDate);
                params.put("MonthId","1");
                params.put("Amount",amount.getText().toString());
                params.put("AdmissionNo", submitfeestudent.getAdmissionNo());
                params.put("Month",Month);
                params.put("ClassId", submitfeestudent.getClassId());
                params.put("SectionId", submitfeestudent.getSessionId());
                params.put("SessionId", submitfeestudent.getSessionId());
                params.put("UpdatedBy","sudarsan");
                params.put("CreatedOn",submitfeestudent.getCreatedDate());
                params.put("SubmitedBy","Ratn");
                params.put("UpdatedOn",formattedDate);


                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }



}
