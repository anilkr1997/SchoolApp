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
import com.example.schoolapp.libarymanagment.Adopter.BooklistAdopter;
import com.example.schoolapp.libarymanagment.modelclass.bookLIstItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Booklist extends Fragment {
    RecyclerView recyclerview;
    List<bookLIstItem> bookLIstItems;
    FloatingActionButton floatingActionButton;
    Fragment fragment;
    ProgressDialog progressDoalog;
    BooklistAdopter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
                View view =inflater.inflate(R.layout.fragment_booklist, container, false);
        recyclerview=view.findViewById(R.id.listofVichel);
        floatingActionButton=view.findViewById(R.id.fab);
        progressDoalog=new ProgressDialog(getActivity());
        bookLIstItems=new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        registerClass();
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                fragment=new Addbook();
                final Bundle bundle=new Bundle();
                bundle.putString("ID",bookLIstItems.get(position).getID());
                bundle.putString("BookName",bookLIstItems.get(position).getBookName());
                bundle.putString("BookCode",bookLIstItems.get(position).getBookCode());
                bundle.putString("AuthorName",bookLIstItems.get(position).getAuthorName());
                bundle.putString("price",bookLIstItems.get(position).getPrice());
                bundle.putString("Quantity",bookLIstItems.get(position).getQuantity());
                bundle.putString("RacNo",bookLIstItems.get(position).getRacNo());
                bundle.putString("SubjectCode",bookLIstItems.get(position).getSubjectCode());
                bundle.putString("IsActive",bookLIstItems.get(position).getIsActive());
                bundle.putString("IsDeleted",bookLIstItems.get(position).getIsDeleted());
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
                fragment=new Addbook();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GetAllAddRemoveBook,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myresponse",response);
                        progressDoalog.dismiss();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            bookLIstItems.clear();
                            customAdapter=null;
                            if(obj.getString("IsSuccess").equalsIgnoreCase("true")){
                                JSONArray object=obj.getJSONArray("ResponseData");
                                for(int i=0; i<object.length();i++){
                                    JSONObject jsonObject= (JSONObject) object.get(i);
                                    bookLIstItems.add(new bookLIstItem(
                                            jsonObject.getString("ID"),
                                            jsonObject.getString("BookName"),
                                            jsonObject.getString("BookCode"),
                                            jsonObject.getString("AuthorName"),
                                            jsonObject.getString("price"),
                                            jsonObject.getString("Quantity"),
                                            jsonObject.getString("RacNo"),
                                            jsonObject.getString("SubjectCode"),
                                            jsonObject.getString("IsActive"),
                                            jsonObject.getString("IsDeleted")
                                    ));
                                }
                                customAdapter=new BooklistAdopter(getActivity(),bookLIstItems);
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
