package com.nasserapps.apitester.Controllers.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.nasserapps.apitester.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
