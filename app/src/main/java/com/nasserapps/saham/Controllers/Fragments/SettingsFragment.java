package com.nasserapps.saham.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.nasserapps.saham.Controllers.Activities.ChecklistActivity;
import com.nasserapps.saham.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        //https://www.udacity.com/course/viewer#!/c-ud853/l-1474559101/e-1643578589/m-1643578590
        // https://github.com/udacity/Sunshine-Version-2/compare/3.07_add_locations_setting...3.08_inflate_settingsactivity
        //Preference preference = (Preference)findPreference(SettingsActivity.NOTIFICATION);
        ListPreference mLanguageSelecter = (ListPreference)findPreference("pref_language");
        Preference notificationPreference = (Preference)findPreference("pref_notifications");

//        mLanguageSelecter.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                preference.setSummary(newValue.toString());
//                if (newValue.toString().equalsIgnoreCase("العربية")){
//                    Configuration configuration = new Configuration();
//                    configuration.setLocale(new Locale("ar"));
//
//                    newConfig.locale = Locale.ENGLISH;
//                    super.onConfigurationChanged(newConfig);
//
//                    Locale.setDefault(newConfig.locale);
//                    getBaseContext().getResources().updateConfiguration(newConfig, getResources().getDisplayMetrics());
//                }
//                return false;
//            }
//        });

        notificationPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), ChecklistActivity.class);
                startActivity(i);
                return false;
            }
        });
    }
}
