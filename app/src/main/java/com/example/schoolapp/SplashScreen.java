package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends Activity {

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        img = (ImageView)findViewById(R.id.image);
        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        //UpdateClass();
        img.startAnimation(animZoomIn);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over

                if(SharedPrefrenceManager.getInstance(SplashScreen.this).isLoggedIn()){
                    finish();
                    startActivity(new Intent(SplashScreen.this, SchoolDashBoard.class));
                }
                else{
                    finish();
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }
        }, 2000);

    }
    String url="https://shipway.in/api/authenticateUser";



    public void UpdateClass() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object

                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SplashScreen.this,"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

//Scotch wisky
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username","Vikas01" );
                params.put("password","c20800cedadd2b84a7b81b9db577c9a7");
                return new JSONObject( params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                //return super.getBodyContentType();
                return "application/json";
            }
        };

        VolleySingleton.getInstance(SplashScreen.this).addToRequestQueue(stringRequest);

    }

}
