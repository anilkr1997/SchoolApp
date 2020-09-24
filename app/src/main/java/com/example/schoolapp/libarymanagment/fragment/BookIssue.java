package com.example.schoolapp.libarymanagment.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.schoolapp.R;
import com.example.schoolapp.RecyclerTouchListener;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;
import com.example.schoolapp.interfaces.ClickListener;
import com.example.schoolapp.libarymanagment.Adopter.BookIsueAdopter;
import com.example.schoolapp.libarymanagment.modelclass.issueItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookIssue extends Fragment {
    RecyclerView recyclerview;
    List<issueItem> Item;
    FloatingActionButton floatingActionButton;
    Fragment fragment;
    ProgressDialog progressDoalog;
    BookIsueAdopter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_book_issue, container, false);
        recyclerview=view.findViewById(R.id.listofVichel);
        floatingActionButton=view.findViewById(R.id.fab);
        progressDoalog=new ProgressDialog(getActivity());
        Item=new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        registerClass();
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                fragment=new Issuebookstu();
                final Bundle bundle=new Bundle();
                bundle.putString("ID",Item.get(position).getID());
                bundle.putString("StudentId",Item.get(position).getStudentId());
                bundle.putString("BookId",Item.get(position).getBookId());
                bundle.putString("issueedDate",Item.get(position).getIssueedDate());
                bundle.putString("Issuedforday",Item.get(position).getIssuedforday());
                bundle.putString("SubmissionDate",Item.get(position).getSubmissionDate());
                bundle.putString("IssuedBy",Item.get(position).getIssuedBy());
                bundle.putString("Remarks",Item.get(position).getRemarks());
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new Issuebookstu();
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
       return view;
    }

    private void registerClass() {
        progressDoalog=new ProgressDialog(getActivity());
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetAllBookIssue  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myresponse",response);
                        progressDoalog.dismiss();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Item.clear();
                            customAdapter=null;
                            if(obj.getString("IsSuccess").equalsIgnoreCase("true")){
                                JSONArray object=obj.getJSONArray("ResponseData");
                                for(int i=0; i<object.length();i++){
                                    JSONObject jsonObject= (JSONObject) object.get(i);
                                    Item.add(new issueItem(
                                            jsonObject.getString("ID"),
                                            jsonObject.getString("StudentId"),
                                            jsonObject.getString("BookId"),
                                            jsonObject.getString("issueedDate"),
                                            jsonObject.getString("Issuedforday"),
                                            jsonObject.getString("SubmissionDate"),
                                            jsonObject.getString("IssuedBy"),
                                            jsonObject.getString("Remarks")
                                    ));
                                }
                                customAdapter=new BookIsueAdopter(getActivity(),Item);
                                recyclerview.setAdapter(customAdapter);

                            }
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

                Log.e("Anil",params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
