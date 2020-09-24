package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.schoolapp.Adapter.StudentAttandantAdapter;
import com.example.schoolapp.Model.StudentAttandantModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceManagment extends AppCompatActivity {
    StudentAttandantAdapter adapter;
    List<StudentAttandantModel> studentAttandantModelList;
    RecyclerView recyclerView;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_managment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        studentAttandantModelList=new ArrayList<>();
        submit=findViewById(R.id.submit);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceManagment.this));
        for(int i=1;i<101;i++)
        studentAttandantModelList.add(new StudentAttandantModel(String.valueOf(i),true,false));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<studentAttandantModelList.size();i++){
                    Log.d("ashishsikarwar",String.valueOf(i)+studentAttandantModelList.get(i).isPresent()+"");
                }
            }
        });

        adapter=new StudentAttandantAdapter(this,studentAttandantModelList);
        recyclerView.setAdapter(adapter);
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
