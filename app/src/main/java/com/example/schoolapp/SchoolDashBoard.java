package com.example.schoolapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.schoolapp.Adapter.SchoolDashboardAadapter;
import com.example.schoolapp.Model.SchoolDashModel;
import com.example.schoolapp.Slider.ImageModel;
import com.example.schoolapp.Slider.SlidingImage_Adapter;
import com.example.schoolapp.Transport.transport;
import com.example.schoolapp.aboutschool.Schoollist;
import com.example.schoolapp.interfaces.ClickListener;
import com.example.schoolapp.libarymanagment.libarymanagment;
import com.google.android.material.navigation.NavigationView;
import com.viewpagerindicator.CirclePageIndicator;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SchoolDashBoard extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    List<SchoolDashModel> schoolDashModels;
    RecyclerView recyclerview;
    int image[]={R.drawable.studentadmission,R.drawable.studentinfo
            ,R.drawable.fees,R.drawable.exam
            ,R.drawable.parent,R.drawable.gallery
            ,R.drawable.staff,R.drawable.library,R.drawable.transport
            ,R.drawable.timtable,R.drawable.attendance
            , R.drawable.abouts
         };
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.s1, R.drawable.s2, R.drawable.s1, R.drawable.s2};
    SchoolDashboardAadapter schoolDashboardAadapter;

    NavigationView navigationView;
    TextView website;
    private AppBarConfiguration mAppBarConfiguration;
   SharedPreferences sharedPreferences;
   String ID;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("lifecycle2","onCreate");
        setContentView(R.layout.activity_school_dash_board);



      /*  PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone="+ "91" +
                    "60134686" +"&text=" + URLEncoder.encode("hello", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/

        sharedPreferences=getSharedPreferences("MyPref1",MODE_PRIVATE);
        ID=sharedPreferences.getString("ID","0");
        Toolbar toolbar =  findViewById(R.id.my_awesome_toolbar);
        schoolDashModels=new ArrayList<>();




    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.createfeetypedailog, null);

        getSupportActionBar().setCustomView(v);*/

        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.nav_view);

        // Now retrieve the DrawerLayout so that we can set the status bar color.
        // This only takes effect on Lollipop, or when using translucentStatusBar
        // on KitKat.
         drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        navigationView.setNavigationItemSelectedListener(this);




        drawer.openDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.START);
        website=findViewById(R.id.website);
        website.setText(SharedPrefrenceManager.getInstance(this).getName()+"\n"+
                SharedPrefrenceManager.getInstance(this).getUser());

        schoolDashModels.add(new SchoolDashModel("Student Admission ",image[0]));
        schoolDashModels.add(new SchoolDashModel("Student Information ",image[1]));
        schoolDashModels.add(new SchoolDashModel("Fee Management ",image[2]));
        schoolDashModels.add(new SchoolDashModel("Grading & Examination ",image[3]));
        schoolDashModels.add(new SchoolDashModel("Parent Teacher Communication  ",image[4]));
        schoolDashModels.add(new SchoolDashModel("Gallery & Achievement ",image[5]));
        schoolDashModels.add(new SchoolDashModel("Staff Information  ",image[6]));
        schoolDashModels.add(new SchoolDashModel("e-Resource & Library ",image[7]));
        schoolDashModels.add(new SchoolDashModel("Transport Management ",image[8]));
        schoolDashModels.add(new SchoolDashModel("Time Table With Substitution  ",image[9]));
        schoolDashModels.add(new SchoolDashModel("Attendance management ",image[10]));
        schoolDashModels.add(new SchoolDashModel("About School",image[11]));
        schoolDashboardAadapter=new SchoolDashboardAadapter(schoolDashModels,SchoolDashBoard.this);
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(SchoolDashBoard.this,3));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(schoolDashboardAadapter);

        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerview, new ClickListener() {
            @Override
        public void onClick(View view, final int position) {
            //Values are passing Sc activity & to fragment as well
            switch (position){

                case 0:
                    startActivity(new Intent(SchoolDashBoard.this,StudentAdmissionMenu.class));
                    break;

                    case 1:
                    startActivity(new Intent(SchoolDashBoard.this,StudentList.class));
                    break;

                    case 2:
                        startActivity(new Intent(SchoolDashBoard.this,FeeManagmentTypes.class));

                  /*  startActivity(new Intent(SchoolDashBoard.this,FeeManagment.class)
                            .putExtra("AdmissionNo", AdmissionNo.getText().toString()).
                                    putExtra("StudentName", StudentName.getText().toString()).
                                    putExtra("ClassId", ClassId.getText().toString())
                    );*/
                    break;

                case 4:
                  //  startActivity(new Intent(SchoolDashBoard.this,StaffList.class));
                    startActivity(new Intent(SchoolDashBoard.this,ParentTeacherCommunication.class));
                    break;
                case 6:
                  //  startActivity(new Intent(SchoolDashBoard.this,StaffList.class));
                    startActivity(new Intent(SchoolDashBoard.this,StaffManagment.class));
                    break;
                case 3:
                    startActivity(new Intent(SchoolDashBoard.this,TestActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(SchoolDashBoard.this,GalleryAchievements.class));
                    break;
                case 7:
                    startActivity(new Intent(SchoolDashBoard.this, libarymanagment.class));

                    break;
                case 8:
                    startActivity(new Intent(SchoolDashBoard.this, transport.class));

                    break;

                    case 9:
                    startActivity(new Intent(SchoolDashBoard.this,TimeTableActivity.class));
                    break;
                    case 10:
                    startActivity(new Intent(SchoolDashBoard.this,AttendanceManagment.class));
                    break;
                case 11:
                  Fragment fragment=new Schoollist();
                    FragmentTransaction fragmentTransaction14 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction14.add(R.id.container, fragment).addToBackStack(null).commit();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

                case R.id.logout:
                // app icon in action bar clicked; goto parent activity.
                    finish();
               SharedPrefrenceManager.getInstance(this).logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < myImageList.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        init();
        Log.d("lifecycle2","onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle2","onStart");
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecycle2","onPause");
    }

    @Override
    public void onStop() {
        Log.d("lifecycle2","onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("lifecycle2","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle2","onRestart");
    }
    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(SchoolDashBoard.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem) {
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawer.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.profile:
               startActivity(new Intent(SchoolDashBoard.this,UserSignUp.class).putExtra("ID",ID));
                break;
                case R.id.logout:
                    finish();
                    SharedPrefrenceManager.getInstance(this).logout();
                break;
                case R.id.about:
                    startActivity(new Intent(SchoolDashBoard.this,AboutSchool.class).putExtra("ID",ID));

                    break;

                case R.id.nav_share:
                    Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
                    String app_url = "Let's Download cool "+getResources().getString(R.string.app_name)+" App.\n https://play.google.com/store/apps/details?id=com.vividtechno";
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                    break;
                case R.id.rating:
                   try {
                       Intent intent = new Intent(Intent.ACTION_VIEW);
                       intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vividtechno"));
                       startActivity(intent);
                   }
                   catch (Exception e){}
                    break;

        }
            return true;
    }

}
