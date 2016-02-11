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
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.Model.UserData;
import com.nasserapps.apitester.R;

public class HomeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private UserData mUserData;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 1.0 Initialize the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 1.1 Create the adapter that will return a fragment for each of the three primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager());
        // 1.2 Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(2);
        // 1.3 Add the ViewPager to the Tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        // 2.0 Initialize the UserData.
        mUserData = new UserData(this);



        // TODO 2.0 Check if user is new or existing
        // 2.1a If user is available (mUserData.isUserDataAvailable), then get the user



        // 2.1b Else, create a new User.
        User mUser = new User(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem menuItem = toolbar.getMenu().getItem(0);
        if(!mUserData.isNotificationEnabled()) {
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
                if(mUserData.isNotificationEnabled()) {
                    item.setIcon(R.drawable.ic_notifications_none_white_24dp);
                    mUserData.setNotificationStatus(false);
                }
                else{
                    item.setIcon(R.drawable.ic_notifications_active_white_24dp);
                    mUserData.setNotificationStatus(true);
                }
                break;
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
