package com.nasserapps.apitester.Controllers.Application;

import android.app.Application;
import android.content.Intent;

import com.nasserapps.apitester.Controllers.Activities.WelcomeScreen.WelcomeActivity;
import com.nasserapps.apitester.Model.User;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        User mUser = User.getUser(this);
//        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SettingsActivity.NOTIFICATION,false)){
//        TODO Launch ensure that the broadcast receiver is alive;
//        }

        if(false){//mUser.getUserData().isFirstTime()
            Intent i = new Intent(getBaseContext(), WelcomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //GO to welcome screen and after agreeing on all the info, on clicking agree make the first time(false)
            //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
            //mUser.getUserData().setIsFirstTime(false);
            startActivity(i);
        }

    }
}