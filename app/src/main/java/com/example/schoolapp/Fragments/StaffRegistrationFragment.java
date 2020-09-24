package com.example.schoolapp.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.schoolapp.Adapter.AddStaffAdapter;
import com.example.schoolapp.BuildConfig;
import com.example.schoolapp.Model.AddStaffModel;
import com.example.schoolapp.Model.StaffListModel;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffRegistrationFragment extends Fragment {
    View view;
    EditText Name,FatherName,MotherName,Address,Phone,email,DOB,RegisterDate;
    Spinner spinnerprofile;
    Button save;
    ProgressDialog progressDoalog;
   public static ImageView img;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String Imagebase4="";
    String formattedDate;
    List<String> EmploeeTypeName=new ArrayList<>();
    List<String> ids=new ArrayList<>();
    ArrayAdapter<String> adapter;
    String profle="1";
    String id="0";
    String imagepath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_staff_registration, container, false);
      
     
       // Toast.makeText(getActivity(),a+ "", Toast.LENGTH_SHORT).show();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        formattedDate = df.format(c);
        progressDoalog=new ProgressDialog(getActivity());
        Name=view.findViewById(R.id.Name);


        FatherName=view.findViewById(R.id.FatherName);
        MotherName=view.findViewById(R.id.MotherName);
        Address=view.findViewById(R.id.Address);
        Phone=view.findViewById(R.id.Phone);
        email=view.findViewById(R.id.email);
        DOB=view.findViewById(R.id.DOB);
        RegisterDate=view.findViewById(R.id.RegisterDate);
        spinnerprofile=view.findViewById(R.id.spinnerprofile);
        save=view.findViewById(R.id.save);
        img=view.findViewById(R.id.img);
        String n="";
   //  img.setImageBitmap(convertBase64ToBitmap(n));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(  getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


                    ActivityCompat.requestPermissions(
                             getActivity(),
                            PERMISSIONS_STORAGE,
                            1
                    );

                    return;
                }

                final Dialog dialog=new Dialog( getActivity());
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
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f));
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
                   return;
               }
               AddStaff(id,Name.getText().toString(),FatherName.getText().toString(),MotherName.getText().toString(),
                     profle,Address.getText().toString(),Phone.getText().toString(),email.getText().toString(),
                    DOB.getText().toString(),"",Imagebase4,formattedDate,"Ashish","Ashish",
                       formattedDate,"true","false"
               );


           }
       });


        LoadStaffProfile();

   return view;
    }

    private void fillEdittext() {
       // Name,FatherName,MotherName,Address,Phone,email,DOB,RegisterDate
      /*  bundle.putString("name", );
        bundle.putString("father", father);
        bundle.putString("mother", mother);
        bundle.putString("Add", Add);
        bundle.putString("Phone", Phone);
        bundle.putString("email", email);
        bundle.putString("dob", dob);
        bundle.putString("registerdate", registerdate);*/
        id=  getArguments().getString("id");
        Name.setText(getArguments().getString("name"));
        FatherName.setText(getArguments().getString("father"));
        MotherName.setText(getArguments().getString("mother"));
        Address.setText(getArguments().getString("Add"));
        Phone.setText(getArguments().getString("Phone"));
        email.setText(getArguments().getString("email"));
        DOB.setText(getArguments().getString("dob"));
        RegisterDate.setText(getArguments().getString("registerdate"));
        imagepath=getArguments().getString("imagepath");
        try{
        Glide.with(getActivity()).load( "http://ratnakar-001-site1.atempurl.com/empimage/"+imagepath).into(  img);}
        catch (Exception e){

        }

        int spincount=0;
        for(int i=0;i<ids.size();i++){
            if(ids.get(i).equalsIgnoreCase( getArguments().getString("profile"))){
                break;
            }
            spincount=spincount+1;
        }
        try{
            spinnerprofile.setSelection(spincount);
        }
        catch (Exception e){
            spinnerprofile.setSelection(0);
        }

    }

    public boolean validate() {
        boolean valid = true;

        if ( Name.getText().toString().isEmpty()  ) {
            Name.setError("Enter Name");
            valid = false;
        }else {
            Name.setError(null);
        }

        if ( FatherName.getText().toString().isEmpty()  ) {
            FatherName.setError("Enter FatherName");
            valid = false;
        }else {
            FatherName.setError(null);
        }

        if ( MotherName.getText().toString().isEmpty()) {
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
        if ( email.getText().toString().isEmpty()  ) {
            email.setError("Enter email");
            valid = false;
        } else {
            email.setError(null);
        }
        if ( DOB.getText().toString().isEmpty()) {
            DOB.setError("Enter AdmissionNo");
            valid = false;
        } else {
            DOB.setError(null);
        }

        if ( RegisterDate.getText().toString().isEmpty()  ) {
            RegisterDate.setError("Enter RegisterDate");
            valid = false;
        } else {
            RegisterDate.setError(null);
        }




        return valid;
    }

    public void AddStaff( final String id,final String EmpName,final String FatherName,
            final String MotherName,
            final String EmpTypeId,final String Address,final String Phone,final String Email,
            final String DOJ, final String ImagePath,final String Imagebase4,final String CreatedDate, final String CreatedBy,
            final String UpdatedBy,final String UpdatedDate, final String IsActive, final String IsDeleted){
        progressDoalog.setMessage("Session is Updating..");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertEmployeedetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                         //   if(obj.getBoolean("IsSuccess"))
                               // LoadStaffProfile();


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

                params.put("Id",id );
                params.put("EmpName",EmpName);
                params.put("FatherName",FatherName);
                params.put("MotherName",MotherName);
                params.put("EmpTypeId",EmpTypeId);
                params.put("Address",Address);
                params.put("Phone",Phone);
                params.put("Email",Email);
                params.put("DOJ",DOJ);
                params.put("ImagePath",ImagePath);
                params.put("Imagebase4",Imagebase4);
                params.put("CreatedDate",CreatedDate);
                params.put("CreatedBy",CreatedBy);

                params.put("UpdatedBy",UpdatedBy);
                params.put("UpdatedDate",UpdatedDate);
                params.put("IsActive",IsActive);
                params.put("IsDeleted",IsDeleted);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (requestCode == 12 && resultCode == getActivity().RESULT_OK ) {
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
            Toast.makeText(getActivity(), ""+imagePath, Toast.LENGTH_SHORT).show();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bp = BitmapFactory.decodeFile(imagePath, options);
            bp.compress(Bitmap.CompressFormat.JPEG, 10, bos);

       //     Log.d("ashishsikarwar", ConvertBitmapToString(bp));
           // StaffRegistrationFragment.  img.setImageBitmap(convertBase64ToBitmap( ConvertBitmapToString(bp)));



            int nh = (int) ( bp.getHeight() * (112.0 / bp.getWidth()) );
             bp = Bitmap.createScaledBitmap(bp, 112, nh, true);

             img.setImageBitmap(bp);
            Imagebase4=ConvertBitmapToString(bp);
         Log.d("ashishsikarwar",  ConvertBitmapToString(bp));


        }




        else if (requestCode == 11 && resultCode == getActivity().RESULT_OK ) {
            // Let's read picked image data - its URI

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filepath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(filepath);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);

            //     Log.d("ashishsikarwar", ConvertBitmapToString(bp));
            // StaffRegistrationFragment.  img.setImageBitmap(convertBase64ToBitmap( ConvertBitmapToString(bp)));



            int nh = (int) ( bitmap.getHeight() * (112.0 / bitmap.getWidth()) );
            bitmap = Bitmap.createScaledBitmap(bitmap, 112, nh, true);
          //  img.setImageBitmap(convertBase64ToBitmap( ConvertBitmapToString(bitmap)));
           img.setImageBitmap(bitmap);
            Imagebase4=ConvertBitmapToString(bitmap);
        }

}

    public static String ConvertBitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    public class MyTask extends AsyncTask<Bitmap,Void,String>{



        @Override
        protected String doInBackground(Bitmap... bitmaps) {
          String n=  ConvertBitmapToString(bitmaps[0]);
            return n;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ashishsikarwar",s);
        //    Toast.makeText(getActivity(),""+s,0).show();
        }
    }
    private void LoadStaffProfile() {

        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllEmployeeType,
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
                                EmploeeTypeName.clear();
                                ids.clear();
                                adapter=null;
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);
                                    EmploeeTypeName.add( studentObject.getString("EmploeeTypeName"));
                                    ids.add( studentObject.getString("ID"));

                                    Log.d("ashishsikarwar", studentObject.getString("ID"));


                                }
                                adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,EmploeeTypeName);
                                spinnerprofile.setAdapter(adapter);
                                Log.d("ashishsikarwar", obj.getBoolean("IsSuccess")+"");
                                //  UpadateFee();
                                spinnerprofile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        profle=ids.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                try{
                                    getArguments().getString("id");
                                    fillEdittext();}
                                catch (Exception e){}
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
}
