package com.nasserapps.saham.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nasserapps.saham.Controllers.Fragments.SettingsFragment;
import com.nasserapps.saham.R;

public class SettingsActivity extends AppCompatActivity {

    //TODO Update the settings and add checklists and notification rules by going to http://developer.android.com/guide/topics/ui/settings.html
    public static final String NOTIFICATION = "pref_key_enable_notifications";// The application has auto refresh, when notification is enabled it notify the user when the rules are met.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SettingsFragment())
                .commit();
    }
}
