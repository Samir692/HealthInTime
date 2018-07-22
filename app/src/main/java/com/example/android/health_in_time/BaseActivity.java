package com.example.android.health_in_time;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.health_in_time.R;

import java.lang.reflect.Field;


public class BaseActivity extends AppCompatActivity {


    //private DrawerLayout mDrawer;
    private DrawerLayout fullView ;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private static Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected boolean useToolbar()
    {
        return true;
    }

    public static Context getAppContext(){
        return mContext;
    }



    public static void  restartgetAppContext(){


        HeartRateMonitor hr = new HeartRateMonitor();

        Intent intent = new Intent(mContext, HeartRateMonitor.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mContext.startActivity(intent);
        hr.activity.finish();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, fullView, toolbar, R.string.drawer_open,  R.string.drawer_close);

    }
    @Override
    public void setContentView(int layoutResID) {
        //Context context = getApplicationContext();
        //CharSequence text = "Menuuuuuuuuuuuuu)";
        //int duration = Toast.LENGTH_SHORT;
        //Toast toast = Toast.makeText(context, text, duration);
        //toast.show();

        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.flContent);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);




        drawerToggle = setupDrawerToggle();
        fullView.addDrawerListener(drawerToggle);
        //fullView.setBackground();


        toolbar = (Toolbar) findViewById(R.id.toolbarlyt);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.start);


        if (useToolbar())
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Health In Time");



            drawerToggle = new ActionBarDrawerToggle(this, fullView, toolbar, R.string.drawer_open, R.string.drawer_close)
            {

                public void onDrawerClosed(View view)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                }
            };
            drawerToggle.setDrawerIndicatorEnabled(true);
            fullView.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }


        final NavigationView navigationView = (NavigationView) fullView.findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        TextView nckname = (TextView) header.findViewById(R.id.nicknameForDrwr);
        nckname.setText(LoginActivity.username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.history:
                        fullView.closeDrawers();
                        Intent intent1 = new Intent(getApplicationContext(), History.class);
                        //intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        return true;

                    case R.id.home:
                        fullView.closeDrawers();
                        Intent intent3 = new Intent(getApplicationContext(), HeartRateMonitor.class);
                        //intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        return true;

                    case R.id.logout:
                        fullView.closeDrawers();
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_xml, menu);
        return true;
    }
    */


}
