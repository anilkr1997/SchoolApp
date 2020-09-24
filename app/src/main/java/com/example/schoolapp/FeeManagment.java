package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.print.PrintHelper;
import android.Manifest;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Model.SubmitFee;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeeManagment extends AppCompatActivity implements View.OnClickListener {
    EditText AdmissionNo,StudentName,Amount,SubmissionDate;
    Spinner feetype;
    Button save,print;
    View view;
    ImageView image;
    Button jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec;
    LinearLayout prinlayout;
    boolean m1=false,m2=false,m3=false,m4=false,m5=false,m6=false,
            m7=false,m8=false,m9=false,m10=false,m11=false,m12=false;
    int feeamount=0;
    ArrayList<String> feestypes=new ArrayList<>();
    String selectedfeetype;
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<SubmitFee> submitFees=new ArrayList<>();
    String ClassId="1";
    ProgressBar progressbar;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_managment);

  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

       /* AdmissionNo.setText(this.getArguments().getString("AdmissionNo"));
        StudentName.setText(this.getArguments().getString("StudentName"));
        ClassId=this.getArguments().getString("StudentName");*/

        selectedfeetype="";

    /*
        String myValue = this.getArguments().getString("id");
        String namevalue = this.getArguments().getString("name");
        final String fathern = this.getArguments().getString("father");
        final String Classn = this.getArguments().getString("Class");*/
     /*   schoolid.setText(myValue);
        name.setText(namevalue);*/

        jan.setOnClickListener(this);
        jan.setClickable(false);
        feb.setOnClickListener(this);
        mar.setOnClickListener(this);
        apr.setOnClickListener(this);
        may.setOnClickListener(this);
        jun.setOnClickListener(this);
        jul.setOnClickListener(this);
        aug.setOnClickListener(this);
        sep.setOnClickListener(this);
        oct.setOnClickListener(this);
        nov.setOnClickListener(this);
        dec.setOnClickListener(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpadateFee();
            }
        });


        SubmissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(FeeManagment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                SubmissionDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        feetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                selectedfeetype=    feetype.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  imageprint();
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( FeeManagment.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission( FeeManagment.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


                    ActivityCompat.requestPermissions(
                            FeeManagment.this,
                            PERMISSIONS_STORAGE,
                            1
                    );

                    return;
                }

                final Dialog dialog=new Dialog(FeeManagment.this);
                dialog.setContentView(R.layout.feelayoutprint);
                prinlayout=dialog.findViewById(R.id.prinlayout);
                Button print=dialog.findViewById(R.id.print);
                Button share=dialog.findViewById(R.id.share);
                TextView name1=dialog.findViewById(R.id.name);
                TextView amount1=dialog.findViewById(R.id.amount);
                TextView dof1=dialog.findViewById(R.id.dof);
                TextView father=dialog.findViewById(R.id.father);
                TextView Class=dialog.findViewById(R.id.Class);
                TextView feetype1=dialog.findViewById(R.id.feetype);
                TextView schoolname=dialog.findViewById(R.id.schoolname);

                Typeface typeface= Typeface.createFromAsset(FeeManagment.this.getAssets(),"part.ttf");

                schoolname.setTypeface(typeface);
                feetype1.setText(feetype.getSelectedItem().toString());
                name1.setText(StudentName.getText().toString());
                amount1.setText(Amount.getText().toString());
                Class.setText(ClassId);
               /* Class.setText(Classn);
                father.setText(fathern);*/
                dof1.setText(SubmissionDate.getText().toString());

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageprint(prinlayout);
                        dialog.dismiss();
                    }
                });

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveImage(loadBitmapFromView(prinlayout));
                        send();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void init() {
        AdmissionNo=findViewById(R.id.AdmissionNo);
        StudentName=findViewById(R.id.StudentName);
        feetype=findViewById(R.id.feetype);

        Amount=findViewById(R.id.Amount);
        save=findViewById(R.id.save);
        print=findViewById(R.id.print);
        SubmissionDate=findViewById(R.id.SubmissionDate);
        image=findViewById(R.id.image);
        progressbar=findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);

        try{
            AdmissionNo.setShowSoftInputOnFocus(false);
            StudentName.setShowSoftInputOnFocus(false);
            //Amount.setShowSoftInputOnFocus(false);
            SubmissionDate.setShowSoftInputOnFocus(false);
        }
        catch (Exception e){

        }

//www.reddit.cpm/.json

        jan=findViewById(R.id.jan);
        feb=findViewById(R.id.feb);
        mar=findViewById(R.id.mar);
        apr=findViewById(R.id.apr);
        may=findViewById(R.id.may);
        jun=findViewById(R.id.jun);
        jul=findViewById(R.id.jul);
        aug=findViewById(R.id.aug);
        sep=findViewById(R.id.sep);
        oct=findViewById(R.id.oct);
        nov=findViewById(R.id.nov);
        dec=findViewById(R.id.dec);
        //jan.setBackgroundResource(R.color.nonselectmonth);
        feb.setBackgroundResource(R.color.nonselectmonth);
        mar.setBackgroundResource(R.color.nonselectmonth);
        apr.setBackgroundResource(R.color.nonselectmonth);
        may.setBackgroundResource(R.color.nonselectmonth);
        jun.setBackgroundResource(R.color.nonselectmonth);
        jul.setBackgroundResource(R.color.nonselectmonth);
        aug.setBackgroundResource(R.color.nonselectmonth);
        sep.setBackgroundResource(R.color.nonselectmonth);
        oct.setBackgroundResource(R.color.nonselectmonth);
        nov.setBackgroundResource(R.color.nonselectmonth);
        dec.setBackgroundResource(R.color.nonselectmonth);
        LoadFeetype();
    }

    public void imageprint(View printlayout){
        PrintHelper printHelper = new PrintHelper(FeeManagment.this);
        // Set the desired scale mode.
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        // Get the bitmap for the ImageView's drawable.
        Bitmap icon = BitmapFactory.decodeResource(FeeManagment.this.getResources(),
                R.drawable.check);
        Bitmap bitmap=loadBitmapFromView(printlayout);
        // Print the bitmap.
        printHelper.printBitmap("Print Bitmap", bitmap);

    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
    private static void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        Log.i("Directory", "==" + myDir);
        myDir.mkdirs();

        String fname = "Image-test" + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send() {
        try {
            File myFile = new File("/storage/emulated/0/saved_images/Image-test.jpg");
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType(type);
            sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile));
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } catch (Exception e) {
            // Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jan:
                if(m1){
                    decreaseFee();
                    jan.setBackgroundResource(R.color.nonselectmonth);
                    m1=false;
                }
                else{
                    increseFee();
                    jan.setBackgroundResource(R.color.selectedmonth);
                    m1=true;
                }
                break;
            case R.id.feb:
                if(m2){
                    decreaseFee();
                    feb.setBackgroundResource(R.color.nonselectmonth);
                    m2=false;
                }
                else{increseFee();
                    feb.setBackgroundResource(R.color.selectedmonth);
                    m2=true;
                }
                break;
            case R.id.mar:
                if(m3){
                    decreaseFee();
                    mar.setBackgroundResource(R.color.nonselectmonth);
                    m3=false;
                }
                else{increseFee();
                    mar.setBackgroundResource(R.color.selectedmonth);
                    m3=true;
                }
                break;
            case R.id.apr:
                if(m4){
                    decreaseFee();
                    apr.setBackgroundResource(R.color.nonselectmonth);
                    m4=false;
                }
                else{increseFee();
                    apr.setBackgroundResource(R.color.selectedmonth);
                    m4=true;
                }
                break;
            case R.id.may:
                if(m5){
                    decreaseFee();
                    may.setBackgroundResource(R.color.nonselectmonth);
                    m5=false;
                }
                else{increseFee();
                    may.setBackgroundResource(R.color.selectedmonth);
                    m5=true;
                }
                break;
            case R.id.jun:
                if(m6){
                    decreaseFee();
                    jun.setBackgroundResource(R.color.nonselectmonth);
                    m6=false;
                }
                else{increseFee();
                    jun.setBackgroundResource(R.color.selectedmonth);
                    m6=true;
                }
                break;
            case R.id.jul:
                if(m7){
                    decreaseFee();
                    jul.setBackgroundResource(R.color.nonselectmonth);
                    m7=false;
                }
                else{increseFee();
                    jul.setBackgroundResource(R.color.selectedmonth);
                    m7=true;
                }
                break;
            case R.id.aug:
                if(m8){
                    decreaseFee();
                    aug.setBackgroundResource(R.color.nonselectmonth);
                    m8=false;
                }
                else{increseFee();
                    aug.setBackgroundResource(R.color.selectedmonth);
                    m8=true;
                }
                break;
            case R.id.sep:
                if(m9)
                {decreaseFee();
                    sep.setBackgroundResource(R.color.nonselectmonth);
                    m9=false;
                }
                else{increseFee();
                    sep.setBackgroundResource(R.color.selectedmonth);
                    m9=true;
                }
                break;
            case R.id.oct:
                if(m10){decreaseFee();
                    oct.setBackgroundResource(R.color.nonselectmonth);
                    m10=false;
                }
                else{increseFee();
                    oct.setBackgroundResource(R.color.selectedmonth);
                    m10=true;
                }
                break;
            case R.id.nov:
                if(m11){
                    decreaseFee();
                    nov.setBackgroundResource(R.color.nonselectmonth);
                    m11=false;
                }
                else{increseFee();
                    nov.setBackgroundResource(R.color.selectedmonth);
                    m11=true;
                }
                break;
            case R.id.dec:
                if(m12){decreaseFee();
                    dec.setBackgroundResource(R.color.nonselectmonth);
                    m12=false;
                }
                else{increseFee();
                    dec.setBackgroundResource(R.color.selectedmonth);
                    m12=true;
                }
                break;
        }
    }
    public void increseFee(){
        feeamount=feeamount+2400;
        Amount.setText(""+feeamount);

    }
    public void decreaseFee(){
        feeamount=feeamount-2400;
        Amount.setText(""+feeamount);
    }

    private void LoadFeetype() {
        //getting the progressbar

        //making the progressbar visible
        // progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.getfeetype,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //   progressBar.setVisibility(View.INVISIBLE);


                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);


                            JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                            //  studentUpdatedetails.clear();
                            for (int i = 0; i < stuarray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject stufee = stuarray.getJSONObject(i);
                                feestypes.add(stufee.getString("FeeType1"));
                                Log.d("ashishsikarwar",stufee.getString("FeeType1"));

                            }
                            ArrayAdapter<String> arrayAdapter=new ArrayAdapter(FeeManagment.this,R.layout.spinnerlayout,feestypes);
                            feetype.setAdapter(arrayAdapter);



                            //   Log.d("ashishsikarwar",response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(FeeManagment.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    private void UpadateFee() {

        progressbar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.submitstufee,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressbar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);
                            // Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            //JSONObject jo=obj.getJSONObject("ResponseData");
                            //Toast.makeText(getApplicationContext(), jo.getString("StudentName"), Toast.LENGTH_SHORT).show();
                            //  LoadStudentList();
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                //     Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                //   Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("ID","1200" );
                params.put("PaymentModeId","2" );
                params.put("FeeTypeId","2");
                params.put("SubmissionDate",SubmissionDate.getText().toString() );
                params.put("MonthId","1" );
                params.put("Amount",Amount.getText().toString() );
                params.put("AdmissionNo",AdmissionNo.getText().toString() );
                params.put("CreatedOn",SubmissionDate.getText().toString() );
                params.put("CreatedBy","Ashish " );
                params.put("UpdatedOn",SubmissionDate.getText().toString() );


                return params;
            }
        };

        VolleySingleton.getInstance(FeeManagment.this).addToRequestQueue(stringRequest);

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
}