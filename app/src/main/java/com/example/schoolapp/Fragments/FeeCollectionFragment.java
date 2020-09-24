package com.example.schoolapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.FeeCollectionAdapter;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FeeCollectionFragment extends Fragment implements FeeCollectionAdapter.Sharefee {
    RecyclerView recyclerview;
    View view;
    List<FeeCollectionModel> feeCollectionModelList=new ArrayList<>();
    FeeCollectionAdapter adapter;
    FeeCollectionAdapter.Sharefee sharefee;
    ProgressDialog progressDoalog;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.fragment_fee_collection, container, false);
        sharefee=this;
        recyclerview=view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        getFeeDetails();

  return view;
    }


    @Override
    public void ShareFeeDetails(String id_,String name_,String father_, String admissionno_ ,String classid_,
                                String  date_,String month_,String amount_,String session_,
                                String submitby_,String updatedby_) {
         dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.customfeecollection);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        TextView name,Class,father,admission,date,month,amount,session,submitby,updatedby;
        final Button share;
        final LinearLayout lin;
        lin=dialog.findViewById(R.id.lin);

        name=dialog.findViewById(R.id.name);
        Class=dialog.findViewById(R.id.Class);
        father=dialog.findViewById(R.id.father);
        admission=dialog.findViewById(R.id.admission);
        date=dialog.findViewById(R.id.date);
        month=dialog.findViewById(R.id.month);
        amount=dialog.findViewById(R.id.amount);
        session=dialog.findViewById(R.id.session);
        submitby=dialog.findViewById(R.id.submitby);
        updatedby=dialog.findViewById(R.id.updatedby);
        share=dialog.findViewById(R.id.share);
        name.setText(name_);
        father.setText(father_);
        admission.setText(admissionno_);
        Class.setText(classid_);
        date.setText(date_);
        month.setText(month_);
        amount.setText(amount_);
        session.setText(session_);
        submitby.setText(submitby_);
        updatedby.setText(updatedby_);
       share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               share.setVisibility(View.GONE);

               saveImage(loadBitmapFromView(lin));
               send();
           }
       });
        dialog.show();

    }


    private void getFeeDetails() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog=new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.getffecollection,
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
                                feeCollectionModelList.clear();
                                adapter=null;
                                JSONArray stuarray = obj.getJSONArray("ResponseData");

//
//                            //now looping through all the elements of the json array

                              //  for (int i = 0; i < stuarray.length(); i++) {
                                    for(int j=stuarray.length()-1;j>=0;j--){
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(j);
                                  feeCollectionModelList.add(new FeeCollectionModel( studentObject.getString("ID"),
                                          "",
                                          "", studentObject.getString("PaymentModeId"),
                                          studentObject.getString("FeeTypeId"),
                                          studentObject.getString("SubmissionDate"),
                                          studentObject.getString("Month"),
                                          studentObject.getString("Amount"),
                                          studentObject.getString("SessionId"),
                                          studentObject.getString("SectionId"),
                                          studentObject.getString("ClassId"),
                                          studentObject.getString("AdmissionNo"),
                                          studentObject.getString("SubmitedBy"),
                                          studentObject.getString("UpdatedBy"),
                                          studentObject.getString("UpdatedOn")));

                                }
                               adapter=new FeeCollectionAdapter(sharefee,feeCollectionModelList,getActivity());
                                recyclerview.setAdapter(adapter);


                                //  UpadateFee();
                            }





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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
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
            dialog.dismiss();
        } catch (Exception e) {
            // Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(1000, 700, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}
