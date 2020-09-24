package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.GallaryAdapter;
import com.example.schoolapp.Adapter.StaffListAdapter;
import com.example.schoolapp.Model.GalleryAchieve;
import com.example.schoolapp.Model.StaffListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GalleryAchievements extends AppCompatActivity implements GallaryAdapter.updategallery {

    FloatingActionButton fab;
    RecyclerView recyclerview;
    ProgressDialog progressDoalog;
    List<GalleryAchieve> galleryAchieveList=new ArrayList<>();
    GallaryAdapter adapter=null;
    GallaryAdapter.updategallery updategallery;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ImageView image;
    String Imagebase4="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_achievements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDoalog=new ProgressDialog(this);
        updategallery=this;
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fab=findViewById(R.id.fab);
        LoadGalleryList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(GalleryAchievements.this);
                dialog.setContentView(R.layout.dialog_gallery);
                final EditText description=dialog.findViewById(R.id.description);
                final EditText title = dialog.findViewById(R.id.title);

                 image=dialog.findViewById(R.id.image);


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= 23 &&
                                ContextCompat.checkSelfPermission( GalleryAchievements.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission( GalleryAchievements.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ) {


                            ActivityCompat.requestPermissions(
                                   GalleryAchievements.this,
                                    PERMISSIONS_STORAGE,
                                    1
                            );

                            return;
                        }

                        final Dialog dialog=new Dialog(GalleryAchievements.this);
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
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GalleryAchievements.this, BuildConfig.APPLICATION_ID + ".provider", f));
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


                Button update=dialog.findViewById(R.id.update);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddGallery("0",title.getText().toString(),"25.12.2020","25.12.2020",
                                "25.12.2020","25.12.2020","1","1",description.getText().toString(),
                                Imagebase4,"ps.jpg");
                       dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }


    private void LoadGalleryList() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Processing....");
        progressDoalog.setTitle("Loading Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllGallAchivement,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressDoalog.dismiss();


                        try {
                            Log.d("ashishsikarwar",response);
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            if(obj.getBoolean("IsSuccess")) {

                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array
                                galleryAchieveList.clear();
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);


                                    galleryAchieveList.add(new GalleryAchieve(
                                            studentObject.getString("ID"),
                                            studentObject.getString("Tittle"),
                                            studentObject.getString("CreatedDate"),
                                            studentObject.getString("CreatedBy"),
                                            studentObject.getString("UpdatedDate"),
                                            studentObject.getString("UpdatedBy"),
                                            studentObject.getString("IsActive"),
                                            studentObject.getString("IsDeleted"),
                                            studentObject.getString("Description"),

                                            studentObject.getString("FileName")

                                            //     studentObject.getString("Imagebase4"),
                                            ));
                                }
                                adapter=new GallaryAdapter(GalleryAchievements.this,galleryAchieveList,updategallery);
                                recyclerview.setAdapter(adapter);

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
                        Toast.makeText(getApplicationContext(), "Network Connection is not working ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void upadateGallery(final String ID,final String titles,final String descriptions,final String imagenames) {
        final Dialog dialog=new Dialog(GalleryAchievements.this);
        dialog.setContentView(R.layout.dialog_gallery);
        final EditText description=dialog.findViewById(R.id.description);
        final EditText title = dialog.findViewById(R.id.title);
        description.setText(descriptions);
        title.setText(titles);
        image=dialog.findViewById(R.id.image);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( GalleryAchievements.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission( GalleryAchievements.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


                    ActivityCompat.requestPermissions(
                            GalleryAchievements.this,
                            PERMISSIONS_STORAGE,
                            1
                    );

                    return;
                }

                final Dialog dialog=new Dialog(GalleryAchievements.this);
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
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GalleryAchievements.this, BuildConfig.APPLICATION_ID + ".provider", f));
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


        Button update=dialog.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGallery(ID,title.getText().toString(),"25.12.2020","25.12.2020",
                        "25.12.2020","25.12.2020","1","1",
                        description.getText().toString(), Imagebase4,"as.jpg");

                dialog.dismiss();
            }
        });

        dialog.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (requestCode == 12 && resultCode ==  RESULT_OK ) {
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
            Toast.makeText(GalleryAchievements.this, ""+imagePath, Toast.LENGTH_SHORT).show();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bp = BitmapFactory.decodeFile(imagePath, options);
            bp.compress(Bitmap.CompressFormat.JPEG, 10, bos);

            //     Log.d("ashishsikarwar", ConvertBitmapToString(bp));
            // StaffRegistrationFragment.  img.setImageBitmap(convertBase64ToBitmap( ConvertBitmapToString(bp)));



            int nh = (int) ( bp.getHeight() * (112.0 / bp.getWidth()) );
            bp = Bitmap.createScaledBitmap(bp, 112, nh, true);

            image.setImageBitmap(bp);
            Imagebase4=ConvertBitmapToString(bp);



        }




        else if (requestCode == 11 && resultCode ==  RESULT_OK ) {
            // Let's read picked image data - its URI

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor =  getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filepath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(filepath);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            //     Log.d("ashishsikarwar", ConvertBitmapToString(bp));
            // StaffRegistrationFragment.  img.setImageBitmap(convertBase64ToBitmap( ConvertBitmapToString(bp)));



            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
            bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            //  img.setImageBitmap(convertBase64ToBitmap( ConvertBitmapToString(bitmap)));
            image.setImageBitmap(bitmap);
            Imagebase4=ConvertBitmapToString(bitmap);
        }

    }

    public  String ConvertBitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public void AddGallery( final String ID,final String Tittle,final String CreatedDate,
                          final String CreatedBy,
                          final String UpdatedDate,final String UpdatedBy,final String IsActive,final String IsDeleted,
                          final String Description, final String Imagebase4,final String FileName  ){

        progressDoalog.setMessage("Processing....");
        progressDoalog.setTitle("Loading Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.InsertGallaryAchivemebtdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            progressDoalog.dismiss();
                            //converting response to json object
                            Log.d("ashishsikarwar",response);
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(GalleryAchievements.this, obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            //   if(obj.getBoolean("IsSuccess"))
                            LoadGalleryList();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        Toast.makeText(GalleryAchievements.this,"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("ID",ID );
                params.put("Tittle",Tittle);
                params.put("CreatedDate",CreatedDate);
                params.put("CreatedBy",CreatedBy);
                params.put("UpdatedDate",UpdatedDate);
                params.put("UpdatedBy",UpdatedBy);
                params.put("IsActive",IsActive);
                params.put("IsDeleted",IsDeleted);
                params.put("Description",Description);
                params.put("Imagebase4",Imagebase4);
                params.put("FileName",FileName);
                return params;
            }
        };

        VolleySingleton.getInstance(GalleryAchievements.this).addToRequestQueue(stringRequest);
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
