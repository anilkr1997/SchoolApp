package com.example.schoolapp.Transport.Fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


public class DriverRegistation extends Fragment {
    EditText DriverName,Email,Phone,Address,licenceNo,licenceviladationdate;
    CircleImageView circleImageView;
    Button registation;
    ProgressDialog progressDoalog;
    String drivername,driveremail,driverphone,driveradress,imagepath,Licenceno,validedate,lancencetype;
    String id="0";
    Dialog dialog;
    String Drname,Drphone,Dremail,Dradders,licnumber,validate,laincetype;
    private static final int CAMERA_REQUEST=123;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bmp;
    String imageEncoded;
    AutoCompleteTextView actv;
    String[] licencetype = { "Private", "Commercial", "Private Automatic", "Commercial with Badge", "Commercial Heavy "};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_driver_registation, container, false);
         actv = view.findViewById(R.id.licencetype);
         DriverName=view.findViewById(R.id.DriverName);
         Email=view.findViewById(R.id.DriverEmail);
         Phone=view.findViewById(R.id.DriverPhone);
         Address=view.findViewById(R.id.DriverAddress);
         licenceNo=view.findViewById(R.id.licencenumber);
         licenceviladationdate=view.findViewById(R.id.Validationdate);
        circleImageView=view.findViewById(R.id.ImagePath);
        registation=view.findViewById(R.id.vechileregistation);
        fragmentdata();
        registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DriverName.getText().toString().isEmpty()){
                    DriverName.setError(" "); }
                else if(Phone.getText().toString().isEmpty()){
                    Phone.setError(" "); }
                else if(licenceNo.getText().toString().isEmpty()){
                    licenceNo.setError(" "); }
                else if(licenceviladationdate.getText().toString().isEmpty()){
                    licenceviladationdate.setError(" "); }

                else{

                    intt();
                    registerClass(id);
                }
            }});
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBox1();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, licencetype);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "Selected Item: " + position, Toast.LENGTH_SHORT).show();
               // lancencetype=getString(position);

            }
        });
        return view;
    }
    private void fragmentdata() {
        try{
            Bundle args = getArguments();
            id= args.getString("id");
            Drname= args.getString("DriverName");
            Drphone= args.getString("Phone");
            Dremail= args.getString("Email");
            Dradders= args.getString("Address");
            licnumber= args.getString("LicenceNo");
            validate= args.getString("LicenceValideDate");
            laincetype=args.getString("LicenceType");
            DriverName.setText(Drname);
            Email.setText(Dremail);
            Phone.setText(Drphone);
            Address.setText(Dradders);
            licenceNo.setText(licnumber);
            licenceviladationdate.setText(validate);
            actv.setText(laincetype);


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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_InsertDriverDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myresponse",response);
                        progressDoalog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("rsp",obj.toString());



                        } catch (Exception e) {
                            e.printStackTrace();
                        }                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID",id);
                params.put("DriverName",drivername);
                params.put("Email",driveremail);
                params.put("Phone",driverphone);
                params.put("Address",driveradress);
                params.put("ImagePath",imageEncoded);
                params.put("UpdatedBy","anilimage");
                params.put("CreatedDate","2019-12-31");
                params.put("CreatedBy","anilimage");
                params.put("UpadatedDate","2019-12-31");
                params.put("LicenceNo",Licenceno);
                params.put("LicenceValideDate",validedate);
                params.put("LicenceType",lancencetype);

                Log.e("Anil",params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    private void intt() {
        drivername=DriverName.getText().toString();
        driverphone=Phone.getText().toString();
        driveremail=Email.getText().toString();
       // driveradress=VehicleTypeid;
        driveradress=Address.getText().toString();
        Licenceno=licenceNo.getText().toString();
        validedate=licenceviladationdate.getText().toString();
        lancencetype=actv.getText().toString();
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
                    if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
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
        bitmap_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


}
