package com.example.schoolapp.Transport.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VeheclesRegistation extends Fragment {

EditText vechelnumber,vechelRCnumber,wonername,wonernumber,wonerAddres;
Button vechileregistation;
Spinner spinner;
    ProgressDialog progressDoalog;
    String vnumber;
    String vrcnumber;
    String wname;
    String wnumber;
    String waddres;
    String VehicleTypeid="1";
    String VehicleN,RcNo,WonerName,WonerAddress,PhoneNo;
    String id="0";
    String a="0";
    private ArrayList<String> vechile;
    private ArrayList<String> ids;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_transportfragment, container, false);
       vechelnumber=view.findViewById(R.id.vachilenumber);
       vechelRCnumber=view.findViewById(R.id.Rcdetail);
        wonername=view.findViewById(R.id.wonername);
        wonernumber=view.findViewById(R.id.wonernumbedr);
        wonerAddres=view.findViewById(R.id.woneraddres);
        progressDoalog=new ProgressDialog(getActivity());
       spinner=view.findViewById(R.id.vecheltype);
       vechileregistation=view.findViewById(R.id.vechileregistation);
        vechile=new ArrayList<String>();
        ids=new ArrayList<String>();
        fragmentdata();
        getvechiletype();
        vechileregistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(vechelnumber.getText().toString().isEmpty()){
                    vechelnumber.setError(" "); }
                else if(vechelRCnumber.getText().toString().isEmpty()){
                    vechelRCnumber.setError(" "); }
                else if(wonername.getText().toString().isEmpty()){
                    wonername.setError(" "); }
                else if(wonernumber.getText().toString().isEmpty()){
                    wonernumber.setError(" "); }
                else if(wonerAddres.getText().toString().isEmpty()){
                    wonerAddres.setError(" "); }
                else{
                     intt();
                    registerClass(id);
                }
            }});
        return view;
    }
    private void fragmentdata() {
        try{
            Bundle args = getArguments();
            id= args.getString("id");
            VehicleTypeid= args.getString("VehicleTypeId");
            VehicleN= args.getString("VehicleNo");
            RcNo= args.getString("RcNo");
            WonerName= args.getString("WonerName");
            WonerAddress= args.getString("WonerAddress");
            PhoneNo= args.getString("PhoneNo");
            wonername.setText(WonerName);
            vechelnumber.setText(VehicleN);
            vechelRCnumber.setText(RcNo);
            wonernumber.setText(PhoneNo);
            wonerAddres.setText(WonerAddress);
        }
        catch (Exception e){
          id="0";
        }
    }
    private void intt() {
        vnumber=vechelnumber.getText().toString();
        vrcnumber=vechelRCnumber.getText().toString();
        wname=wonername.getText().toString();
        wnumber=wonernumber.getText().toString();
        waddres=wonerAddres.getText().toString();
    }
    public void registerClass(final String id) {
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_Vechale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDoalog.dismiss();
                    }
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
                params.put("Id",id);
                params.put("VehicleTypeId",VehicleTypeid);
                params.put("VehicleNo",vnumber);
                params.put("RcNo",vrcnumber);
                params.put("WonerName",wname);
                params.put("WonerAddress",waddres);
                params.put("PhoneNo",wnumber);
                Log.e("Anil",params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    public void getvechiletype() {
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_getVechaletype,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDoalog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("Anil",response);
                            if(obj.getBoolean("IsSuccess")) {
                                JSONArray stuarray = obj.getJSONArray("ResponseData");
                                for (int i = 0; i < stuarray.length(); i++) {

                                    JSONObject studentObject = stuarray.getJSONObject(i);

                                    vechile.add(studentObject.getString("VehicalTypename"));
                                    ids.add(studentObject.getString("ID"));

                                }
                            }
                            spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vechile));
                             //setspinner id and vechileid
                            int spincount=0;
                            for(int i=0;i<ids.size();i++){
                                if(ids.get(i).equals(VehicleTypeid))
                                    break;
                                spincount=spincount+1;
                            }
                            try{
                                if(ids.size()>spincount)
                                spinner.setSelection(spincount);
                                else{
                                    spincount=0;
                                    spinner.setSelection(spincount);
                                }
                            }
                            catch (Exception e){
                                Log.d("ashishsikarwar",e+"");
                                spinner.setSelection(0);
                            }
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    VehicleTypeid= ids.get(position); }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) { }
                            });

                        } catch (Exception e) { e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                          progressDoalog.dismiss();
                        Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) ;
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}

