package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.schoolapp.Fragments.ParentCommunication;

import java.util.ArrayList;

public class ParentTeacherCommunication extends AppCompatActivity {

    ListView listview;
    ArrayAdapter<String> adapter;
    ArrayList<String> mylist=new ArrayList<>();
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_teacher_communication);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview=findViewById(R.id.listview);
        mylist.add("Communication With Student's Parents");
        mylist.add("Communication With Staff");
        adapter=new ArrayAdapter<>(ParentTeacherCommunication.this,android.R.layout.simple_list_item_1,mylist);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        fragment=new ParentCommunication();
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.container,fragment).addToBackStack(null);
                        fragmentTransaction.commit();
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
