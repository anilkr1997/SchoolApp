package com.example.schoolapp.Transport;

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
import com.example.schoolapp.Transport.Fragment.Add_vechile_type;
import com.example.schoolapp.Transport.Fragment.AssigneviceList;
import com.example.schoolapp.Transport.Fragment.DriverList;
import com.example.schoolapp.Transport.Fragment.ListofVichaile_fragment;
import com.example.schoolapp.Transport.Fragment.Route_Fragment;
import com.example.schoolapp.Transport.modelclass.TDModelClass;
import com.example.schoolapp.interfaces.ClickListener;


public class transport extends AppCompatActivity {
RecyclerView recyclerView;
Fragment fragment;
    int image[]={R.drawable.v, R.drawable.driver
            , R.drawable.road, R.drawable.ic_directions_bus_black_24dp
            , R.drawable.xenon, R.drawable.xenon
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TDModelClass[] tdModelClass= new TDModelClass[]{
                new TDModelClass(image[0],"Vehecles List "),
                new TDModelClass(image[1],"Driver Registation "),
                new TDModelClass(image[2],"Route"),
                new TDModelClass(image[3],"Vehecles Type"),
                new TDModelClass(image[4],"Vehecles Assign")
        };
        TDAdopter tdAdopter=new TDAdopter(transport.this,tdModelClass);
        recyclerView=findViewById(R.id.transportdasboard);
        recyclerView.setLayoutManager(new GridLayoutManager(transport.this,3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tdAdopter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
        recyclerView, new ClickListener() {
    @Override
    public void onClick(View view, int position) {
        fragmentopen(position);
        Toast.makeText(transport.this, ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(View view, int position) {
    }
   }));
    }

    private void fragmentopen(int position) {
        switch (position){
            case 0:
                fragment=new ListofVichaile_fragment();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
            case 1:
                fragment=new DriverList();
                FragmentTransaction fragmentTransaction11 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction11.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
            case 2:
                fragment=new Route_Fragment();
                FragmentTransaction fragmentTransaction12 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction12.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
            case 3:
                fragment=new Add_vechile_type();
                FragmentTransaction fragmentTransaction13 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction13.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
            case 4:
                fragment=new AssigneviceList();
                FragmentTransaction fragmentTransaction14 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction14.add(R.id.container, fragment).addToBackStack(null).commit();
                break;
        }
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
