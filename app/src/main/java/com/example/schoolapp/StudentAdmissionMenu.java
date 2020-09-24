package com.example.schoolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.Fragments.AddSessionFragment;
import com.example.schoolapp.Fragments.ClassFragment;
import com.example.schoolapp.Fragments.HouseFragment;
import com.example.schoolapp.Fragments.SectionFragment;
import com.example.schoolapp.interfaces.ClickListener;

public class StudentAdmissionMenu extends AppCompatActivity {
    RecyclerView recyclerview;
    StudentmenuAdapter adapter;
    String item[]={"Class","Section","House","Session","Student Registration"};
    int icon[]={R.drawable.classicon,R.drawable.sectionicon,R.drawable.houseicon,R.drawable.sessionicon,R.drawable.studentreg};
    Fragment fragment;
    FragmentTransaction fragmentTransaction1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admission_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//"""//"""""//""""//"""
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.setHasFixedSize(true);

        adapter=new StudentmenuAdapter();
       recyclerview.setAdapter(adapter);
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing Sc activity & to fragment as well
                switch (position){

                    case 0:
                        fragment=new ClassFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                    case 1:
                        fragment=new SectionFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                    case 2:
                        fragment=new HouseFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                    case 3:
                        fragment=new AddSessionFragment();
                        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                        break;

                    case 4:
                        startActivity(new Intent(StudentAdmissionMenu.this,StudentAdmission.class).putExtra("ID","0"));
                        break;


                }

            }

            @Override
            public void onLongClick(View view, int position) {
            /*    Toast.makeText(SchoolDashBoard.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();*/
            }}));





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
public class StudentmenuAdapter extends RecyclerView.Adapter<StudentmenuAdapter.ViewHolder>{


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custumdash,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.textView.setText(item[position]);
       holder.imageView.setImageResource(icon[position]);
    }

    @Override
    public int getItemCount() {
        return item.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.dashimage);
            textView=itemView.findViewById(R.id.dashtitle);
        }
    }
}

}


