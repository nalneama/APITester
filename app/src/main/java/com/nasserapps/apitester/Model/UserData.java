package com.nasserapps.apitester.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {


    private Context mContext;
    private SharedPreferences memory;
    private SharedPreferences.Editor memoryWriter;


    private static final String MEMORY_KEY="appMemory";
    private static final String SETTINGS_AI_ACTIVATED ="isAIActivated";
    private static final String SETTINGS_NOTIFICATION_STATUS="isNotificationEnabled";



    private String mSortPreference;



    public UserData(Context context) {
        mContext = context;
        memory = mContext.getSharedPreferences(MEMORY_KEY, mContext.MODE_PRIVATE);
    }

    public User getUser(){
//        if(isUserDataAvailable()) {
//            //return userdata mWallet = mDataSource.getWallet();
//            return getSavedUser;
//        }
//        //2.2b Else,this is first time opening of app, set the wallet to initial data
//        else{
//            // return user with initial watchlist and no investments
            return new User(mContext);
//        }
    }

    public boolean isUserDataAvailable (){
        String userData =  memory.getString("UserData", "No Data");
        return !userData.contains("No Data");
    }

    public String getSortPreference() {
        return mSortPreference;
    }

    public void setSortPreference(String sortPreference) {
        mSortPreference = sortPreference;
    }
}
