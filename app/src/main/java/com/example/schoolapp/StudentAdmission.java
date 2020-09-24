package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolapp.Adapter.ClassAdapter;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.Model.SectionModel;
import com.example.schoolapp.Model.SessionModel;
import com.example.schoolapp.Model.StudentUpdatedetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAdmission extends AppCompatActivity  implements View.OnTouchListener {
    private static final String TAG = "StudentAdmission";
    //   GlobalData gd = new GlobalData();
    Button save;
    List<StudentUpdatedetails> studentUpdatedetails;
    Fragment fragment;

    EditText AdmissionNo,StudentName,FatherName,MotherName,Address,Phone,DOB,OcupetionOfFather,RegisterDate,
            CreatedDate,CreatedBy,UpdatedDate,UpdatedBy,BoardId,IsActive,IsDeleted,fathereducation,mothereducation
            ,OcupetionOfmother,previousschool,bloodgroup,Email,Sibiling,emergencyContactNo,adhar;
    ProgressDialog progressDoalog;

    int totalAmount=0;
    ImageView img;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
   String ID;



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
    String stuclassid="1";
    String stusessionid="1";
    String ImageBase4="";
    String section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admission);
        ID= getIntent().getExtras().getString("ID");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        // mobile = getIntent().getExtras().getString("mobile");


        studentUpdatedetails=new ArrayList<>();


        progressDoalog.setMessage("Students Details are loading....");
        progressDoalog.setTitle("Processing..");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        if(ID.equals("0")){

            LoadClassDetails();
        }
        else{

            LoadStudentList();
        }





    }

    private void init() {
        progressDoalog=new ProgressDialog(this);

        spinnersession=findViewById(R.id.spinnersession);
        sessionModelList=new ArrayList<>();
        sessionid=new ArrayList<>();
        sessionname=new ArrayList<>();

        spinnerclass=findViewById(R.id.spinnerclass);
        classModelList=new ArrayList<>();
        classid=new ArrayList<>();
        classname=new ArrayList<>();


        spinnersection=findViewById(R.id.spinnersection);
        sectionModellist=new ArrayList<>();
        sectionid=new ArrayList<>();
        sectionname=new ArrayList<>();







        AdmissionNo=findViewById(R.id.AdmissionNo);
        StudentName=findViewById(R.id.StudentName);
        FatherName=findViewById(R.id.FatherName);
        MotherName=findViewById(R.id.MotherName);
        Address=findViewById(R.id.Address);
        Phone=findViewById(R.id.Phone);
        DOB=findViewById(R.id.DOB);
        OcupetionOfFather=findViewById(R.id.OcupetionOfFather);

        RegisterDate=findViewById(R.id.RegisterDate);
        CreatedDate=findViewById(R.id.CreatedDate);
        CreatedBy=findViewById(R.id.CreatedBy);
        UpdatedDate=findViewById(R.id.UpdatedDate);
        UpdatedBy=findViewById(R.id.UpdatedBy);

        BoardId=findViewById(R.id.BoardId);
        IsActive=findViewById(R.id.IsActive);
        IsDeleted=findViewById(R.id.IsDeleted);

        fathereducation=findViewById(R.id.fathereducation);
        mothereducation=findViewById(R.id.mothereducation);
        OcupetionOfmother=findViewById(R.id.OcupetionOfmother);
        previousschool=findViewById(R.id.previousschool);
        bloodgroup=findViewById(R.id.bloodgroup);
        Email=findViewById(R.id.Email);
        Sibiling=findViewById(R.id.Sibiling);
        emergencyContactNo=findViewById(R.id.emergencyContactNo);
        adhar=findViewById(R.id.adhar);





        save=findViewById(R.id.save);
        img=findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( StudentAdmission.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission( StudentAdmission.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


                    ActivityCompat.requestPermissions(
                            StudentAdmission.this,
                            PERMISSIONS_STORAGE,
                            1
                    );

                    return;
                }

                final Dialog dialog=new Dialog(StudentAdmission.this);
                dialog.setContentView(R.layout.galerrycamera);
                ImageView camera=dialog.findViewById(R.id.camera);
                ImageView gallery=dialog.findViewById(R.id.gallery);

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 11);
                        dialog.dismiss();


                    }
                });

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 23) {

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(StudentAdmission .this, BuildConfig.APPLICATION_ID + ".provider", f));
                            startActivityForResult(intent, 12);
                            // Toast.makeText(FirstScreen.this, f+"", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            startActivityForResult(intent, 12);
                        }
                    }
                });
                dialog.show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onStudentAdmissionFailed();
                    return;
                }

                registerUser();
               /* new EnterTask().execute(admissionid.getText().toString(),_nameText.getText().toString(),
                        class_name.getText().toString(),father.getText().toString(),mothername.getText().toString(),
                        section.getText().toString(),dob.getText().toString(),_addressText.getText().toString(),
                        _emailText.getText().toString(),_mobileText.getText().toString());*/

            }
        });

    }





    public void onStudentAdmissionFailed() {
        Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();


    }

    public boolean validate() {
        boolean valid = true;


        if ( AdmissionNo.getText().toString().isEmpty()  ) {
            AdmissionNo.setError("Enter AdmissionNo");
            valid = false;
        } else {
            AdmissionNo.setError(null);
        }

        if ( StudentName.getText().toString().isEmpty()  ) {
            StudentName.setError("Enter StudentName");
            valid = false;
        } else {
            StudentName.setError(null);
        }
        if ( FatherName.getText().toString().isEmpty()  ) {
            FatherName.setError("Enter FatherName");
            valid = false;
        } else {
            FatherName.setError(null);
        }
        if ( MotherName.getText().toString().isEmpty()  ) {
            MotherName.setError("Enter MotherName");
            valid = false;
        } else {
            MotherName.setError(null);
        }
        if ( Address.getText().toString().isEmpty()  ) {
            Address.setError("Enter Address");
            valid = false;
        } else {
            Address.setError(null);
        }
        if ( Phone.getText().toString().isEmpty()  ) {
            Phone.setError("Enter Phone");
            valid = false;
        } else {
            Phone.setError(null);
        }
        if ( DOB.getText().toString().isEmpty()  ) {
            DOB.setError("Enter AdmissionNo");
            valid = false;
        } else {
            DOB.setError(null);
        }
        if ( OcupetionOfFather.getText().toString().isEmpty()  ) {
            OcupetionOfFather.setError("Enter Ocupetion Of Father");
            valid = false;
        } else {
            OcupetionOfFather.setError(null);
        }

        if ( RegisterDate.getText().toString().isEmpty()  ) {
            RegisterDate.setError("Enter RegisterDate");
            valid = false;
        } else {
            RegisterDate.setError(null);
        }

        if ( CreatedDate.getText().toString().isEmpty()  ) {
            CreatedDate.setError("Enter CreatedDate");
            valid = false;
        } else {
            CreatedDate.setError(null);
        }
        if ( CreatedBy.getText().toString().isEmpty()  ) {
            CreatedBy.setError("Enter CreatedBy");
            valid = false;
        } else {
            CreatedBy.setError(null);
        }
        if ( UpdatedDate.getText().toString().isEmpty()  ) {
            UpdatedDate.setError("Enter UpdatedDate");
            valid = false;
        } else {
            UpdatedDate.setError(null);
        }
        if ( UpdatedBy.getText().toString().isEmpty()  ) {
            UpdatedBy.setError("Enter UpdatedBy");
            valid = false;
        } else {
            UpdatedBy.setError(null);
        }

        if ( BoardId.getText().toString().isEmpty()  ) {
            BoardId.setError("Enter BoardId");
            valid = false;
        } else {
            BoardId.setError(null);
        }
        if ( IsActive.getText().toString().isEmpty()  ) {
            IsActive.setError("Enter IsActive");
            valid = false;
        } else {
            IsActive.setError(null);
        }
        if ( IsDeleted.getText().toString().isEmpty()  ) {
            IsDeleted.setError("Enter IsDeleted");
            valid = false;
        } else {
            IsDeleted.setError(null);
        }


/*
        if (check.isChecked()) {

        } else {
            valid = false;
            check.setError("Make Sure you are agree to our Terms of Service");
        }*/

        return valid;
    }

    public void resetAll(){
        AdmissionNo.setText("");
        StudentName.setText("");
        FatherName.setText("");
        MotherName.setText("");
        Address.setText("");
        Phone.setText("");
        DOB.setText("");
        OcupetionOfFather.setText("");

        RegisterDate.setText("");
        CreatedDate.setText("");
        CreatedBy.setText("");
        UpdatedDate.setText("");
        UpdatedBy.setText("");

        BoardId.setText("");
        IsActive.setText("");
        IsDeleted.setText("");
    }






    private void registerUser() {

        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Regiister your data..");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.datainsert,
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
                            Toast.makeText(getApplicationContext(), jo.getString("StudentName"), Toast.LENGTH_SHORT).show();

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
               params.put("ID",ID );
                params.put("AdmissionNo", AdmissionNo.getText().toString());
                params.put("StudentName",StudentName.getText().toString());
                params.put("FatherName", FatherName.getText().toString());
                params.put("MotherName", MotherName.getText().toString());
                params.put("Address", Address.getText().toString());
                params.put("Phone",Phone.getText().toString());
                params.put("DOB",DOB.getText().toString());
                params.put("OcupetionOfFather", OcupetionOfFather.getText().toString());
                params.put("SessionId", stusessionid);
                params.put("RegisterDate",RegisterDate.getText().toString());
                params.put("CreatedDate", CreatedDate.getText().toString());
                params.put("CreatedBy",CreatedBy.getText().toString());
                params.put("UpdatedDate", UpdatedDate.getText().toString());
                params.put("UpdatedBy",UpdatedBy.getText().toString());
                params.put("ClassId", stuclassid);
                params.put("BoardId", BoardId.getText().toString());
                params.put("IsActive", IsActive.getText().toString());
                params.put("IsDeleted", IsDeleted.getText().toString());

                params.put("FatherEducation",fathereducation.getText().toString());
                params.put("MotherOccupation",OcupetionOfmother.getText().toString());
                params.put("MotherEducation", mothereducation.getText().toString());
                params.put("PreviousSchoolName",previousschool.getText().toString());
                params.put("BloodGroup", bloodgroup.getText().toString());
                params.put("Email", Email.getText().toString());
                params.put("Sibiling", Sibiling.getText().toString());
                params.put("emergencyContactNo", emergencyContactNo.getText().toString());
                params.put("AdharCardNo", adhar.getText().toString());
                params.put("SectionId", section);
                params.put("ImageBase4", ImageBase4);
                params.put("ImagePath", "");

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }





    private void LoadStudentList() {
        //getting the progressbar

        //making the progressbar visible


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.studentsdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion



                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                studentUpdatedetails.clear();
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    if (studentObject.getString("ID").equals(ID)) {
                                        studentUpdatedetails.add(new StudentUpdatedetails(
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
                                    }
                                }
                             updateValue();
                                LoadClassDetails();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void updateValue() {
        AdmissionNo.requestFocus();
        AdmissionNo.setText(studentUpdatedetails.get(0).getAdmissionNo());
        StudentName.setText(studentUpdatedetails.get(0).getStudentName());
        FatherName.setText(studentUpdatedetails.get(0).getFatherName());
        MotherName.setText(studentUpdatedetails.get(0).getMotherName());
        Address.setText(studentUpdatedetails.get(0).getAddress());
        Phone.setText(studentUpdatedetails.get(0).getPhone());
        DOB.setText(studentUpdatedetails.get(0).getDOB());
        OcupetionOfFather.setText(studentUpdatedetails.get(0).getOcupetionOfFather());
        RegisterDate.setText(studentUpdatedetails.get(0).getRegisterDate());
        CreatedDate.setText(studentUpdatedetails.get(0).getCreatedDate());
        CreatedBy.setText(studentUpdatedetails.get(0).getCreatedBy());
        UpdatedDate.setText(studentUpdatedetails.get(0).getUpdatedDate());
        UpdatedBy.setText(studentUpdatedetails.get(0).getUpdatedBy());

        BoardId.setText(studentUpdatedetails.get(0).getBoardId());
        IsActive.setText(studentUpdatedetails.get(0).getIsActive());
        IsDeleted.setText(studentUpdatedetails.get(0).getIsDeleted());


        fathereducation.setText(studentUpdatedetails.get(0).getFatherEducation());
        mothereducation.setText(studentUpdatedetails.get(0).getMotherEducation());
        OcupetionOfmother.setText(studentUpdatedetails.get(0).getOcupetionOfFather());
        previousschool.setText(studentUpdatedetails.get(0).getPreviousSchoolName());
        bloodgroup.setText(studentUpdatedetails.get(0).getBloodGroup());
        Email.setText(studentUpdatedetails.get(0).getEmail());
        Sibiling.setText(studentUpdatedetails.get(0).getSibiling());
        emergencyContactNo.setText(studentUpdatedetails.get(0).getEmergencyContactNo());
        adhar.setText(studentUpdatedetails.get(0).getAdharCardNo());


        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_photo_camera_black_24dp)
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);

            Glide.with(StudentAdmission.this).load("http://ratnakar-001-site1.atempurl.com/Studentimage/"+studentUpdatedetails.get(0).getImagePath())
                    .apply(options)
                    .into(img);

        //    Glide.with(StudentAdmission.this).load( "http://ratnakar-001-site1.atempurl.com/Studentimage/"+studentUpdatedetails.get(0).getImagePath()).into(  img);

         //  holder.image.setImageBitmap(convertBase64ToBitmap(filterstaffListModelList.get(position).getImagebase4()));
        }
        catch (Exception e){
            Toast.makeText(StudentAdmission.this,"error no image",0).show();
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.






        if (requestCode == 12 && resultCode == RESULT_OK ) {
            // Let's read picked image data - its URI

            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

//careers@webmazix.com
            String imagePath=""+new File(Environment.getExternalStorageDirectory().toString())+"/temp.jpg";
            Toast.makeText(this, ""+imagePath, Toast.LENGTH_SHORT).show();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);
            int nh = (int) ( bitmap.getHeight() * (112.0 / bitmap.getWidth()) );
            bitmap = Bitmap.createScaledBitmap(bitmap, 112, nh, true);
            img.setImageResource(0);
            img.setImageBitmap(null);
            img.setImageBitmap(bitmap);
            ImageBase4=ConvertBitmapToString(bitmap);

        }




       else if (requestCode == 11 && resultCode == RESULT_OK ) {
            // Let's read picked image data - its URI

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filepath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
            int nh = (int) ( bitmap.getHeight() * (112.0 / bitmap.getWidth()) );
            bitmap = Bitmap.createScaledBitmap(bitmap, 112, nh, true);
          //  Drawable drawable = new BitmapDrawable(bitmap);
           // img.setBackground(drawableToBitmap());
           img.setImageResource(0);
            img.setImageBitmap(null);

           img.setImageBitmap(bitmap);
           ImageBase4=ConvertBitmapToString(bitmap);


        }
    }


    public static String ConvertBitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.classdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion



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

                                classadapter=new ArrayAdapter<>(StudentAdmission.this,android.R.layout.simple_spinner_dropdown_item,classname);
                                spinnerclass.setAdapter(classadapter);
                                if(!ID.equals("0"))
                                {
                                    for(int i=0;i<classid.size();i++){
                                        if(studentUpdatedetails.get(0).getClassId()==classid.get(i))
                                        {
                                            spinnerclass.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                                spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        stuclassid=classid.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                LoadSectionDetails();

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
                        Toast.makeText(StudentAdmission.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentAdmission.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void LoadSectionDetails() {
        //getting the progressbar

        //making the progressbar visible


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllSection,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion



                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.d("ashishsikarwar",response);
                            if(obj.getBoolean("IsSuccess")) {
                                //    Toast.makeText(StudentAdmission.this,""+obj.getBoolean("IsSuccess"),Toast.LENGTH_SHORT).show();
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

                                sectionadapter=new ArrayAdapter<>(StudentAdmission.this,android.R.layout.simple_spinner_dropdown_item,sectionname);
                                spinnersection.setAdapter(sectionadapter);

                                try{
                                    for(int i=0;i<sectionid.size();i++)
                                    {
                                        if(sectionid.get(i).equalsIgnoreCase(studentUpdatedetails.get(0).getSectionId())){
                                            spinnersection.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                                catch (Exception e){}
                                spinnersection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        section=sectionid.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                LoadSessionDetails();

                                //  UpadateFee();
                            }


                            //   Log.d("ashishsikarwar",response);


                        } catch (JSONException e) {
                            Toast.makeText(StudentAdmission.this,""+"error",Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                        //displaying the error in toast if occurrs
                        Toast.makeText(StudentAdmission.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentAdmission.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void LoadSessionDetails() {
        //getting the progressbar

        //making the progressbar visible


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
                                sessionadapter=new ArrayAdapter<>(StudentAdmission.this,android.R.layout.simple_spinner_dropdown_item,sessionname);
                                spinnersession.setAdapter(sessionadapter);
                             //   spinnersession.setSelection(2);

                                if(!ID.equals("0"))
                                {
                                    for(int i=0;i<sessionid.size();i++){
                                        if(studentUpdatedetails.get(0).getSessionId()==sessionid.get(i))
                                        {
                                            spinnersession.setSelection(i);

                                            Log.d("ashishsikarwar1",i+"");
                                            break;
                                        }
                                    }
                                }
                                spinnersession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        stusessionid=sessionid.get(position);
                                     Log.d("ashishsikarwar1",stusessionid);
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
                        Toast.makeText(StudentAdmission.this,"Try Again Later"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentAdmission.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                // touch move code
                break;

            case MotionEvent.ACTION_UP:
                // touch up code
                break;
        }
        return true;
    }
}
