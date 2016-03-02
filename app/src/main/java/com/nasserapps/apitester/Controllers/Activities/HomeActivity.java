package com.nasserapps.apitester.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nasserapps.apitester.Controllers.Adapters.SectionsPagerAdapter;
import com.nasserapps.apitester.Controllers.Services.ExecutedTask;
import com.nasserapps.apitester.Controllers.Services.InformAI;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;

public class HomeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private User mUser;

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


        mUser = User.getUser(this);
        // 2.0 Initialize the UserData.


        // 2.1a If user is available (mUserData.isUserDataAvailable), then get the user



        // 2.1b Else, create a new User.

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem menuItem = toolbar.getMenu().getItem(0);
        if(!mUser.getUserData().isNotificationEnabled()) {
            menuItem.setIcon(R.drawable.bell_outline);
        }
        else{
            menuItem.setIcon(R.drawable.bell_ring);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_notification:
                if(mUser.getUserData().isNotificationEnabled()) {
                    item.setIcon(R.drawable.bell_outline);
                    mUser.getUserData().setNotificationStatus(false);
                    stopAI();
                }
                else{
                    item.setIcon(R.drawable.bell_ring);
                    mUser.getUserData().setNotificationStatus(true);
                    startAI();
                }
                break;
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //Start AI Assistant if it is not activated (Only allow AI Assistant activation if SharedPreferences indicates that the AI is off).
    public void startAI() {

        boolean isAIActivated = mUser.getUserData().isAIActivated();

        if(isAIActivated){
            Snackbar.make(toolbar, "AI Assistance already working", Snackbar.LENGTH_LONG).show();
        }
        else {
            mUser.getUserData().setIsAIActivated(true);
            // Construct an intent that will execute the AlarmReceiver
            Intent intent = new Intent(getApplicationContext(), InformAI.class);
            // Create a PendingIntent to be triggered when the alarm goes off
            final PendingIntent pIntent = PendingIntent.getBroadcast(this, InformAI.REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            int interval = ExecutedTask.REFRESHING_INTERVAL;
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    interval, pIntent);
            Snackbar.make(toolbar, "AI Assistance Started", Snackbar.LENGTH_LONG).show();
        }

    }

    //Stop AI Assistant if it is activated (Only allow AI Assistant de-activation if SharedPreferences indicates that the AI is on).
    public void stopAI() {

        boolean isAIActivated = mUser.getUserData().isAIActivated();

        if(!isAIActivated){
            Snackbar.make(toolbar, "AI Assistance already not working", Snackbar.LENGTH_LONG).show();
        }

        else{
            mUser.getUserData().setIsAIActivated(false);
            Intent intent = new Intent(getApplicationContext(), InformAI.class);
            final PendingIntent pIntent = PendingIntent.getBroadcast(this, InformAI.REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pIntent);
            Snackbar.make(toolbar, "AI Assistance Stopped", Snackbar.LENGTH_LONG).show();
        }
    }

}
