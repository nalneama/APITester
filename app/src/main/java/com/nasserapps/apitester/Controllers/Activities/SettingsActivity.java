package com.nasserapps.apitester.Controllers.Activities;

import android.app.Activity;
import android.os.Bundle;

import com.nasserapps.apitester.Controllers.Fragments.SettingsFragment;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
