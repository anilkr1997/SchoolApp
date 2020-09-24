package com.example.schoolapp.aboutschool;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AddSchool extends Fragment {
    TextView schoolname,schoolprincplename,schoolurl,schoolmail,schoolnumber,schoolwonername,SchoolAddress,
            Boardid,SchoolAbout;
    Button registation;
    CircleImageView circleImageView;
    ProgressDialog progressDoalog;
    String Sname,Semail,Sphone,Sadress,imagepath="",Sprincplename,Surl,Snumber,Swonername,SBoardid,SAbout;
    String id="0";
    String imageEncoded;
    Dialog dialog;
    String Scname,Scemail,Scphone,Scadress,Imagepath,Scprincplename,Scurl,Scnumber,Scwonername,ScBoardid,ScAbout;
    private static final int CAMERA_REQUEST=123;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bmp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
View itemView=inflater.inflate(R.layout.fragment_add_school, container, false);
        schoolname=itemView.findViewById(R.id.SchoolName);
       // schoolid=itemView.findViewById(R.id.Schoolid);
        schoolprincplename=itemView.findViewById(R.id.SchoolPrincipalName);
        schoolurl=itemView.findViewById(R.id.SchoolwebsiteUrl);
        schoolmail=itemView.findViewById(R.id.SchoolEmail);
        schoolnumber=itemView.findViewById(R.id.SchoolPhone);
        schoolwonername=itemView.findViewById(R.id.SchoolOwnerName);
        SchoolAddress=itemView.findViewById(R.id.SchoolAddress);
        Boardid=itemView.findViewById(R.id.SchoolBoardID);
        circleImageView=itemView.findViewById(R.id.ImagePath);
        SchoolAbout=itemView.findViewById(R.id.AboutSchool);

        fragmentdata();
        registation=itemView.findViewById(R.id.registation);
        registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(schoolname.getText().toString().isEmpty()){
                      schoolname.setError(" ");
                } if(schoolprincplename.getText().toString().isEmpty()){
                    schoolprincplename.setError(" ");
                } if(schoolmail.getText().toString().isEmpty()){
                    schoolmail.setError(" ");
                } if(schoolnumber.getText().toString().isEmpty()){
                    schoolnumber.setError(" ");
                } if(SchoolAddress.getText().toString().isEmpty()){
                    SchoolAddress.setError(" ");
                } if(Boardid.getText().toString().isEmpty()){
                    Boardid.setError(" ");
                } if(schoolwonername.getText().toString().isEmpty()){
                    schoolwonername.setError(" ");
                } if(SchoolAbout.getText().toString().isEmpty()){
                    SchoolAbout.setError(" ");
                }
                else{
                    intt();
                    registerClass(id);
                }
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBox1();
            }
        });
        return itemView ;
    }

    private void fragmentdata() {
        try{
            Bundle args = getArguments();
            id= args.getString("ID");
            Scname= args.getString("SchoolName");
            Scemail= args.getString("Email");
            Scphone= args.getString("PhoneNo");
            Scadress= args.getString("Address");
            Scprincplename= args.getString("PrincipalName");
            Scurl= args.getString("websiteUrl");
            Scwonername= args.getString("OwnerName");
            ScBoardid= args.getString("BoardID");
            ScAbout= args.getString("AboutSchool");
            schoolname.setText(Scname);
           //schoolname.setEnabled(false);
            schoolprincplename.setText(Scprincplename);
            schoolmail.setText(Scemail);
            schoolnumber.setText(Scphone);
            SchoolAddress.setText(Scadress);
            Boardid.setText(ScBoardid);
            schoolwonername.setText(Scwonername);
            SchoolAbout.setText(ScAbout);
            schoolurl.setText(Scurl);
        }
        catch (Exception e){
            id="0";
        }
    }
    private void registerClass(final String id) {
        progressDoalog=new ProgressDialog(getActivity());
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_InsertSchoolInfodetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("myresponse",response);
                        progressDoalog.dismiss();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.e("rsp",obj.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Log.e("rsp",error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID",id);
                params.put("SchoolName",Sname);
                params.put("Address",Sadress);
                params.put("OwnerName",Swonername);
                params.put("PrincipalName",Sprincplename);
                params.put("AboutSchool",SAbout);
                params.put("PhoneNo",Sphone);
                params.put("Email",Semail);
                params.put("ImagePath",imagepath);
                params.put("BoardID",SBoardid);
                params.put("websiteUrl",Surl);
                Log.e("Anil",params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void intt() {
        Sname =schoolname.getText().toString();
        Semail=schoolmail.getText().toString();
        Sphone=schoolnumber.getText().toString();
        Sadress=SchoolAddress.getText().toString();
        Sprincplename=schoolprincplename.getText().toString();
        Surl=schoolurl.getText().toString();
        imageEncoded=imagepath;
        Swonername=schoolwonername.getText().toString();
        SBoardid=Boardid.getText().toString();
        SAbout=SchoolAbout.getText().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK && null != data) {

            if (requestCode == 123) {
                bmp = (Bitmap)data.getExtras().get("data");
                circleImageView.setImageBitmap(bmp);
                int nh = (int) ( bmp.getHeight() * (100.0 / bmp.getWidth()) );
                bmp=Bitmap.createScaledBitmap(bmp,100,nh,true);
                encodeTobase64(bmp);
                // uploadImage(bitmap);
            }
            else if (requestCode == 1) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor =getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                try {
                    bmp = getBitmapFromUri(selectedImage);
                    int nh = (int) ( bmp.getHeight() * (100.0 / bmp.getWidth()) );
                    bmp=Bitmap.createScaledBitmap(bmp,100,nh,true);
                    encodeTobase64(bmp);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                circleImageView.setImageBitmap(bmp);
            }
        }

    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity(). getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap circleImageView = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return circleImageView;
    }
    public String encodeTobase64(Bitmap image) {
        Bitmap bitmap_image = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap_image.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] b = baos.toByteArray();

        imagepath= Base64.encodeToString(b, Base64.DEFAULT);
     //   imagepath="ashishsikarwar";
        return imagepath;
    }


    public void saveBox1() {
        dialog = new Dialog(getActivity());
        // dialog .create();
        dialog.show();
        dialog.setContentView(R.layout.dilogbox);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.setCancelable(false);
        dialog.setTitle("");
        Button browser = (Button) dialog.findViewById(R.id.popbrowser);
        Button camera  = (Button) dialog.findViewById(R.id.popcamera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                    }
                    else
                    {
                        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(photoCaptureIntent,CAMERA_REQUEST );

                    }
                }
                dialog.dismiss();
            }


        });
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                dialog.dismiss();

            }

        });

    }


}
