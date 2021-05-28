package com.example.animalpedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.animalpedia.R;

public class SettingsFragment extends PreferenceFragment {

    ListPreference listPreference;
    Preference infoPreference;
    Preference mailPreference;

    //public static final String MyPREFERENCES = "nightModePrefs";
    //public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // below line is used to add preference
        // fragment from our xml folder.
        addPreferencesFromResource(R.xml.preference_screen);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listPreference = (ListPreference) findPreference("listPref_theme");

        String theme = sharedPreferences.getString("listPref_theme", "false");
        if ("default".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            listPreference.setSummary(listPreference.getEntry());
        } else if ("light".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            listPreference.setSummary(listPreference.getEntry());
        } else if ("dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            listPreference.setSummary(listPreference.getEntry());
        }

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String items = (String) newValue;
                if (preference.getKey().equals("listPref_theme")) {
                    switch (items) {
                        case "default":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            //startActivity(getIntent());
                            //overridePendingTransition(0, 0);
                            break;
                        case "light":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            break;
                        case "dark":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            break;
                    }
                    ListPreference lP = (ListPreference) preference;
                    lP.setSummary(lP.getEntries()[lP.findIndexOfValue(items)]);
                }
                return true;
            }
        });

        infoPreference = findPreference("dialog_pref");
        infoPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                openInfo("Info", getString(R.string.info_message));
                return true;
            }
        });

        mailPreference = findPreference("mail_pref");
        mailPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("mailto:animalpedia2021@gmail.com");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return false;
            }
        });

    }


    private void openInfo(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}
