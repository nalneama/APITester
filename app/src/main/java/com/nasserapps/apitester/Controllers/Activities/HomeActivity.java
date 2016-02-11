package com.nasserapps.apitester.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nasserapps.apitester.Controllers.Adapters.SectionsPagerAdapter;
import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.R;

public class HomeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private DataSource mDataSource;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO Check if user is available or should create a user

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(2);

        mDataSource = new DataSource(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem menuItem = toolbar.getMenu().getItem(0);
        if(!mDataSource.isNotificationEnabled()) {
            menuItem.setIcon(R.drawable.ic_notifications_none_white_24dp);
        }
        else{
            menuItem.setIcon(R.drawable.ic_notifications_active_white_24dp);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_notification:
                if(mDataSource.isNotificationEnabled()) {
                    item.setIcon(R.drawable.ic_notifications_none_white_24dp);
                    mDataSource.setNotificationStatus(false);
                }
                else{
                    item.setIcon(R.drawable.ic_notifications_active_white_24dp);
                    mDataSource.setNotificationStatus(true);
                }
                break;
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
