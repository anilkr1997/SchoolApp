package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.Adapter.StaffListAdapter;
import com.example.schoolapp.Adapter.StudentListAdapter;
import com.example.schoolapp.Fragments.StaffRegistrationFragment;
import com.example.schoolapp.Fragments.TiameTableFragment;
import com.example.schoolapp.Model.StaffListModel;
import com.example.schoolapp.Model.StudentListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class StaffList extends AppCompatActivity implements StaffListAdapter.SHowUpadate {
    RecyclerView recyclerview;
    ProgressDialog progressDoalog;
    FloatingActionButton fab;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    StaffListAdapter adapter;
    List<StaffListModel> staffListModelList=new ArrayList<>();
    SearchView searchview;
    StaffListAdapter.SHowUpadate sHowUpadate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        sHowUpadate=this;
        progressDoalog=new ProgressDialog(this);
        fab=findViewById(R.id.fab);
        recyclerview=findViewById(R.id.recyclerview);
        searchview=findViewById(R.id.searchview);


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });


        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        LoadStudentList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffRegistrationFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
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


    private void LoadStudentList() {
        //getting the progressbar

        //making the progressbar visible
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetAllEmployee,
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
                                staffListModelList.clear();
                                for (int i = 0; i < stuarray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject studentObject = stuarray.getJSONObject(i);


                                    staffListModelList.add(new StaffListModel(
                                            studentObject.getString("Id"),
                                            studentObject.getString("EmpName"),
                                            studentObject.getString("FatherName"),
                                            studentObject.getString("MotherName"),
                                            studentObject.getString("EmpTypeId"),
                                            studentObject.getString("Address"),
                                            studentObject.getString("Phone"),
                                            studentObject.getString("Email"),
                                            studentObject.getString("DOJ"),
                                            studentObject.getString("ImagePath"),
                                           "",
                                       //     studentObject.getString("Imagebase4"),
                                            studentObject.getString("CreatedDate"),
                                            studentObject.getString("CreatedBy") ,
                                            studentObject.getString("UpdatedBy"),
                                            studentObject.getString("UpdatedDate")
                                    ));
                                }
                                adapter=new StaffListAdapter(StaffList.this,staffListModelList,sHowUpadate);
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
    public void showupdate(String id,String name,String father,String mother ,String Add,String Phone,
                           String email, String dob, String registerdate,String profile,String imagepath) {


        Bundle bundle = new Bundle();

 //       bundle.putSerializable("stafflistModel", (Serializable) staffListModel);
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("father", father);
        bundle.putString("mother", mother);
        bundle.putString("Add", Add);
        bundle.putString("Phone", Phone);
        bundle.putString("email", email);
        bundle.putString("dob", dob);
        bundle.putString("registerdate", registerdate);
        bundle.putString("profile", profile);
        bundle.putString("imagepath", imagepath);

        fragment = new StaffRegistrationFragment();

        fragment.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragment).addToBackStack(null).commit();
    }
}
