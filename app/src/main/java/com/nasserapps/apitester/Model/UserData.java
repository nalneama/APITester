package com.nasserapps.apitester.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nasserapps.apitester.AI.CustomNotificationStatement.Checklist;

import java.util.ArrayList;

public class UserData {


    private Context mContext;
    private SharedPreferences memory;
    private SharedPreferences.Editor memoryWriter;


    private static final String MEMORY_KEY="appMemory";
    private static final String SETTINGS_AI_ACTIVATED ="isAIActivated";
    private static final String SETTINGS_NOTIFICATION_STATUS="isNotificationEnabled";
    private static final String SETTINGS_SORTING_PREFERENCE="sortingPreference";
    private static final String STORAGE_CHECKLIST="storedChecklists";



    private String mSortPreference;



    public UserData(Context context) {
        mContext = context;
        memory = mContext.getSharedPreferences(MEMORY_KEY, mContext.MODE_PRIVATE);
    }



    public boolean isUserDataAvailable (){
        String userData =  memory.getString("UserData", "No Data");
        return !userData.contains("No Data");
    }



    public String getSortPreference() {
        return memory.getString(SETTINGS_SORTING_PREFERENCE, "Gain");
    }

    public void setSortPreference(String sortPreference) {
        memoryWriter = memory.edit();
        memoryWriter.putString(SETTINGS_SORTING_PREFERENCE, sortPreference).apply();
    }




    public boolean isNotificationEnabled(){
        return memory.getBoolean(SETTINGS_NOTIFICATION_STATUS, false);
    }

    public void setNotificationStatus(boolean b){
        memoryWriter = memory.edit();
        memoryWriter.putBoolean(SETTINGS_NOTIFICATION_STATUS, b).apply();
    }




    public boolean isAIActivated(){
        return memory.getBoolean(SETTINGS_AI_ACTIVATED, false);
    }

    public void setIsAIActivated(boolean b){
        memoryWriter = memory.edit();
        memoryWriter.putBoolean(SETTINGS_AI_ACTIVATED,b).apply();
    }



    public ArrayList<Checklist> getChecklist() {
//        Gson gson = new Gson();
//        String checklist = memory.getString(STORAGE_CHECKLIST, "empty");
//        if (checklist.equals("empty")){
//            return new ArrayList<>();
//        }
//        else {
//            Type type = new TypeToken<List<Checklist>>() {}.getType();
//            return new Gson().fromJson(checklist, type);
//        }
        return new ArrayList<>();
    }

    public void setChecklist(ArrayList<Checklist> checklists) {
        Gson gson = new Gson();
        String checklist = gson.toJson(checklists);
        memoryWriter = memory.edit();
        memoryWriter.putString(STORAGE_CHECKLIST, checklist).apply();
    }

}
