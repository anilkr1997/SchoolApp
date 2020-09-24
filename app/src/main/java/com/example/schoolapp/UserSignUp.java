package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserSignUp extends AppCompatActivity {

    EditText UserName,Email,Role,Password,UpdatedBy;
    Button signUp;
    ProgressDialog  progressDoalog;
    String mydate;
    String id="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        id=getIntent().getExtras().getString("ID");
        UserName=findViewById(R.id.UserName);
        Email=findViewById(R.id.Email);
        Role=findViewById(R.id.Role);
        Password=findViewById(R.id.Password);
        UpdatedBy=findViewById(R.id.UpdatedBy);
        signUp=findViewById(R.id.signUp);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        mydate = df.format(c);

        progressDoalog=new ProgressDialog(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {

                    return;
                }
                else
                    registerUser();
            }
        });
        if(id.equalsIgnoreCase("0"))
        {}
        else{
            fillEdittext();

        }


    }

    private void fillEdittext() {
        UserName.setText(  SharedPrefrenceManager.getInstance(this).getName());
        Email.setText(  SharedPrefrenceManager.getInstance(this).getUser());
        Password.setText(  SharedPrefrenceManager.getInstance(this).getPass());

        signUp.setText("Update");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserSignUp.this,"you cant update ",0).show();
            }
        });


    }


    private void registerUser() {
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Register your data..");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertUserDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            JSONObject jo=obj.getJSONObject("ResponseData");

                            //if no error in response

                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(getApplicationContext(),"Network Error Press Buttun save Again."+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID",id );
                params.put("UserName", UserName.getText().toString());
                params.put("Email",Email.getText().toString());
                params.put("Role", Role.getText().toString());
                params.put("SubmissionDate", mydate);
                params.put("Password", Password.getText().toString());
                params.put("IsActive","1");
                params.put("UpdatedBy",UpdatedBy.getText().toString());
                params.put("CreatedOn", mydate);
                params.put("CreatedBy", "Ashish");
                params.put("Updatedon", mydate);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    public boolean validate() {
        boolean valid = true;
      if ( UserName.getText().toString().isEmpty()  ) {
            UserName.setError("Enter UserName");
            valid = false;
        } else {
            UserName.setError(null);
        }
        if ( Email.getText().toString().isEmpty()  ) {
            Email.setError("Enter Email");
            valid = false;
        } else {
            Email.setError(null);
        }
        if ( Role.getText().toString().isEmpty()  ) {
            Role.setError("Enter Role");
            valid = false;
        } else {
            Role.setError(null);
        }
        if ( Password.getText().toString().isEmpty()  ) {
            Password.setError("Enter Password");
            valid = false;
        } else {
            Password.setError(null);
        }
        if ( UpdatedBy.getText().toString().isEmpty()  ) {
            UpdatedBy.setError("Enter UpdatedBy");
            valid = false;
        } else {
            UpdatedBy.setError(null);
        }
    return valid;
    }
}
