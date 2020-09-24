package com.example.schoolapp.libarymanagment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.RecyclerTouchListener;
import com.example.schoolapp.Transport.Adopter.TDAdopter;
import com.example.schoolapp.Transport.modelclass.TDModelClass;
import com.example.schoolapp.interfaces.ClickListener;
import com.example.schoolapp.libarymanagment.fragment.Addbook;
import com.example.schoolapp.libarymanagment.fragment.BookIssue;
import com.example.schoolapp.libarymanagment.fragment.Booklist;

public class libarymanagment extends AppCompatActivity {
    RecyclerView recyclerView;
    Fragment fragment;
    int image[]={R.drawable.faculty, R.drawable.exam
            , R.drawable.book, R.drawable.library
            , R.drawable.assignment, R.drawable.exam  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libarymanagment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TDModelClass[] tdModelClass= new TDModelClass[]{
                new TDModelClass(image[0],"Libraian"),
                new TDModelClass(image[1],"Membar"),
                new TDModelClass(image[2],"Add/Remove/Edite"),
                new TDModelClass(image[3],"BookList"),
                new TDModelClass(image[4],"IssueBook")};
        TDAdopter tdAdopter=new TDAdopter(libarymanagment.this,tdModelClass);
        recyclerView=findViewById(R.id.transportdasboard);
        recyclerView.setLayoutManager(new GridLayoutManager(libarymanagment.this,3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tdAdopter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                fragmentopen(position);
                Toast.makeText(libarymanagment.this, ""+position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void fragmentopen(int position) {
        switch (position){
            case 0:

                break;
            case 1:

                break;
            case 2:
                fragment=new Addbook();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
            case 3:
                fragment=new Booklist();
                FragmentTransaction fragmentTransaction11 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction11.add(R.id.container, fragment).addToBackStack(null).commit();

                break;
            case 4:
                fragment=new BookIssue();
                FragmentTransaction fragmentTransaction14 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction14.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
