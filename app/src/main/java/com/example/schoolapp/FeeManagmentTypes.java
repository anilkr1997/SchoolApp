package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.schoolapp.Fragments.ClassFeeFragment;
import com.example.schoolapp.Fragments.CreateFeeTypes;
import com.example.schoolapp.Fragments.FeeCollectionFragment;
import com.example.schoolapp.Fragments.FeeStructureFragment;

public class FeeManagmentTypes extends AppCompatActivity implements CreateFeeTypes.OnFragmentInteractionListener {

    ListView listview;
    ArrayAdapter<String> adapter;
    String item[]={"Create Fee Type","Add Fee Value as per the class","Fee Payment & Fee Receipt","Update Student Fee","Fee Structure"
    };
    Fragment fragment;
    FragmentTransaction fragmentTransaction1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_managment_types);
        Log.d("lifecycle1","onCreate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview = findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(FeeManagmentTypes.this, android.R.layout.simple_list_item_1, item);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        fragment = new CreateFeeTypes();
                         fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                        case 1:
                        fragment = new ClassFeeFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                    case 2:
                        fragment = new FeeCollectionFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                    case 3:
                       startActivity(new Intent(FeeManagmentTypes.this, UpdateStudentFee.class));
                        break;

                    case 4:
                        fragment = new FeeStructureFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
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

    @Override
    public void onbackPressed() {
        onBackPressed();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecycle1","onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle1","onStart");
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecycle1","onPause");
    }

    @Override
    public void onStop() {
        Log.d("lifecycle1","onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("lifecycle1","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle1","onRestart");
    }
}
