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

    public String getSortPreference() {
        return mSortPreference;
    }

    public void setSortPreference(String sortPreference) {
        mSortPreference = sortPreference;
    }
}
