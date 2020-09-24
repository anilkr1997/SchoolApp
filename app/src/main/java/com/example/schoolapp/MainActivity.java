package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.example.schoolapp.Model.ClassModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button Studentsubmit;
    DatePickerDialog picker;
    TextView signup,open;
    ProgressDialog progressDoalog;
    CheckBox remember;
    boolean rememberpass=false;
    String usernames,passwords;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       sharedPreferences=getSharedPreferences("MyPref1",MODE_PRIVATE);
       rememberpass=sharedPreferences.getBoolean("rememberpass",false);
       usernames=sharedPreferences.getString("usernames","");
        passwords=sharedPreferences.getString("passwords","");
        Log.d("ashishsikarwar",""+rememberpass);

        progressDoalog=new ProgressDialog(this);
        //SubmitStuFee();
        remember=findViewById(R.id.remember);
        username=findViewById(R.id.username);
        signup=findViewById(R.id.registration);
        password=findViewById(R.id.password);
        Studentsubmit=findViewById(R.id.login);
        open=findViewById(R.id.open);

        if(rememberpass){
            remember.setChecked(true);
            password.setText(passwords);
            username.setText(usernames);
        }



        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SchoolDashBoard.class));
            }
        });

        Studentsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Studentsubmit.setClickable(false);
                Studentsubmit.setEnabled(false);
                    LogIn(username.getText().toString(),password.getText().toString());
                 //   LogIn("aviaryan970@gmail.com","aryan3246");



        }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserSignUp.class).putExtra("ID","0"));}

               /* final Dialog dialog = new Dialog(studentlogin.this);
                dialog.setContentView(R.layout.insertmobile);
                Button save=dialog.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText mobile=dialog.findViewById(R.id.mobile);
                        if(mobile.getText().toString().length()==10){
                            dialog.dismiss();
                            finish();
                            startActivity(new Intent(studentlogin.this,SignUp.class).putExtra("mobile",mobile.getText().toString()));}
                        else{
                            Toast.makeText(studentlogin.this,"Please Enter a Valid 10 Digits mobile no  ",0).show();
                        }
                    }
                });
                dialog.show();*/

        });
        //SubmitStuFee();
    }


    private void LogIn(final String name, final String Pass) {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.Authenticate+name+"&Password="+Pass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressDoalog.dismiss();


                        try {//UserName

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);
                            JSONObject reponsedata=obj.getJSONObject("ResponseData");
                            if(obj.getString("Message").equalsIgnoreCase("Success")) {
                              String ID=reponsedata.getString("ID");

                                SharedPrefrenceManager.getInstance(getApplicationContext()).userLogin(name,reponsedata.getString("UserName"),reponsedata.getString("Password"));
                                startActivity(new Intent(MainActivity.this, SchoolDashBoard.class));
                                if(remember.isChecked()){
                                  SharedPreferences.Editor editor=sharedPreferences.edit();
                                  editor.putString("ID",ID);
                                  editor.putBoolean("rememberpass",true);
                                  editor.putString("usernames",name);
                                  editor.putString("passwords",Pass);
                                  editor.apply();
                                    Log.d("ashishsikarwar",""+remember.isChecked());
                                }
                                else{
                                  SharedPreferences.Editor editor=sharedPreferences.edit();
                                  editor.putBoolean("rememberpass",false);
                                    editor.putString("ID",ID);
                                  editor.apply();
                                    Log.d("ashishsikarwar",""+remember.isChecked());
                                }
                            }
                            else {
                                Studentsubmit.setClickable(true);
                                Studentsubmit.setEnabled(true);
                                Toast.makeText(MainActivity.this, obj.getString("Message")+"", Toast.LENGTH_SHORT).show();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            Studentsubmit.setClickable(true);
                            Studentsubmit.setEnabled(true);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

}

