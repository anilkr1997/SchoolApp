package com.example.schoolapp;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import com.example.schoolapp.Helper.ImageHelper;
import com.example.schoolapp.Model.StudentUpdatedetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateIdCard extends AppCompatActivity {

    LinearLayout id_card;
    Button save,share;
    ImageView image,img;
    TextView StudentName,FatherName,Address,Phone,DOB,SessionId,ClassId,bloodgroup;
    ProgressDialog progressDoalog;
    List<StudentUpdatedetails>  studentUpdatedetails=new ArrayList<>();
    String ID;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generateidard);
        progressDoalog=new ProgressDialog(this);
        progressDoalog.setTitle("Loading");
        progressDoalog.setMessage("Generating ID Card ");
        progressDoalog.show();
        ID=getIntent().getExtras().getString("ID");
        id_card=findViewById(R.id.id_card);
        save=findViewById(R.id.save);
        share=findViewById(R.id.share);
        image=findViewById(R.id.image);
        img=findViewById(R.id.img);

        StudentName=findViewById(R.id.StudentName);
        FatherName=findViewById(R.id.FatherName);
        Address=findViewById(R.id.Address);
        Phone=findViewById(R.id.Phone);
        DOB=findViewById(R.id.DOB);
        SessionId=findViewById(R.id.SessionId);
        ClassId=findViewById(R.id.ClassId);
        bloodgroup=findViewById(R.id.bloodgroup);



        LoadStudentList();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitMap(GenerateIdCard.this,id_card);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b=getBitmapFromView(id_card);
                String bitmapPath = MediaStore.Images.Media.insertImage(GenerateIdCard.this.getContentResolver(), b,"title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                startActivity(Intent.createChooser(intent, "Share"));

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( GenerateIdCard.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission( GenerateIdCard.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


                    ActivityCompat.requestPermissions(
                            GenerateIdCard.this,
                            PERMISSIONS_STORAGE,
                            1
                    );

                    return;
                }

                final Dialog dialog=new Dialog(GenerateIdCard.this);
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
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GenerateIdCard .this, BuildConfig.APPLICATION_ID + ".provider", f));
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


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( GenerateIdCard.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission( GenerateIdCard.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


                    ActivityCompat.requestPermissions(
                            GenerateIdCard.this,
                            PERMISSIONS_STORAGE,
                            1
                    );

                    return;
                }

                final Dialog dialog=new Dialog(GenerateIdCard.this);
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
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GenerateIdCard .this, BuildConfig.APPLICATION_ID + ".provider", f));
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

    }



    private File saveBitMap(Context context, View drawView){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Handcare");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }
    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it

        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                                UpadateIdCard();
                                progressDoalog.dismiss();
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

    private void UpadateIdCard() {
       /* StudentName=findViewById(R.id.StudentName);
        FatherName=findViewById(R.id.FatherName);
        Address=findViewById(R.id.Address);
        Phone=findViewById(R.id.Phone);
        DOB=findViewById(R.id.DOB);
        SessionId=findViewById(R.id.SessionId);
        ClassId=findViewById(R.id.ClassId);*/
        StudentName.setText(studentUpdatedetails.get(0).getStudentName());
        FatherName.setText(studentUpdatedetails.get(0).getFatherName());
        Address.setText(studentUpdatedetails.get(0).getAddress());
        Phone.setText(studentUpdatedetails.get(0).getPhone());
        DOB.setText(studentUpdatedetails.get(0).getDOB());
        ClassId.setText(studentUpdatedetails.get(0).getClassId());
        bloodgroup.setText(studentUpdatedetails.get(0).getBloodGroup());
        SessionId.setText(studentUpdatedetails.get(0).getSessionId());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.images)
                .error(R.drawable.images)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
      //  ImageHelper.getRoundedCornerBitmap(icon,100);
        Glide.with(GenerateIdCard.this).load("http://ratnakar-001-site1.atempurl.com/Studentimage/"+studentUpdatedetails.get(0).getImagePath())
                .apply(options)
                .into(image);
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
           bitmap= ImageHelper.getRoundedCornerBitmap(bitmap,100);

            img.setVisibility(View.VISIBLE);
            img.setImageBitmap(bitmap);
           image.setVisibility(View.GONE);

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
            bitmap= ImageHelper.getRoundedCornerBitmap(bitmap,100);
            img.setVisibility(View.VISIBLE);
            img.setImageBitmap(bitmap);
            image.setVisibility(View.GONE);


        }
    }

}
