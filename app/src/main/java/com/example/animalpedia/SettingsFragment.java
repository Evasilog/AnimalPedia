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

/**
 * Αυτή η κλάση διαχειρίζεται το fragment για τις ρυθμίσεις
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_screen); //σύνδεση με το preference_screen.xml

        ListPreference listPreference = (ListPreference) findPreference("listPref_theme");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //έλεγχος και εφαρμογή του κατάλληλου θέματος
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

        //αν ο χρήστης αλλάξει το θέμα γίνεται η εφαρμογή αυτού που επέλεξε
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String items = (String) newValue;
                if (preference.getKey().equals("listPref_theme")) {
                    switch (items) {
                        case "default":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
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

        //εμφάνιση dialog παραθύρου
        Preference infoPreference = findPreference("dialog_pref");
        infoPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                openInfo(getString(R.string.info_title), getString(R.string.info_message));
                return true;
            }
        });

        //ανακατεύθυνση του χρήστη στην εφαρμοφή email του κινητού
        Preference mailPreference = findPreference("mail_pref");
        mailPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("mailto:animalpedia2021@gmail.com");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return false;
            }
        });

    }

    /**
     * διαχείριση του dialog παραθύρου
     * @param title ο τίτλος του παραθύρου
     * @param message το μήνυμα του παραθύρου
     */
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
