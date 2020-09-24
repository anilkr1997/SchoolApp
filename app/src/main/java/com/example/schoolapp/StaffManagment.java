package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.schoolapp.Fragments.AddStaffProfileFrag;
import com.example.schoolapp.Fragments.StaffRegistrationFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class StaffManagment extends AppCompatActivity {

    ListView listview;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> adapter=null;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_managment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview=findViewById(R.id.listview);
        arrayList.add("Add Profile Types");
        arrayList.add("View Staff");
        arrayList.add("Add Staff");
        adapter=new ArrayAdapter<>(StaffManagment.this,android.R.layout.simple_spinner_dropdown_item,arrayList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        fragment=new AddStaffProfileFrag();
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.container,fragment).addToBackStack(null).commit();
                        break;
                    case 1:
                        startActivity(new Intent(StaffManagment.this,StaffList.class));
                         break;
                    case 2:
                        fragment = new StaffRegistrationFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;
                }
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


}
